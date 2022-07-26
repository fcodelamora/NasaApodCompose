package commons

import Dependencies
import Dependencies.TestDependencies

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

dependencies {
    add("implementation", Dependencies.Kotlin.COROUTINES)

    add("implementation", Dependencies.TIMBER_JDK)

    "testImplementation"(TestDependencies.JUPITER_API)
    "testImplementation"(TestDependencies.JUPITER_ENGINE)
    "testImplementation"(TestDependencies.JUPITER_PARAMS)
    "testImplementation"(TestDependencies.MOCKITO)
    "testImplementation"(TestDependencies.MOCKITO_KOTLIN)
}