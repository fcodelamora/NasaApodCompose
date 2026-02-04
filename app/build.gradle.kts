plugins {
    id("com.android.application")
    id("commons.android-shared-dependencies")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.mikepenz.aboutlibraries.plugin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.training.nasa.apod"

        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
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
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
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

    flavorDimensions += setOf("environment")

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
    implementation(project(":core:entities"))
    implementation(project(":core:usecases"))

    implementation(project(":common:resources"))
    implementation(project(":common:di"))

    // implementation(project(":provide:systems")) currently, no systems in use

    implementation(project(":features:gallery"))

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.activity.compose)

    implementation(libs.aboutlibraries.compose)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.android.compiler)

    // Required by Hilt
    implementation(project(":provide:repositories"))

    // Differentiate between real and mock dependencies.
    "productImplementation"(project(":provide:apis"))
    "qaImplementation"(project(":provide:apis"))
    "devImplementation"(project(":provide:mocks:apis"))
}
