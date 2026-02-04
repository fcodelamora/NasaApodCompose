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

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

android {
    compileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()

    defaultConfig {
        minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
        targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()

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
        kotlinCompilerExtensionVersion = libs.findVersion("compose").get().requiredVersion
    }

}

dependencies {
    implementation(libs.findLibrary("hilt-android").get())
    kapt(libs.findLibrary("hilt-compiler").get())
    kapt(libs.findLibrary("hilt-android-compiler").get())
}