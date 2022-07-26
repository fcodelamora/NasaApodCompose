package com.training.nasa.apod.core.repository

import com.training.nasa.apod.core.entities.UserPreferences

interface IUserPreferencesRepository {
    suspend fun storeUserPreferences(preferences: UserPreferences)
    suspend fun getUserPreferences(): UserPreferences
}
