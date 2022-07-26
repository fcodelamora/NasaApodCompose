object Dependencies {

    object Versions {
        // Kotlin
        const val COROUTINES = "1.6.2"

        // https://developer.android.com/jetpack/androidx/releases/compose-runtime
        // https://developer.android.com/jetpack/androidx/releases/compose-kotlin
        // Compose
        const val COMPOSE = "1.1.0"
        const val KOTLIN_COMPILER_EXTENSION = COMPOSE
        const val ACTIVITY = "1.3.1"
        const val NAVIGATION_COMPOSE = "2.4.2"
        const val LIFECYCLE_VIEWMODEL_COMPOSE = "2.4.1"
        const val HILT_NAVIGATION = "1.0.0"

        // Accompanist
        const val ACCOMPANIST = "0.23.1"

        // AndroidX
        const val ANNOTATION = "1.2.0"
        const val APPCOMPAT = "1.4.2"
        const val ROOM = "2.4.0"
        const val NAVIGATION = "2.3.0"
        const val LIFECYCLE = "2.4.1"
        const val CORE_KTX = "1.8.0"
        const val WORK_MANAGER = "2.5.0"
        const val HILT = "2.42"
        const val PALETTE = "1.0.0"
        const val PALETTE_KTX = "1.0.0"
        const val ANDROIDX_COMPOSE = "1.1.1"

        // Google
        const val TRANSLATE = "16.1.2"

        // Others
        const val TIMBER = "5.0.0-SNAPSHOT"
        const val RETROFIT = "2.9.0"
        const val LOGGING = "4.10.0"
        const val MOSHI = "1.13.0"
        const val COIL = "2.1.0"
        const val ABOUT_LIBRARIES = "10.1.0" // Update buildSrc too
        const val LOTTIE = "5.2.0"


        // Tests
        const val JUPITER = "5.8.2"
        const val MOCKITO = "4.6.1"
        const val MOCKITO_KOTLIN = "4.0.0"
        const val ESPRESSO = "3.4.0"
    }

    object Kotlin {
        const val COROUTINES =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    }

    object Compose {
        const val UI = "androidx.compose.ui:ui:${Versions.ANDROIDX_COMPOSE}"
        const val UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.ANDROIDX_COMPOSE}"
        const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.ANDROIDX_COMPOSE}"
        const val ACTIVITY = "androidx.activity:activity-compose:${Versions.ACTIVITY}"
        const val NAVIGATION =
            "androidx.navigation:navigation-compose:${Versions.NAVIGATION_COMPOSE}"

        const val MATERIAL = "androidx.compose.material:material:${Versions.ANDROIDX_COMPOSE}"
        const val RUNTIME = "androidx.compose.runtime:runtime:${Versions.ANDROIDX_COMPOSE}"
        const val RUNTIME_LIVEDATA = "androidx.compose.runtime:runtime-livedata:${Versions.ANDROIDX_COMPOSE}"
        const val LIFECYCLE_VIEWMODEL =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.LIFECYCLE_VIEWMODEL_COMPOSE}"

        const val HILT_NAVIGATION =
            "androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAVIGATION}"
    }


    object Accompanist {
        const val SWIPE_TO_REFRESH =
            "com.google.accompanist:accompanist-swiperefresh:${Versions.ACCOMPANIST}"
        const val SYSTEM_UI_CONTROLLER =
            "com.google.accompanist:accompanist-systemuicontroller:${Versions.ACCOMPANIST}"
        const val NAVIGATION_ANIMATION =
            "com.google.accompanist:accompanist-navigation-animation:${Versions.ACCOMPANIST}"
        const val WINDOW_INSETS =
            "com.google.accompanist:accompanist-insets:${Versions.ACCOMPANIST}"
    }


    object AndroidX {
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
        const val LIFECYCLE_VIEWMODEL =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
        const val LIFECYCLE_RUNTIME_KTX =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
        const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
        const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val PALETTE = "androidx.palette:palette-ktx:${Versions.PALETTE_KTX}"
        const val ROOM = "androidx.room:room-runtime:${Versions.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
        const val WORK_MANAGER = "androidx.work:work-runtime:${Versions.WORK_MANAGER}"
        const val WORK_MANAGER_KTX = "androidx.work:work-runtime-ktx:${Versions.WORK_MANAGER}"
    }


    object Google {
        const val TRANSLATE = "com.google.mlkit:translate:${Versions.TRANSLATE}"
    }

    //Others
    const val TIMBER_ANDROID = "com.jakewharton.timber:timber-android:${Versions.TIMBER}"
    const val TIMBER_JDK = "com.jakewharton.timber:timber-jdk:${Versions.TIMBER}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"

    const val OKHTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:${Versions.LOGGING}"
    const val MOSHI = "com.squareup.moshi:moshi:${Versions.MOSHI}"
    const val MOSHI_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"

    const val COIL = "io.coil-kt:coil:${Versions.COIL}"
    const val COIL_COMPOSE_EXTENSIONS = "io.coil-kt:coil-compose:${Versions.COIL}"

    const val ABOUT_LIBRARIES_UI = "com.mikepenz:aboutlibraries-compose:${Versions.ABOUT_LIBRARIES}"

    const val LOTTIE = "com.airbnb.android:lottie-compose:${Versions.LOTTIE}"

    /**
     * Project annotation processor dependencies, makes it easy to include external binaries or
     * other library modules to build.
     */
    object AnnotationProcessors {
        const val HILT_ANDROID = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
        const val HILT = "com.google.dagger:hilt-compiler:${Versions.HILT}"
        const val ROOM = "androidx.room:room-compiler:${Versions.ROOM}"
    }

    object TestAndroidDependencies {
        const val ANNOTATION = "androidx.annotation:annotation:${Versions.ANNOTATION}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
    }

    object TestDependencies {
        const val JUPITER_API = "org.junit.jupiter:junit-jupiter-api:${Versions.JUPITER}"
        const val JUPITER_ENGINE = "org.junit.jupiter:junit-jupiter-engine:${Versions.JUPITER}"
        const val JUPITER_PARAMS = "org.junit.jupiter:junit-jupiter-params:${Versions.JUPITER}"

        const val MOCKITO = "org.mockito:mockito-core:${Versions.MOCKITO}"
        const val MOCKITO_KOTLIN = "org.mockito.kotlin:mockito-kotlin:${Versions.MOCKITO_KOTLIN}"
    }
}
