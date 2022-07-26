plugins {
    id("commons.android-library-base")
}

dependencies {
    implementation(project(BuildModules.Core.ENTITIES))
    implementation(project(BuildModules.Core.USECASES))
}
