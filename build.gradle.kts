plugins {
    application
    id("java")
    id("com.github.johnrengelman.shadow") version ("7.1.1") apply false
    kotlin("jvm") version "1.7.10" apply false // TODO: depending on kotlin just for api() is lame
}

subprojects {
    apply {
        plugin("org.gradle.application")
        plugin("java")
        plugin("com.github.johnrengelman.shadow")
        plugin("org.jetbrains.kotlin.jvm")
    }

    repositories {
        mavenCentral()
    }
}

allprojects {
    group = "dev.qixils.aviary"
}
