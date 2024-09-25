import org.liquibase.gradle.LiquibaseTask
import java.io.IOException
import java.util.Properties

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
    // Liquibase
    liquibaseRuntime("info.picocli:picocli:4.+")
    liquibaseRuntime("org.liquibase:liquibase-core:4.+")
    liquibaseRuntime("org.postgresql:postgresql:42.7.+")
    liquibaseRuntime("org.liquibase.ext:liquibase-hibernate6:4.+")
    liquibaseRuntime("org.springframework.boot:spring-boot-starter-data-jpa")
}

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "changelogFile" to "/db/changelog/master.xml",
            "url" to "jdbc:${props["DATABASE_URL"]}",
            "username" to props["DATABASE_USER"],
            "password" to props["DATABASE_PASSWORD"],
        )
    }
    activities.register("rollback") {
        this.arguments = mapOf(
            "changelogFile" to "/db/changelog/master.xml",
            "url" to "jdbc:${props["DATABASE_URL"]}",
            "username" to props["DATABASE_USER"],
            "password" to props["DATABASE_PASSWORD"],
            "count" to 1,
        )
    }
}


tasks.named<LiquibaseTask>("update") {
    doFirst {
        liquibase.setProperty("runList", "rollback")
    }
}

tasks.named<LiquibaseTask>("updateSql") {
    doFirst {
        liquibase.setProperty("runList", "rollback")
    }
}

tasks.named<LiquibaseTask>("rollbackCount") {
    doFirst {
        liquibase.setProperty("runList", "rollback")
    }
}

tasks.named<LiquibaseTask>("rollbackCountSql") {
    doFirst {
        liquibase.setProperty("runList", "rollback")
    }
}