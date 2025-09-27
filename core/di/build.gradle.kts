plugins { id("convention.kmp.library.plain") }

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(project(":core:network"))
            implementation(project(":feature:accounts-list"))
        }
    }
}
