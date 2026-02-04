package commons

/**
 * Default Gradle file used by modules in `:feature`
 */
plugins {
    id("commons.android-library")
}

dependencies {
    implementation(project(":core:usecases"))
    implementation(project(":core:entities"))
}
