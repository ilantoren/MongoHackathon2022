package hackathon

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.runInterruptible
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.util.zip.GZIPInputStream

class Process {
    private val objectMapper = jacksonObjectMapper()
    private val records = mutableListOf<GdeltEvent>()

    private val dotenv = Dotenv.load()

    private var insertId: ByteArray = ByteArray(0)

    private val httpClient: HttpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = JacksonSerializer(objectMapper)
        }
    }

    init {
        dotenv {
            systemProperties = true
        }
        run()
    }

    fun run() = runBlocking {
        val apiKey: String = dotenv.get("mongo-api-key")
        // this builder requires the @OptIn, which in turns requires
        //   -opt-in=kotlin.RequiresOptIn in the program arguments
        // when building with gradle

        val requestHeaders = HeadersBuilder(4).apply {
            append("Content-Type", "application/json")
            append("Access-Control-Request-Headers", "*")
            append("Accept-Encoding", "gzip")
            append("api-key", apiKey)
        }.build()

        val database: String = dotenv.get("mongo_database")
        val sourceCollection: String = dotenv.get("mongo_source_collection")
        val targetCollection: String = dotenv.get("mongo_target_collection")
        val datasource: String = dotenv.get("mongo_datasource")


        val projection = org.bson.Document(mapOf("id" to 1, "Day" to 1, "GlobalEventId" to 1))
        val sort = org.bson.Document("GlobalEventId", -1)

        launch {
            httpClient.request<HttpResponse> {
                url("https://data.mongodb-api.com/app/data-vsvwa/endpoint/data/beta/action/find")
                method = HttpMethod.Post
                headers.appendAll(requestHeaders)
                body = MongoRequest("mongoworld", database, sourceCollection, projection, sort)
            }.apply {
                log.info("Request status ${this.status}")
                val channel: ByteArray = getByteArrayFromResponse(this)
                println(channel.decodeToString())
                val lastId = processMongoResponse(channel.inputStream())
                log.info("Pulling from biqquery from GlobalEventId  $lastId")
                lastId?.let {
                    val events = CollectGDeltEvents.call(lastId)
                    records.addAll(events)
                }

                if (records.isNotEmpty())
                    httpClient.request<HttpResponse> {
                        url("https://data.mongodb-api.com/app/data-vsvwa/endpoint/data/beta/action/insertMany")
                        method = HttpMethod.Post
                        headers.appendAll(requestHeaders)
                        body = MongoInsertMany(datasource, database, targetCollection, records)
                    }.apply {
                        insertId = getByteArrayFromStream(this.content.toByteArray().inputStream())
                        log.info(String(insertId))
                    }
                httpClient.close()
            }

        }
    }

    /*
       Response can be either a gzip byte array or uncompressed
     */
    private suspend fun getByteArrayFromResponse(httpResponse: HttpResponse): ByteArray {
        return if (httpResponse.headers["content-encoding"] == "gzip") {
            val bis = httpResponse.readBytes().inputStream()
            getByteArrayFromStream(bis)
        } else {
            httpResponse.readBytes()
        }
    }

    //  Unwrap the gzip byte stream into a ByteArray
    private suspend fun getByteArrayFromStream(bis: ByteArrayInputStream): ByteArray = runInterruptible {
        val gzipInputStream = GZIPInputStream(bis)
        gzipInputStream.readAllBytes()
    }

    // use the object mapper to unwrap the byte array to a MongoResponseArray and return array of TravelRequest
    private suspend fun processMongoResponse(byteArray: ByteArrayInputStream): Long? = runInterruptible {
        val parser = objectMapper.createParser(byteArray)
        var response: Long? = null
        val responseAsList = objectMapper.readValue(parser, MongoResponseArray::class.java)
        responseAsList.documents.firstOrNull()?.let {
            log.info("Last day: ${it.Day}   GlobalEventId: ${it.GlobalEventId}")
            response = it.GlobalEventId.toLong()
        }
        response
    }

    companion object {
        val log = LoggerFactory.getLogger("hackathon")

        @JvmStatic
        fun main(args: Array<String>) {
            Process()
        }
    }
}