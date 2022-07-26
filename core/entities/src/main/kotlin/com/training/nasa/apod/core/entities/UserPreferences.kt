package com.training.nasa.apod.core.entities

import java.io.Serializable

data class UserPreferences(
    val selectedTheme: AppTheme = AppTheme.DYNAMIC,
    val showBackButtonOnImages: Boolean = true,
) : Serializable
