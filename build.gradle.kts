import org.liquibase.gradle.LiquibaseTask
import java.io.IOException
import java.util.*

val javaVersion = JavaVersion.VERSION_21
val srcMainDbPath = file("${projectDir.absolutePath}/src/main/db")

group = "io.github.soat7"
version = "0.0.1-SNAPSHOT"

if (!javaVersion.isCompatibleWith(JavaVersion.current())) {
    error(
        """
        =======================================================
        RUN WITH JAVA $javaVersion
        =======================================================
        """.trimIndent(),
    )
}

val props = Properties()
try {
    props.load(file("$projectDir/.env").inputStream())
} catch (e: IOException) {
    println(e.message)
}

plugins {
    kotlin("jvm") version "2.0.20"
    id("org.liquibase.gradle") version "3.0.1"
}

buildscript {
    dependencies {
        classpath("org.liquibase:liquibase-core:4.+")
    }
}

repositories {
    mavenCentral()
}

sourceSets {
    main {
        resources {
            srcDirs + srcMainDbPath
        }
    }
}

dependencies {
    implementation("info.picocli:picocli:4.+")
    implementation("org.liquibase:liquibase-core:4.+")
    implementation("org.postgresql:postgresql:42.7.+")

    // Liquibase
    liquibaseRuntime("info.picocli:picocli:4.+")
    liquibaseRuntime("org.liquibase:liquibase-core:4.+")
    liquibaseRuntime("org.postgresql:postgresql:42.7.+")
    liquibaseRuntime(sourceSets.main.get().resources)
}


liquibase {
    this.activities.register("update") {
        this.arguments = mapOf(
            "changelogFile" to "/changelog/master.xml",
            "url" to props["DATABASE_URL"],
            "username" to props["DATABASE_USER"],
            "password" to props["DATABASE_PASSWORD"],
            "logLevel" to "debug"
        )
        logger.info("########################################################")
        logger.info("## activities.register = ${liquibase.activities.names}")
        logger.info("########################################################")
    }
    runList = "update"
}

tasks.named<LiquibaseTask>("update") {
    doFirst {
        liquibase.setProperty("runList", "update")
        logger.info("########################################################")
        logger.info("## doFirst ${liquibase.activities.asMap}")
        logger.info("########################################################")
    }

    logger.info("########################################################")
    logger.info("## named = ${liquibase.activities.names}")
    logger.info("## activities[update] = ${liquibase.activities.getByName("update")}")
    logger.info("## activities[update].args = ${liquibase.activities.getByName("update").arguments}")
    logger.info("########################################################")
}