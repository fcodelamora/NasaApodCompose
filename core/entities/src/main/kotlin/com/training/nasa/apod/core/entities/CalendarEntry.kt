package com.training.nasa.apod.core.entities

import java.io.Serializable
import java.time.LocalDate

sealed class CalendarEntry : Serializable {
    class Day(
        val date: LocalDate,
        val pictureOfTheDay: PictureOfTheDay
    ) : CalendarEntry()

    sealed class Util : CalendarEntry() {
        sealed class EmptyDay : Util() {
            object PastDay : EmptyDay()
            object FutureDay : EmptyDay()
        }

        object Padding : Util()
    }
}
