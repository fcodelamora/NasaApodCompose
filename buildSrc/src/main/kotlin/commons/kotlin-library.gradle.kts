package commons

/**
 * Base Gradle file used by all Kotlin Gradle files (Ex. Modules at `:core`) to reduce repetition
 * of shared attributes.
 */
plugins {
    id("kotlin")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    "implementation"(libs.findLibrary("kotlinx-coroutines-core").get())

    "implementation"(libs.findLibrary("timber").get())

    "testImplementation"(libs.findLibrary("junit-jupiter-api").get())
    "testImplementation"(libs.findLibrary("junit-jupiter-engine").get())
    "testImplementation"(libs.findLibrary("junit-jupiter-params").get())
    "testImplementation"(libs.findLibrary("mockito-core").get())
    "testImplementation"(libs.findLibrary("mockito-kotlin").get())
}