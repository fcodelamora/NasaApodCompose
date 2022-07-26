package com.training.nasa.apod.core.usecases

import com.training.nasa.apod.core.entities.ErrorViewData

interface IErrorView {
    fun showErrorView(errorViewData: ErrorViewData)
    fun handleException(exception: Exception)
}
