plugins {
    id("commons.android-library-base")
}

dependencies {
    implementation(project(":core:repositories"))
    implementation(project(":core:usecases"))
}
