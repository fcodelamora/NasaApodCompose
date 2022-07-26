package com.training.nasa.apod.core.repository.extensions

import com.training.nasa.apod.core.api.error.ApiException
import com.training.nasa.apod.core.entities.exception.AppException

/**
 * Default Behavior for General API Exceptions
 */
fun ApiException.toApplicationException(): AppException {
    // Different kinds of exceptions are expected to be added and handled later
    apiError?.let {
        return AppException.GeneralApiException(
            it.errorMessage ?: it.serviceError?.errorMessage ?: "Error Code: $statusCode",
            statusCode
        )
    }

    return AppException.UnknownException
}
