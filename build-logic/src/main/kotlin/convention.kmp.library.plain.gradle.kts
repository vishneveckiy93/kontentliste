plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.cherryyar.kontenliste" + project.path.replace(':', '.')
    compileSdk = 36
    defaultConfig { minSdk = 24 }
    packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
}
