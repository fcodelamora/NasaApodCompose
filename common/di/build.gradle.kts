plugins {
    id("commons.android-library-base")
}

dependencies {
    implementation(project(BuildModules.Core.REPOSITORIES))
    implementation(project(BuildModules.Core.USECASES))
}
