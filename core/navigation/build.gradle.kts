plugins { id("convention.kmp.library.compose") }

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.navigation.compose)
                implementation(compose.animation)
                implementation(project(":feature:accounts-list"))
            }
        }
    }
}
