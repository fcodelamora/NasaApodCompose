package commons

/**
 * Base Gradle file used by all com.android.library Gradle files to reduce repetition of shared
 * attributes.
 */
plugins {
    id("com.android.library")
    id("commons.android-shared-dependencies")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AndroidBuildConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AndroidBuildConfig.MIN_SDK_VERSION
        targetSdk = AndroidBuildConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    android.sourceSets.all {
        java.srcDir("src/$name/kotlin")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Versions.KOTLIN_COMPILER_EXTENSION
    }

}

dependencies {
    implementation(Dependencies.AndroidX.HILT)
    kapt(Dependencies.AnnotationProcessors.HILT)
    kapt(Dependencies.AnnotationProcessors.HILT_ANDROID)
}