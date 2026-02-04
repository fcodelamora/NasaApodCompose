package commons

/**
 * Base Gradle file used by all Android module Gradle files to reduce repetition of commonly used
 * libraries
 */
tasks.withType<Test> {
    useJUnitPlatform()
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    // "" version of implementation is required as below plugin is not applied:
    // id("com.android.<library/app>")


    "implementation"(libs.findLibrary("androidx-appcompat").get())
    "implementation"(libs.findLibrary("androidx-core-ktx").get())
    "implementation"(libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
    "implementation"(libs.findLibrary("androidx-lifecycle-viewmodel-ktx").get())
    "implementation"(libs.findLibrary("androidx-palette-ktx").get())

    "implementation"(libs.findLibrary("androidx-compose-material").get())
    "implementation"(libs.findLibrary("androidx-compose-runtime").get())
    "implementation"(libs.findLibrary("androidx-compose-runtime-livedata").get())
    "implementation"(libs.findLibrary("androidx-compose-ui").get())
    "implementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
    "implementation"(libs.findLibrary("androidx-compose-ui-tooling-preview").get())
    "implementation"(libs.findLibrary("androidx-navigation-compose").get())
    "implementation"(libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())

    "implementation"(libs.findLibrary("accompanist-swiperefresh").get())
    "implementation"(libs.findLibrary("accompanist-navigation-animation").get())
    "implementation"(libs.findLibrary("accompanist-insets").get())
    "implementation"(libs.findLibrary("accompanist-systemuicontroller").get())

    "implementation"(libs.findLibrary("coil").get())
    "implementation"(libs.findLibrary("coil-compose").get())

    "implementation"(libs.findLibrary("lottie-compose").get())

    "implementation"(libs.findLibrary("timber").get())

    "testImplementation"(libs.findLibrary("junit-jupiter-api").get())
    "testImplementation"(libs.findLibrary("junit-jupiter-engine").get())
    "testImplementation"(libs.findLibrary("junit-jupiter-params").get())
    "testImplementation"(libs.findLibrary("mockito-core").get())
    "testImplementation"(libs.findLibrary("mockito-kotlin").get())

    "androidTestImplementation"(libs.findLibrary("androidx-test-espresso-core").get())
    "androidTestImplementation"(libs.findLibrary("androidx-annotation").get())

}
