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
}

android {
    namespace = "com.cherryyar.kontentliste" + project.name.replace('-', '_')
    compileSdk = 36
    defaultConfig { minSdk = 24 }
    packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
}
