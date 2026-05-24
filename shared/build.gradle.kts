import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm() {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)

                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.client.logging)

                implementation(libs.kotlinx.serialization.json)

                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.vm)

                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kotlinx.coroutines.android)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kotlinx.coroutines.swing)
            }
        }
    }
}


android {
    namespace = "org.itis.project.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.itis.project.shared.generated"
    generateResClass = auto
}