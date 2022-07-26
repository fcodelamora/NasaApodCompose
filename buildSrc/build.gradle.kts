plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    maven("https://ci.android.com/builds/submitted/5837096/androidx_snapshot/latest/repository")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

private object PluginsVersions {
    const val GRADLE_ANDROID = "7.3.0-beta04"
    const val KOTLIN = "1.6.10"
    const val HILT = "2.41"
    const val ABOUT_LIBRARIES = "10.1.0"
}

dependencies {

    implementation("com.android.tools.build:gradle:${PluginsVersions.GRADLE_ANDROID}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")

    implementation("com.google.dagger:hilt-android-gradle-plugin:${PluginsVersions.HILT}")
    implementation("com.mikepenz.aboutlibraries.plugin:aboutlibraries-plugin:${PluginsVersions.ABOUT_LIBRARIES}")
}
