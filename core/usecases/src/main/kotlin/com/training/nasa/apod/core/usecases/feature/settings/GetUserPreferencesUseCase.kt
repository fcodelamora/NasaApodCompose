package com.training.nasa.apod.core.usecases.feature.settings

import com.training.nasa.apod.core.entities.UserPreferences
import com.training.nasa.apod.core.repository.IUserPreferencesRepository
import com.training.nasa.apod.core.usecases.IErrorView

class GetUserPreferencesUseCase(
    private val view: IGetUserPreferencesView,
    private val repository: IUserPreferencesRepository
) {
    suspend fun execute() {
        try {
            val prefs = repository.getUserPreferences()
            view.onUserPreferencesRetrieved(prefs)
        } catch (exception: Exception) {
            view.handleException(exception)
        }
    }
}

interface IGetUserPreferencesView : IErrorView {
    fun onUserPreferencesRetrieved(preferences: UserPreferences)
}
