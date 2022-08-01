val junitVersion: String by project
val annotationsVersion: String by project
val mongoVersion: String by project
val reactorVersion: String by project

description = "Module for interacting with Aviary's databases"

dependencies {
    api("org.jetbrains:annotations:${annotationsVersion}")
    api("org.mongodb:mongodb-driver-reactivestreams:${mongoVersion}")
    api(platform("io.projectreactor:reactor-bom:${reactorVersion}"))
    api("io.projectreactor:reactor-core")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
