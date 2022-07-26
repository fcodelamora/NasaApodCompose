package com.training.nasa.apod.common.resources.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.training.nasa.apod.common.resources.BuildConfig
import com.training.nasa.apod.common.resources.R
import com.training.nasa.apod.common.resources.utils.mutableStateOf
import com.training.nasa.apod.core.entities.ErrorViewData
import com.training.nasa.apod.core.entities.exception.AppException
import com.training.nasa.apod.core.usecases.IErrorView
import timber.log.Timber
import timber.log.debug

open class ErrorViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application),
    IErrorView {

    var currentError by savedStateHandle.mutableStateOf<ErrorViewData?>(null)

    override fun showErrorView(errorViewData: ErrorViewData) {
        Timber.debug { "showErrorView" }
        currentError = errorViewData
    }

    override fun handleException(exception: Exception) {
        Timber.debug { "handleException: $exception" }
        val error = when (exception) {
            is AppException.GeneralApiException -> {
                ErrorViewData(
                    getString(R.string.error_title_server_error),
                    exception.messageForApplication,
                    exception.errorCode.toString()
                )
            }
            is AppException -> {
                ErrorViewData(
                    getString(R.string.error_title_application_error),
                    exception.messageForApplication
                )
            }
            else -> {
                ErrorViewData(
                    getString(R.string.error_title_unknown_error),
                    getString(R.string.error_message_unknown_error)
                )
            }
        }

        currentError = if (BuildConfig.DEBUG) {
            error.copy(message = "${error.errorMessage}\n\n$exception")
        } else {
            error
        }
    }

    fun onErrorHandled() {
        currentError = null
    }

    protected fun getString(resId: Int) =
        getApplication<Application>().getString(resId)
}
