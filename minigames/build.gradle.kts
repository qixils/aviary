val minestomVersion: String by project

description = "Aviary's Minigame server"

plugins {
    application
    id("com.github.johnrengelman.shadow") version ("8.1.1")
}

application {
    mainClass.set("dev.qixils.aviary.minigames.Minigames")
}

repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    api(project(":common"))
    api("com.github.Minestom:Minestom:${minestomVersion}")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("minigames-${project.version}.jar")
}
