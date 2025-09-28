plugins { id("convention.kmp.library.plain") }

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.test)             // аннотации @BeforeTest/@AfterTest (KMP)
                implementation(libs.coroutines.test)         // StandardTestDispatcher, runTest (KMP)
            }
        }
    }
}
