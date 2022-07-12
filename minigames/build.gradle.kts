val minestomVersion: String by project

description = "Aviary's Minigame server"
version = "1.0.0-SNAPSHOT"

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
