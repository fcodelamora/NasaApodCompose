package com.training.nasa.apod.core.entities

import timber.log.Timber
import timber.log.debug

enum class AppTheme(val id: Int) {
    SYSTEM(0),
    LIGHT(1),
    RED(2),
    DARK(3),
    DYNAMIC(4);

    companion object {
        fun getFromId(themeId: Int): AppTheme {
            values().forEach { theme ->
                if (theme.id == themeId) {
                    return theme
                }
            }
            Timber.debug { "Invalid theme id received, fallback to SYSTEM" }
            return SYSTEM
        }
    }
}
