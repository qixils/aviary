val kotlinVersion: String by project
val kotlinCoroutinesVersion: String by project

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
        api("org.jetbrains.kotlin:kotlin-stdlib-8:$kotlinVersion") // todo is this necessary?
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$kotlinCoroutinesVersion")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}
