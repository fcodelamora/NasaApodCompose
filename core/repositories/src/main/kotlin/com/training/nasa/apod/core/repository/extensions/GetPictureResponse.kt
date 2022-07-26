package com.training.nasa.apod.core.repository.extensions

import com.training.nasa.apod.core.api.response.GetPictureResponse
import com.training.nasa.apod.core.entities.MediaType
import com.training.nasa.apod.core.entities.PictureOfTheDay

/**
 * Convert the API Response into the entity that is actually used within the other modules of the app.
 * It is expected for XxxxResponse class to not leave :repository
 */
fun GetPictureResponse.toPictureOfTheDay(): PictureOfTheDay =
    PictureOfTheDay(
        dateString = date,
        mediaType = when (mediaType) {
            "image" -> MediaType.IMAGE
            // The webserver uses "video" for both, videos and links
            "video" -> if (thumbnailUrl.isNullOrBlank()) MediaType.UNKNOWN else MediaType.VIDEO
            else -> MediaType.UNKNOWN
        },
        title = title,
        explanation = explanation,
        url = url,
        hdUrl = hdUrl,
        copyright = copyright,
        videoThumbnail = thumbnailUrl,
        serviceVersion = serviceVersion
    )

fun List<GetPictureResponse>.toPictureOfTheDayList(): List<PictureOfTheDay> =
    map { it.toPictureOfTheDay() }
