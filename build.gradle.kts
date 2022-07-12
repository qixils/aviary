plugins {
    application
    id("java")
    id("java-library")
    id("com.github.johnrengelman.shadow") version ("7.1.1") apply false
}

subprojects {
    apply {
        plugin("org.gradle.application")
        plugin("java")
        plugin("java-library")
        plugin("com.github.johnrengelman.shadow")
    }

    repositories {
        mavenCentral()
    }
}

allprojects {
    group = "dev.qixils.aviary"
}
