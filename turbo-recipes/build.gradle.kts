import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    application
    antlr
    maven
}

group = "mfe.yr2020"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    jcenter()
    mavenLocal()
    flatDir {
        println("hello")
        dirs(listOf("$projectDir/libs")).also {
            println("accessed  $projectDir/libs")
        }
    }
}
dependencies {

    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    implementation("com.mfe:parser:0.8")
    implementation("com.mfe:datamodel:0.8")
    implementation( "de.jflex:jflex:1.6.1")
    implementation("org.apache.poi:poi:4.1.2")
    implementation("org.apache.tika:tika:1.24.1")
    implementation("org.apache.tika:tika-parsers:1.24.1")
    implementation("org.apache.tika:tika-app:1.24.1")
    implementation("org.apache.tika:tika-serialization:1.24.1")
    implementation("org.apache.tika:tika-example:1.24.1")
    implementation("org.antlr:antlr4:4.7.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.3")
    implementation( "com.fasterxml.jackson.core:jackson-core:jar:2.11.3")
    implementation( "com.fasterxml.jackson.dataformat:jackson-dataformat-xml:jar:2.11.3")

    antlr("org.antlr:antlr4:4.7.1")
    implementation("org.antlr:antlr4:4.7.1")
    implementation(kotlin("stdlib-jdk8"))
}


tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "13"
}
application {
    mainClassName = "MainKt"
}

tasks.register("hello") {
    doLast {
        println("Hello world!")
    }
}

tasks.generateGrammarSource {
    maxHeapSize = "64m"
    arguments = arguments + listOf("-package", "com.mfe.parser", "-visitor", "-long-messages" )
    inputs.files(source.files)
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}

task("writeNewPom") {
    doLast {
        maven.pom {
            withGroovyBuilder {
                "project" {
                    setProperty("inceptionYear", "2020")
                    "licenses" {
                        "license" {
                            setProperty("name", "The Apache Software License, Version 2.0")
                            setProperty("url", "http://www.apache.org/licenses/LICENSE-2.0.txt")
                            setProperty("distribution", "repo")
                        }
                    }
                }
            }
        }.writeTo("$buildDir/newpom.xml")
    }
}