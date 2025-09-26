plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(libs.gradlePlugin.kotlin)
    implementation(libs.gradlePlugin.android)
    implementation(libs.gradlePlugin.compose)
    implementation(libs.gradlePlugin.compose.compiler)
    implementation(libs.gradlePlugin.serialization)
}