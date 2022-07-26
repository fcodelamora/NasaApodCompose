package commons

import BuildModules

/**
 * Default Gradle file used by modules in `:feature`
 */
plugins {
    id("commons.android-library")
}

dependencies {
    implementation(project(BuildModules.Core.USECASES))
    implementation(project(BuildModules.Core.ENTITIES))
}
