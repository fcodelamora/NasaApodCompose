package com.training.nasa.apod.provide

import com.training.nasa.apod.core.datasources.IUserPreferencesDataSource
import com.training.nasa.apod.core.entities.UserPreferences
import com.training.nasa.apod.core.repository.IUserPreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val userPreferencesDataSource: IUserPreferencesDataSource
) : IUserPreferencesRepository {

    override suspend fun storeUserPreferences(preferences: UserPreferences) =
        withContext(Dispatchers.IO) {
            userPreferencesDataSource.saveUserPreferences(preferences)
        }

    override suspend fun getUserPreferences(): UserPreferences =
        withContext(Dispatchers.IO) {
            return@withContext userPreferencesDataSource.getUserPreferences()
        }
}
