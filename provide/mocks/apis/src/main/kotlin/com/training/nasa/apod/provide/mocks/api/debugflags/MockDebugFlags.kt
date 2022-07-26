package com.training.nasa.apod.provide.mocks.api.debugflags

import com.training.nasa.apod.core.api.error.ApiException
import com.training.nasa.apod.core.api.error.DefaultApiError
import java.net.UnknownHostException
import javax.inject.Singleton

@Singleton
data class MockDebugFlags(
    var delayInMillis: Long = 250,
    var apiErrorType: ApiErrorType = ApiErrorType.NO_ERROR,
    var shouldDisplayAdvertisement: Boolean = true
)

enum class ApiErrorType(val displayName: String) {
    NO_ERROR("No Error"),
    UNKNOWN_HOST_ERROR("Could not reach the server"),
    ERROR("Generic error"),
    UNKNOWN_ERROR("Unknown Error");

    fun handleError() {
        when (this) {
            NO_ERROR -> return
            UNKNOWN_HOST_ERROR -> throw UnknownHostException()
            ERROR -> throw ApiException(422, DefaultApiError(422, "Error", "v1"))
            UNKNOWN_ERROR -> throw ApiException(
                500,
                DefaultApiError(500, "An unknown Error has occurred", "v1")
            )
        }
    }

    fun toIndex(): Int {
        for (errorType in values()) {
            if (this == errorType) {
                return errorType.ordinal
            }
        }
        return 0
    }

    companion object {
        fun fromIndex(index: Int): ApiErrorType {
            return values().first { it.ordinal == index }
        }
    }
}
