plugins {
    id("commons.android-library")
}

android {

    val baseUrlKey = "API_BASE_URL"
    val baseUrlValue = "\"https://api.nasa.gov/planetary/\""

    val apiKeyKey = "API_KEY"
    val apiKeyLocalProperty = "apiKeys.nasaApiKey"

    flavorDimensions += setOf("environment")

    productFlavors {
        create("product") {
            dimension = "environment"
            buildConfigField("String", baseUrlKey, baseUrlValue)
            buildConfigField(
                "String",
                apiKeyKey,
                utils.getLocalProperty(apiKeyLocalProperty, project)
            )
        }
        create("qa") {
            dimension = "environment"
            buildConfigField("String", baseUrlKey, baseUrlValue)
            buildConfigField(
                "String",
                apiKeyKey,
                utils.getLocalProperty(apiKeyLocalProperty, project)
            )
        }
        create("dev") {
            dimension = "environment"
            buildConfigField("String", baseUrlKey, baseUrlValue)
            buildConfigField(
                "String",
                apiKeyKey,
                utils.getLocalProperty(apiKeyLocalProperty, project)
            )
        }
    }
}

dependencies {
    implementation(project(":core:repositories"))
    implementation(project(":core:apis"))
    implementation(project(":core:entities"))
    implementation(project(":core:datasources"))

    // Via Hilt injection
    implementation(project(":provide:datasources"))
}
