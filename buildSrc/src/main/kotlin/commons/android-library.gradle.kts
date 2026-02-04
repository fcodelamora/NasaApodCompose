package commons

/**
 * Default Gradle file used by modules in `:provide` that act as Android libraries.
 */
plugins {
    id("commons.android-library-base")
}

dependencies {
    implementation(project(":common:resources"))
    implementation(project(":common:di"))
}