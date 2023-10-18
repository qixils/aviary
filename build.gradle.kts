val kotlinVersion: String by project

plugins {
    kotlin("jvm") version "1.9.10" apply true
}

allprojects {
    group = "dev.qixils.aviary"
    version = "1.0.0-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    dependencies {
        api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}
