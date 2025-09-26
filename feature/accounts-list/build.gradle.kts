plugins { id("convention.kmp.library.compose") }

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core:network"))        // for now take API/DTO from here
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                api(libs.koin.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.coroutines.test)
                implementation(libs.ktor.client.mock)
            }
        }
    }
}
