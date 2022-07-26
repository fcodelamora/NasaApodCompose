package com.training.nasa.apod.features.gallery.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.training.nasa.apod.common.resources.viewmodels.ErrorViewModel
import com.training.nasa.apod.core.entities.ErrorViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import timber.log.debug
import javax.inject.Inject

@HiltViewModel
class FullImageViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle
) : ErrorViewModel(application, savedStateHandle) {
    fun onImageError() {
        Timber.debug { "onImageError" }
        showErrorView(
            ErrorViewData(
                title = "Error loading image",
                message = "There was an error loading this image, please try again later."
            )
        )
    }
}
