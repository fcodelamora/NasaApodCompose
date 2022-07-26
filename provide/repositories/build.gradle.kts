plugins {
    id("commons.android-library")
}

android {

    val baseUrlKey = "API_BASE_URL"
    val baseUrlValue = "\"https://api.nasa.gov/planetary/\""

    val apiKeyKey = "API_KEY"
    val apiKeyLocalProperty = "apiKeys.nasaApiKey"

    flavorDimensions += setOf(AndroidBuildConfig.ProductDimensions.ENVIRONMENT)

    productFlavors {
        create("product") {
            dimension = AndroidBuildConfig.ProductDimensions.ENVIRONMENT
            buildConfigField("String", baseUrlKey, baseUrlValue)
            buildConfigField(
                "String",
                apiKeyKey,
                utils.getLocalProperty(apiKeyLocalProperty, project)
            )
        }
        create("qa") {
            dimension = AndroidBuildConfig.ProductDimensions.ENVIRONMENT
            buildConfigField("String", baseUrlKey, baseUrlValue)
            buildConfigField(
                "String",
                apiKeyKey,
                utils.getLocalProperty(apiKeyLocalProperty, project)
            )
        }
        create("dev") {
            dimension = AndroidBuildConfig.ProductDimensions.ENVIRONMENT
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
    implementation(project(BuildModules.Core.REPOSITORIES))
    implementation(project(BuildModules.Core.APIS))
    implementation(project(BuildModules.Core.ENTITIES))
    implementation(project(BuildModules.Core.DATASOURCES))

    // Via Hilt injection
    implementation(project(BuildModules.Provide.DATASOURCES))
}
