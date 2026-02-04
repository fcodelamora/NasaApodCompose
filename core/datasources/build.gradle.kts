plugins {
    id("commons.kotlin-library")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core:entities"))
}
