import java.io.IOException
import java.util.*

val javaVersion = JavaVersion.VERSION_21
val srcMainDbPath = "${projectDir.absolutePath}/src/main/db"

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

dependencies {
    implementation("info.picocli:picocli:4.+")
    implementation("org.liquibase:liquibase-core:4.+")
    implementation("org.postgresql:postgresql:42.7.+")

    // Liquibase
    liquibaseRuntime("info.picocli:picocli:4.+")
    liquibaseRuntime("org.liquibase:liquibase-core:4.+")
    liquibaseRuntime("org.postgresql:postgresql:42.7.+")
}

liquibase {
    activities.register("update") {
        this.arguments = mapOf(
            "changelogFile" to "/changelog/master.xml",
            "searchPath" to srcMainDbPath,
            "url" to props["DATABASE_URL"],
            "username" to props["DATABASE_USER"],
            "password" to props["DATABASE_PASSWORD"],
            "logLevel" to "debug"
        )
    }
    activities.register("rollback") {
        this.arguments = mapOf(
            "changelogFile" to "$srcMainDbPath/changelog/master.xml",
            "url" to "jdbc:${props["DATABASE_URL"]}",
            "username" to props["DATABASE_USER"],
            "password" to props["DATABASE_PASSWORD"],
            "count" to 1,
            "logLevel" to "debug"
        )
    }
    runList = "update"
}

