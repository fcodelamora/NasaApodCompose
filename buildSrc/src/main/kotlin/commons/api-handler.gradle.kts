package commons

/**
 * Base Gradle file used by all API related Gradle files to reduce repetition of shared
 * attributes. This file also provides additional BuildConfig fields required to initialize the
 * APIs
 */
plugins {
    id("commons.android-library-base")
}

android {
    val baseUrlKey = "API_BASE_URL"
    val baseUrlValue = "\"https://api.nasa.gov/planetary/\""


    flavorDimensions += setOf("environment")

    productFlavors {
        create("product") {
            dimension = "environment"
            buildConfigField("String", baseUrlKey, baseUrlValue)
        }
        create("qa") {
            dimension = "environment"
            buildConfigField("String", baseUrlKey, baseUrlValue)
        }
        create("dev") {
            dimension = "environment"
            buildConfigField("String", baseUrlKey, baseUrlValue)
        }
    }
}

dependencies {
    implementation(project(":core:apis"))
}