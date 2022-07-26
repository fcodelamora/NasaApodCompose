

plugins {
    id("commons.kotlin-library")
    kotlin("kapt")
}

dependencies {
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER)

    implementation(Dependencies.OKHTTP_LOGGING)

    implementation(Dependencies.MOSHI)

    kapt(Dependencies.MOSHI_CODEGEN)

    // Testing
    kaptTest(Dependencies.MOSHI_CODEGEN)
}
