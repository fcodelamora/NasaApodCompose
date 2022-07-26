package com.training.nasa.apod.common.resources.viewmodels

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.training.nasa.apod.core.usecases.IProgressView

open class ProgressViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : ErrorViewModel(application, savedStateHandle),
    IProgressView {

    // Do not use saved state as it may lead to stuck progress view on canceled coroutines.
    private val _isProgressViewVisible: MutableState<Boolean> = mutableStateOf(false)
    val isProgressViewVisible: State<Boolean> = _isProgressViewVisible

    override fun showProgressView() {
        _isProgressViewVisible.value = true
    }

    override fun hideProgressView() {
        _isProgressViewVisible.value = false
    }
}
