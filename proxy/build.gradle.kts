val velocityVersion: String by project

description = "Aviary's Velocity proxy plugin"
version = "1.0.0-SNAPSHOT"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc"
    }
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:${velocityVersion}")
    annotationProcessor("com.velocitypowered:velocity-api:${velocityVersion}")
}
