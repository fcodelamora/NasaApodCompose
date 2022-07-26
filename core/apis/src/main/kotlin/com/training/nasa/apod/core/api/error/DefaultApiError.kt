package com.training.nasa.apod.core.api.error

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DefaultApiError(
    @field:Json(name = "code")
    val code: Int?,

    @field:Json(name = "msg")
    val errorMessage: String?,

    @field:Json(name = "service_version")
    val serviceVersion: String?,

    @field:Json(name = "error")
    val serviceError: ApiServiceAccessException? = null
)
