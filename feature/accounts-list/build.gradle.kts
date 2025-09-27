plugins {
    id("convention.kmp.library.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
                implementation(project(":core:network"))        // for now take API/DTO from here
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.koin.core)
                implementation(libs.ktor.serialization.json)
        }
        commonTest.dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.coroutines.test)
                implementation(libs.ktor.client.mock)
                implementation(libs.ktor.client.contentnegotiation)
                implementation(libs.ktor.serialization.json)
        }
    }
}
