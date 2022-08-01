val velocityVersion: String by project

description = "Aviary's Velocity proxy plugin"

plugins {
    id("com.github.johnrengelman.shadow") version ("7.1.1")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc"
    }
}

dependencies {
    implementation(project(":common"))
    compileOnly("com.velocitypowered:velocity-api:${velocityVersion}")
    annotationProcessor("com.velocitypowered:velocity-api:${velocityVersion}")
}
