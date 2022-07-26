package commons

import Dependencies
import Dependencies.TestAndroidDependencies
import Dependencies.TestDependencies

/**
 * Base Gradle file used by all Android module Gradle files to reduce repetition of commonly used
 * libraries
 */
tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    // "" version of implementation is required as below plugin is not applied:
    // id("com.android.<library/app>")


    "implementation"(Dependencies.AndroidX.APPCOMPAT)
    "implementation"(Dependencies.AndroidX.CORE_KTX)
    "implementation"(Dependencies.AndroidX.LIFECYCLE_RUNTIME_KTX)
    "implementation"(Dependencies.AndroidX.LIFECYCLE_VIEWMODEL)
    "implementation"(Dependencies.AndroidX.PALETTE)

    "implementation"(Dependencies.Compose.MATERIAL)
    "implementation"(Dependencies.Compose.RUNTIME)
    "implementation"(Dependencies.Compose.RUNTIME_LIVEDATA)
    "implementation"(Dependencies.Compose.UI)
    "implementation"(Dependencies.Compose.UI_TOOLING)
    "implementation"(Dependencies.Compose.UI_TOOLING_PREVIEW)
    "implementation"(Dependencies.Compose.NAVIGATION)
    "implementation"(Dependencies.Compose.LIFECYCLE_VIEWMODEL)

    "implementation"(Dependencies.Accompanist.SWIPE_TO_REFRESH)
    "implementation"(Dependencies.Accompanist.NAVIGATION_ANIMATION)
    "implementation"(Dependencies.Accompanist.WINDOW_INSETS)
    "implementation"(Dependencies.Accompanist.SYSTEM_UI_CONTROLLER)

    "implementation"(Dependencies.COIL)
    "implementation"(Dependencies.COIL_COMPOSE_EXTENSIONS)

    "implementation"(Dependencies.LOTTIE)

    "implementation"(Dependencies.TIMBER_ANDROID)

    "testImplementation"(TestDependencies.JUPITER_API)
    "testImplementation"(TestDependencies.JUPITER_ENGINE)
    "testImplementation"(TestDependencies.JUPITER_PARAMS)
    "testImplementation"(TestDependencies.MOCKITO)
    "testImplementation"(TestDependencies.MOCKITO_KOTLIN)

    "androidTestImplementation"(TestAndroidDependencies.ESPRESSO)
    "androidTestImplementation"(TestAndroidDependencies.ANNOTATION)

}
