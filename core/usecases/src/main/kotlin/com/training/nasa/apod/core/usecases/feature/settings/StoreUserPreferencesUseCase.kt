package com.training.nasa.apod.core.usecases.feature.settings

import com.training.nasa.apod.core.entities.UserPreferences
import com.training.nasa.apod.core.repository.IUserPreferencesRepository
import com.training.nasa.apod.core.usecases.IErrorView

// FIXME Add TEST
class StoreUserPreferencesUseCase(
    private val view: IStoreUserPreferencesView,
    private val repository: IUserPreferencesRepository
) {
    suspend fun execute(preferences: UserPreferences) {
        try {
            repository.storeUserPreferences(preferences)
        } catch (exception: Exception) {
            view.handleException(exception)
        }
    }
}

interface IStoreUserPreferencesView : IErrorView
