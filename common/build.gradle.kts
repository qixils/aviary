val junitVersion: String by project
val adventureVersion: String by project

description = "Common module for Aviary chat"

dependencies {
    api(project(":db"))
    api("net.kyori:adventure-api:${adventureVersion}")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
