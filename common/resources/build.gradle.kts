plugins {
    id("commons.android-library-base")
}

dependencies {
    implementation(project(":core:entities"))
    implementation(project(":core:usecases"))
}
