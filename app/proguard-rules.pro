# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#Remove below inspection as it is currently triggering false errors.(IDE Bug?)
#noinspection ShrinkerUnresolvedReference

#Adding Kotlin and Android rules to prevent serialization on savedStateHandle updates/restore to crash
# Kotlin
-keep class * implements kotlin.Lazy {*;}
# Android
-keepnames class * implements android.os.Parcelable { public static final ** CREATOR; }

# App Serializable entities saved on savedStateHandle by the Delegate.
# The class name is used as a Key to save/restore
# :core:entities
-keep class com.training.nasa.apod.core.entities.AppTheme{*;}
-keep class com.training.nasa.apod.core.entities.PictureOfTheDay{*;}
-keep class com.training.nasa.apod.core.entities.MediaType{*;}
-keep class com.training.nasa.apod.core.entities.WeekDays{*;}

