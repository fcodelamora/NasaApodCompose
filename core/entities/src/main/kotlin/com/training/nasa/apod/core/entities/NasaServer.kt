package com.training.nasa.apod.core.entities

import java.time.LocalDate
import java.time.ZoneId

object NasaServer {

    // Use NASA`s "PST", "America/Los_Angeles" to prevent requesting pictures days in the future
    // for timezones that are earlier than the server (Ex. Japan)
    private val nasaTimeZone: ZoneId = ZoneId.of("PST", ZoneId.SHORT_IDS)

    val currentNasaDate: LocalDate get() = LocalDate.now(nasaTimeZone)
    val nasaApodFirstEntry: LocalDate get() = LocalDate.of(1995, 6, 16)
}
