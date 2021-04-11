package com.mfe.scrapping

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.runBlocking
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.bson.Document

import org.jsoup.Jsoup
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class Process {
    val client: MongoClient = MongoClients.create()
    private val database: MongoDatabase = client.getDatabase("myfavoreats-develop")
    val collection: MongoCollection<Document> = database.getCollection("allrecipes", Document::class.java)
    val  limitChannel = Channel<Int>(1000)
    val flag = AtomicBoolean( false )
    val visited: MutableList<String> = mutableListOf()

    init {
        log.info( "query collection")
        //fillVisited()
        log.info( "filling done")
    }

    fun run() = runBlocking {

        launch {
            limiter()
        }

       getLinks().buffer(100).onCompletion {
           delay(3000)
          flag.set(true)
       }.collect{
               launch {
                   delay(1000)
                   processOneDocument(it)
               }
       }

    }

    private fun processOneDocument(url: String ) = runBlocking {
        limitChannel.receive()
            try {
                val jsoup = Jsoup.parse(URL(url), 10000)
                val selection = jsoup.select("script[type=application/ld+json]")
                val str = selection.html()
                val doc = Document("link", url).append("data", str)
                collection.insertOne(doc).asFlow().collect {
                    log.info("$url ${it.insertedId}")
                }
            } catch (e: Throwable) {
                log.warn("$url  failed", e)
            }


    }

    private fun limiter() = runBlocking {
        val cnt = AtomicInteger(0)
        while(true) {
            if ( flag.get() ) break
            delay(1000)
            limitChannel.send( cnt.incrementAndGet() )
        }
    }

    private  fun getLinks(): Flow<String> {
        val pattern = Regex( "/recipe/")
        val data =  Process::class.java.getResourceAsStream( "/allrecipes_links")?.let{ stream ->
            stream.bufferedReader().readLines()
                .filter{it -> pattern.containsMatchIn(it)}
       //         .filterNot{it -> visited.contains(it) }
                .asFlow()
        }
        data?.let {
            return it
        }
        return listOf<String>().asFlow()
    }
/**
    private fun fillVisited() = runBlocking {
        collection.distinct("link", String::class.java).collect {
            visited.add(it)
        }
    }
    **/
    companion object {
        val log: Log = LogFactory.getLog(Process::class.java.name)
    }

}