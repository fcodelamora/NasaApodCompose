package com.training.nasa.apod.core.datasources

import com.training.nasa.apod.core.entities.UserPreferences

interface IUserPreferencesDataSource {
    suspend fun saveUserPreferences(preferences: UserPreferences)
    suspend fun getUserPreferences(): UserPreferences
}
