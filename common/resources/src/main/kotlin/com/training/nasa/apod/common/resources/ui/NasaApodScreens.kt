package com.training.nasa.apod.common.resources.ui

enum class NasaApodScreens {
    GALLERY,
    IMAGE_DETAILS,
    IMAGE_VIEWER,
    SETTINGS,
    LICENSES;

    companion object {
        fun fromRoute(route: String?): NasaApodScreens =
            when (route?.substringBefore("/")) {
                GALLERY.name -> GALLERY
                IMAGE_DETAILS.name -> IMAGE_DETAILS
                IMAGE_VIEWER.name -> IMAGE_VIEWER
                SETTINGS.name -> SETTINGS
                LICENSES.name -> LICENSES
                null -> GALLERY
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
