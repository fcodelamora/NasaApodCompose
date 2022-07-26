plugins {
    id("commons.kotlin-library")
    kotlin("kapt")
}

dependencies {
    implementation(project(BuildModules.Core.ENTITIES))
    implementation(project(BuildModules.Core.REPOSITORIES))
    implementation(project(BuildModules.Core.SYSTEMS))
}
