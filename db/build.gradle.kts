val junitVersion: String by project
val annotationsVersion: String by project
val mongoVersion: String by project
val reactorVersion: String by project

version = "1.0.0-SNAPSHOT"

dependencies {
    implementation("org.jetbrains:annotations:${annotationsVersion}")
    implementation("org.mongodb:mongodb-driver-reactivestreams:${mongoVersion}")
    implementation(platform("io.projectreactor:reactor-bom:${reactorVersion}"))
    implementation("io.projectreactor:reactor-core")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
