package com.training.nasa.apod.core.entities.extensions

import com.training.nasa.apod.core.entities.WeekDays
import java.time.DayOfWeek

fun DayOfWeek.toWeekDays() = when (this) {
    DayOfWeek.MONDAY -> WeekDays.MONDAY
    DayOfWeek.TUESDAY -> WeekDays.TUESDAY
    DayOfWeek.WEDNESDAY -> WeekDays.WEDNESDAY
    DayOfWeek.THURSDAY -> WeekDays.THURSDAY
    DayOfWeek.FRIDAY -> WeekDays.FRIDAY
    DayOfWeek.SATURDAY -> WeekDays.SATURDAY
    DayOfWeek.SUNDAY -> WeekDays.SUNDAY
}
