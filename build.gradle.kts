val minestomVersion: String by project

plugins {
    application
    id("java")
    id("com.github.johnrengelman.shadow") version ("7.1.1")
}

group = "dev.qixils.aviary"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("com.github.Minestom:Minestom:${minestomVersion}")
}

application {
    mainClass.set("dev.qixils.aviary.Aviary")
    @Suppress("DEPRECATION") // still used by Shadow
    mainClassName = "dev.qixils.aviary.Aviary"
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("aviary-${project.version}.jar")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}