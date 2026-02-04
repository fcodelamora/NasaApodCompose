plugins {
    id("commons.kotlin-library")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core:entities"))
    implementation(project(":core:apis"))
    implementation(project(":core:datasources"))
}
