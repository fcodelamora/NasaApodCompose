package com.training.nasa.apod.core.usecases.feature.gallery

import com.training.nasa.apod.core.entities.CalendarEntry
import com.training.nasa.apod.core.entities.NasaServer
import com.training.nasa.apod.core.entities.PictureOfTheDay
import com.training.nasa.apod.core.entities.WeekDays
import com.training.nasa.apod.core.entities.extensions.toWeekDays
import com.training.nasa.apod.core.usecases.IErrorView
import com.training.nasa.apod.core.usecases.IProgressView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.time.LocalDate

class ConvertToCalendarEntriesUseCase(
    private val view: IConvertToCalendarEntriesUseCaseView
) {
    suspend fun execute(picturesOfTheDay: List<PictureOfTheDay>) = withContext(Dispatchers.IO) {

        view.showProgressView()

        try {
            if (picturesOfTheDay.isEmpty()) {
                view.populateCalendar(listOf())
                view.hideProgressView()
                return@withContext
            }

            val formattedCalendarEntries = mutableListOf<CalendarEntry>()

            val receivedCalendarEntries = picturesOfTheDay.convertToCalendarEntries()

            val receivedMonthFirstDay = picturesOfTheDay[0].date.let {
                LocalDate.of(it.year, it.month, 1)
            }

            val lengthOfMoth = receivedMonthFirstDay.lengthOfMonth()

            // Pad days that belong to another month to achieve a view that looks like a calendar
            // TODO: Allow starting day customization.
            val weekStartsOn = WeekDays.MONDAY.position

            val monthStartWeekDay =
                receivedMonthFirstDay.dayOfWeek.toWeekDays().position

            // Adjust to start the calendar weeks on "weekStartsOn"
            val emptyDays = if (monthStartWeekDay >= weekStartsOn)
                monthStartWeekDay - weekStartsOn
            else
                7 - (weekStartsOn - monthStartWeekDay)

            for (i in 1..emptyDays) {
                formattedCalendarEntries.add(CalendarEntry.Util.Padding)
            }

            val currentNasaDate = NasaServer.currentNasaDate
            var nextExpectedDayOfMonth = 1

            while (receivedCalendarEntries.isNotEmpty()) {
                when (val currentEntry = receivedCalendarEntries[0]) {
                    is CalendarEntry.Day -> {
                        if (nextExpectedDayOfMonth == currentEntry.date.dayOfMonth) {
                            formattedCalendarEntries.add(receivedCalendarEntries.removeAt(0))
                        } else {
                            formattedCalendarEntries.add(CalendarEntry.Util.EmptyDay.PastDay)
                        }
                        nextExpectedDayOfMonth++
                    }
                    else -> {
                        throw RuntimeException("Unexpected type received")
                    }
                }
            }

            // Check how many days are available. Ignore Day Paddings.
            var dayAndEmptyDayCount = formattedCalendarEntries.filter {
                it is CalendarEntry.Util.EmptyDay || it is CalendarEntry.Day
            }.size

            // Add missing days
            receivedMonthFirstDay?.let {
                val isCurrentMonthData = (it.year == currentNasaDate.year) &&
                    (it.month == currentNasaDate.month)

                while (dayAndEmptyDayCount < lengthOfMoth) {
                    formattedCalendarEntries.add(
                        if (isCurrentMonthData)
                            CalendarEntry.Util.EmptyDay.FutureDay
                        else
                            CalendarEntry.Util.EmptyDay.PastDay
                    )
                    dayAndEmptyDayCount++
                }
            }

            // Depending on the length of the month, additional padding may be required
            // to make the calendar have the exact amount of days each week.
            dayAndEmptyDayCount = formattedCalendarEntries.filter {
                it is CalendarEntry.Util || it is CalendarEntry.Day
            }.size

            if (dayAndEmptyDayCount != 28) {
                if (dayAndEmptyDayCount < 35) {
                    while (dayAndEmptyDayCount < 35) {
                        formattedCalendarEntries.add(CalendarEntry.Util.Padding)
                        dayAndEmptyDayCount++
                    }
                } else if (dayAndEmptyDayCount != 35) {
                    while (dayAndEmptyDayCount < 42) {
                        formattedCalendarEntries.add(CalendarEntry.Util.Padding)
                        dayAndEmptyDayCount++
                    }
                }
            }
            view.populateCalendar(formattedCalendarEntries)
        } catch (parseException: ParseException) {
            view.displayInvalidDateErrorView()
        } catch (exception: Exception) {
            view.handleException(exception)
        }

        view.hideProgressView()
    }
}

private fun List<PictureOfTheDay>.convertToCalendarEntries(): MutableList<CalendarEntry> {
    val calendarEntries = mutableListOf<CalendarEntry>()

    this.map {
        calendarEntries.add(CalendarEntry.Day(date = it.date, pictureOfTheDay = it))
    }

    return calendarEntries
}

interface IConvertToCalendarEntriesUseCaseView : IErrorView, IProgressView {
    fun populateCalendar(calendarEntries: List<CalendarEntry>)
    fun displayInvalidDateErrorView()
}
