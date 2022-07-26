plugins {
    id("com.android.application")
    id("commons.android-shared-dependencies")
    kotlin("android")
    kotlin("kapt")
    id("com.mikepenz.aboutlibraries.plugin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AndroidBuildConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = AndroidBuildConfig.APPLICATION_ID

        minSdk = AndroidBuildConfig.MIN_SDK_VERSION
        targetSdk = AndroidBuildConfig.TARGET_SDK_VERSION
        versionCode = AndroidBuildConfig.VERSION_CODE
        versionName = AndroidBuildConfig.VERSION_NAME
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../android_debug.jks")
            keyAlias = "androiddebugkey"
            storePassword = "android"
            keyPassword = "android"
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Versions.KOTLIN_COMPILER_EXTENSION
    }

    android.sourceSets.all {
        java.srcDir("src/$name/kotlin")
    }

    buildTypes {
        getByName("release") {
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            isDebuggable = false
            isMinifyEnabled = true
        }

        getByName("debug") {
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            isDebuggable = true
            isMinifyEnabled = false
        }
    }

    flavorDimensions += setOf(AndroidBuildConfig.ProductDimensions.ENVIRONMENT)

    productFlavors {

        create("product") {
        }
        create("qa") {
            applicationIdSuffix = ".qa"
            signingConfig = signingConfigs.getByName("debug")
        }
        create("dev") {
            applicationIdSuffix = ".dev"
            // resConfigs("en", "xxhdpi")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    hilt {
        enableAggregatingTask = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(BuildModules.Core.ENTITIES))
    implementation(project(BuildModules.Core.USECASES))

    implementation(project(BuildModules.Common.RESOURCES))
    implementation(project(BuildModules.Common.DEPENDENCY_INJECTION))

    // implementation(project(BuildModules.Provide.SYSTEMS)) currently, no systems in use

    implementation(project(BuildModules.Features.GALLERY))

    implementation(Dependencies.Compose.HILT_NAVIGATION)
    implementation(Dependencies.Compose.ACTIVITY)

    implementation(Dependencies.ABOUT_LIBRARIES_UI)

    implementation(Dependencies.AndroidX.HILT)
    kapt(Dependencies.AnnotationProcessors.HILT)
    kapt(Dependencies.AnnotationProcessors.HILT_ANDROID)

    // Required by Hilt
    implementation(project(BuildModules.Provide.REPOSITORIES))

    // Differentiate between real and mock dependencies.
    "productImplementation"(project(BuildModules.Provide.APIS))
    "qaImplementation"(project(BuildModules.Provide.APIS))
    "devImplementation"(project(BuildModules.Provide.Mocks.APIS))
}
