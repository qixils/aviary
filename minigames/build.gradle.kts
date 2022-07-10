val minestomVersion: String by project

description = "Aviary's Minigame server"
version = "1.0.0-SNAPSHOT"

repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("com.github.Minestom:Minestom:${minestomVersion}")
}

application {
    mainClass.set("dev.qixils.aviary.Minigames")
    @Suppress("DEPRECATION") // still used by Shadow
    mainClassName = "dev.qixils.aviary.Minigames"
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("minigames-${project.version}.jar")
}
