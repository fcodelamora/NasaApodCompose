package com.training.nasa.apod.core.api.error

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiServiceAccessException(
    @field:Json(name = "code")
    val code: String?,

    @field:Json(name = "message")
    val errorMessage: String?
)
