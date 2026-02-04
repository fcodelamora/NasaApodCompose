package com.training.nasa.apod.viewmodels

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.training.nasa.apod.common.di.UseCaseProvider
import com.training.nasa.apod.common.resources.ui.NasaApodScreens
import com.training.nasa.apod.common.resources.utils.calculatePictureAccentColor
import com.training.nasa.apod.common.resources.utils.mutableStateOf
import com.training.nasa.apod.common.resources.viewmodels.ErrorViewModel
import com.training.nasa.apod.core.entities.PictureOfTheDay
import com.training.nasa.apod.core.entities.UserPreferences
import com.training.nasa.apod.core.usecases.feature.settings.IGetUserPreferencesView
import com.training.nasa.apod.core.usecases.feature.settings.IStoreUserPreferencesView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val useCaseProvider: UseCaseProvider
) : ErrorViewModel(application, savedStateHandle),
    IStoreUserPreferencesView,
    IGetUserPreferencesView {

    val startScreenRoute: String = NasaApodScreens.GALLERY.name

    var currentDestinationRoute by savedStateHandle.mutableStateOf<String?>(startScreenRoute)

    var userPreferences by savedStateHandle.mutableStateOf(UserPreferences())
        private set

    // FIXME: This may not be required once the information is persisted via Android Room
    var pictureOfTheDay by savedStateHandle.mutableStateOf<PictureOfTheDay?>(null)
        private set

    // FIXME:  Making an initial value that is guaranteed to differ from any other value.
    // This is done to make sure it is updated, and not ignored by been considered
    // "the same value as the previous one".
    // If this is not done, the first launch on this screen does not update colors by image.
    private val defaultFallbackColor = Color.LightGray.copy(alpha = 0.99f)

    // Color is not Serializable, saving the value instead.
    private var _pictureOfTheDayDominantColorValue by savedStateHandle.mutableStateOf<String?>(
        defaultFallbackColor.value.toString()
    )
    val pictureOfTheDayDominantColor: Color
        get() = _pictureOfTheDayDominantColorValue?.toULong()?.let { Color(it) }
            ?: defaultFallbackColor

    fun updatePictureOfTheDay(pictureOFTheDay: PictureOfTheDay) {
        this.pictureOfTheDay = pictureOFTheDay
    }

    // Used by the coil painter image load Success call.
    fun updateDynamicColor(drawable: Drawable) {
        drawable.calculatePictureAccentColor(viewModelScope) { color ->
            _pictureOfTheDayDominantColorValue = color.value.toString()
        }
    }

    // Used by the Settings screen to skip calculations.
    fun updateDynamicColor(color: Color) {
        _pictureOfTheDayDominantColorValue = color.value.toString()
    }

    fun updateUserPreferences(preferences: UserPreferences) {
        userPreferences = preferences
        viewModelScope.launch {
            Timber.d("updateUserPreferences: $userPreferences")
            val useCase =
                useCaseProvider.provideStoreUserPreferencesUseCase(this@MainViewModel)
            useCase.execute(userPreferences)
        }
    }

    fun loadUserPreferences() {
        viewModelScope.launch {
            val useCase = useCaseProvider.provideLoadUserPreferencesUseCase(this@MainViewModel)
            useCase.execute()
        }
    }

    override fun onUserPreferencesRetrieved(preferences: UserPreferences) {
        userPreferences = preferences
    }
}
