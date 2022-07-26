package com.training.nasa.apod.core.api.error

import java.io.IOException

class ApiException(
    val statusCode: Int,
    val apiError: DefaultApiError? = null
) : IOException()
