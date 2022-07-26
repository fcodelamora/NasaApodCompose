plugins {
    id("commons.kotlin-library")
    kotlin("kapt")
}

dependencies {
    implementation(project(BuildModules.Core.ENTITIES))
}
