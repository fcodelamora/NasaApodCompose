

plugins {
    id("commons.kotlin-library")
    kotlin("kapt")
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)

    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.moshi)

    kapt(libs.moshi.kotlin.codegen)

    // Testing
    kaptTest(libs.moshi.kotlin.codegen)
}
