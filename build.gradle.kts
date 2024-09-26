import org.jetbrains.kotlin.gradle.utils.extendsFrom
import org.liquibase.gradle.LiquibaseTask
import java.io.IOException
import java.util.*

val javaVersion = JavaVersion.VERSION_21

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

configurations {
    liquibaseRuntime {
        extendsFrom(configurations.compileClasspath.get())
    }
}

dependencies {
    implementation("info.picocli:picocli:4.+")
    implementation("org.liquibase:liquibase-core:4.+")
    implementation("org.postgresql:postgresql:42.7.+")
}


liquibase {
    this.activities.register("update") {
        this.arguments = mapOf(
            "changelogFile" to "/db/changelog/master.xml",
            "searchPath" to sourceSets.main.get().resources.srcDirs.first(),
            "url" to props["DATABASE_URL"],
            "username" to props["DATABASE_USER"],
            "password" to props["DATABASE_PASSWORD"],
            "logLevel" to "debug"
        )
    }
    runList = "update"
}

tasks.named<LiquibaseTask>("update") {
    dependsOn("processResources")
    doFirst {
        liquibase.setProperty("runList", "update")
    }
}