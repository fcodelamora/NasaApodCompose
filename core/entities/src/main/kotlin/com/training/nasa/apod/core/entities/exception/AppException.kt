@file:Suppress("SpellCheckingInspection")

package com.training.nasa.apod.core.entities.exception

sealed class AppException(val messageForApplication: String) : Exception(messageForApplication) {
    object UnknownException : AppException("Network error or another unknown issue")

    object ExternalBrowserLaunchException : AppException("Could not launch external browser")

    data class GeneralApiException(
        override val messageFromApi: String,
        override val errorCode: Int
    ) : AppException(messageFromApi), IGeneralApiException

    interface IGeneralApiException {
        val messageFromApi: String
        val errorCode: Int
    }
}
