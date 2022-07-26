package com.training.nasa.apod.core.entities

import com.training.nasa.apod.core.entities.MediaType.IMAGE
import com.training.nasa.apod.core.entities.MediaType.UNKNOWN
import com.training.nasa.apod.core.entities.MediaType.VIDEO
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

data class PictureOfTheDay(
    val dateString: String = "",
    val explanation: String = "",
    val hdUrl: String? = null,
    val mediaType: MediaType = IMAGE,
    val serviceVersion: String = "",
    val title: String = "",
    val url: String = "",
    val copyright: String? = null,
    private val videoThumbnail: String? = null
) : Serializable {
    companion object {
        const val EMPTY_PICTURE_OF_THE_DAY_ID = 0
    }

    val thumbnail: String? = when (mediaType) {
        VIDEO -> videoThumbnail
        IMAGE -> url
        UNKNOWN -> null
    }

    val date: LocalDate = try {
        SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).parse(dateString)
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } catch (e: Exception) {
        Date().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    val dateAsSimpleString: String? by lazy {
        // FIXME: Currently forcing an English Locale, consider a different date approach
        // when translated articles and multi-language support are provided.
        // Ex. date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        date.format(DateTimeFormatter.ofPattern("dd - MMM - yyyy", Locale.ENGLISH))
    }

    val dayOfMonthFormatted: String = date.dayOfMonth.toString()

    val id = dateString.replace("-", "").toIntOrNull() ?: EMPTY_PICTURE_OF_THE_DAY_ID

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PictureOfTheDay

        if (id != other.id) return false

        return true
    }
}
