val minestomVersion: String by project

description = "Aviary's Minigame server"

plugins {
    application
    id("com.github.johnrengelman.shadow") version ("7.1.1")
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

application {
    mainClass.set("dev.qixils.aviary.minigames.Minigames")
    @Suppress("DEPRECATION") // still used by Shadow
    mainClassName = "dev.qixils.aviary.minigames.Minigames"
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("minigames-${project.version}.jar")
}
