plugins {
    application
    id("java")
    id("com.github.johnrengelman.shadow") version ("7.1.1")
}

subprojects {
    apply(plugin = "org.gradle.application")
    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
    }
}

allprojects {
    group = "dev.qixils.aviary"
}
