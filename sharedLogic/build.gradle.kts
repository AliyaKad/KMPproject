import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.sqldelight)
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
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.serialization.core)

                implementation(libs.bundles.ktorClientCommon)

                implementation(libs.koin.core)
                implementation(libs.multiplatform.settings)
                implementation(libs.multiplatform.settings.serialization)

                implementation(libs.sqldelight.coroutines.extensions)

                implementation(libs.androidx.lifecycle.viewmodel.multiplatform)
                implementation(libs.kotlinx.datetime)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.sqldelight.android.driver)
                implementation(libs.okhttp3.logging.interceptor)
                implementation(libs.timber)
                implementation("com.google.firebase:firebase-analytics-ktx:22.3.0")
                implementation("com.google.firebase:firebase-crashlytics-ktx:19.4.0")
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.okhttp3.logging.interceptor)
                implementation(libs.sqldelight.sqlite.driver)
            }
        }
    }
}


sqldelight {
    databases {
        create("Database") {
            packageName.set("org.itis.project.sharedlogic")
        }
    }
}

android {
    namespace = "org.itis.project.sharedlogic"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

val nasaKey: String = run {
    val props = Properties()
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use(props::load)
    (props.getProperty("NASA_API_KEY") ?: System.getenv("NASA_API_KEY") ?: "DEMO_KEY")
}

buildkonfig {
    packageName = "org.itis.project.sharedlogic.config"
    objectName = "BuildConfig"
    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "NASA_API_KEY", nasaKey)
    }
}