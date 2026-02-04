package com.training.nasa.apod.core.entities

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
            return SYSTEM
        }
    }
}
