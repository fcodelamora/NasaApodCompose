pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
    plugins {
        id("com.android.application") version "7.1.0"
        id("com.android.library") version "7.1.0"
        id("org.jetbrains.kotlin.android") version "1.6.10"
        id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}
rootProject.name = "NasaApodMultimoduleCompose"

include(

    ":app",

    // Core
    ":core:apis",
    ":core:datasources",
    ":core:entities",
    ":core:repositories",
    ":core:systems",
    ":core:usecases",

    // Provide
    ":provide:apis",
    ":provide:repositories",
    ":provide:datasources",

    // Mocks
    ":provide:mocks:apis",

    // Features
    ":features:gallery",

    // Common
    ":common:resources",
    ":common:di",

)
