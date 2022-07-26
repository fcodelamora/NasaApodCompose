package com.training.nasa.apod.core.entities

import java.io.Serializable

data class ErrorViewData(
    val title: String? = null,
    private val message: String,
    private val errorCode: String? = null
) : Serializable {
    val errorMessage: String
        get() {
            return if (errorCode == null) {
                message
            } else {
                "$message\n$errorCode"
            }
        }
}
