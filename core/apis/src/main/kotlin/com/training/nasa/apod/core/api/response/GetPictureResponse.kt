package com.training.nasa.apod.core.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetPictureResponse(
    @field:Json(name = "date")
    val date: String,

    @field:Json(name = "explanation")
    val explanation: String,

    @field:Json(name = "hdurl")
    val hdUrl: String?,

    @field:Json(name = "media_type")
    val mediaType: String,

    @field:Json(name = "service_version")
    val serviceVersion: String,

    @field:Json(name = "title")
    val title: String,

    @field:Json(name = "url")
    val url: String,

    @field:Json(name = "copyright")
    val copyright: String?,

    @field:Json(name = "thumbnail_url")
    val thumbnailUrl: String?
)
