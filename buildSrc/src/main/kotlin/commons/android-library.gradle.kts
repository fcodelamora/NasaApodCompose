package commons

/**
 * Default Gradle file used by modules in `:provide` that act as Android libraries.
 */
plugins {
    id("commons.android-library-base")
}

dependencies {
    implementation(project(BuildModules.Common.RESOURCES))
    implementation(project(BuildModules.Common.DEPENDENCY_INJECTION))
}