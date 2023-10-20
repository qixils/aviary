val minecraftVersion: String by project

description = "Aviary's Minigame server"

plugins {
    id("com.github.johnrengelman.shadow") version ("8.1.1")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api(project(":common"))
    compileOnly("io.papermc.paper:paper-api:${minecraftVersion}-R0.1-SNAPSHOT")
    implementation(kotlin("reflect"))
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("minigames-${project.version}.jar")
}
