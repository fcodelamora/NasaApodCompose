package com.training.nasa.apod.core.usecases.test.gallery

import com.training.nasa.apod.core.entities.CalendarEntry
import com.training.nasa.apod.core.entities.ErrorViewData
import com.training.nasa.apod.core.entities.PictureOfTheDay
import com.training.nasa.apod.core.usecases.feature.gallery.ConvertToCalendarEntriesUseCase
import com.training.nasa.apod.core.usecases.feature.gallery.IConvertToCalendarEntriesUseCaseView
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull

/**
 * All test assume a calendar where the week start day is WeekDay.MONDAY
 */
class ConvertToCalendarEntriesUseCaseTest {

    private lateinit var mockView: IConvertToCalendarEntriesUseCaseView
    private lateinit var useCase: ConvertToCalendarEntriesUseCase

    private fun prepareUseCaseTest(entries: MutableList<CalendarEntry>): ConvertToCalendarEntriesUseCase {
        val mockedView = object : IConvertToCalendarEntriesUseCaseView {
            override fun populateCalendar(calendarEntries: List<CalendarEntry>) {
                entries.addAll(calendarEntries)
            }

            override fun displayInvalidDateErrorView() {}
            override fun showErrorView(errorViewData: ErrorViewData) {}
            override fun handleException(exception: Exception) {}
            override fun showProgressView() {}
            override fun hideProgressView() {}
        }

        return ConvertToCalendarEntriesUseCase(mockedView)
    }

    @Test
    fun `execute - populateCalendar on empty list`() = runBlocking {

        mockView = mock(IConvertToCalendarEntriesUseCaseView::class.java)
        useCase = ConvertToCalendarEntriesUseCase(mockView)

        useCase.execute(listOf())

        verify(mockView, times(1)).populateCalendar(any())
        verify(mockView, times(1)).showProgressView()
        verify(mockView, times(1)).hideProgressView()
        verify(mockView, never()).displayInvalidDateErrorView()
        verify(mockView, never()).showErrorView(anyOrNull())
        verify(mockView, never()).handleException(anyOrNull())
    }

    @Test
    fun `execute - Check Received data for month expecting 28 calendar entries`() =
        runBlocking {
            val entries = mutableListOf<CalendarEntry>()
            useCase = prepareUseCaseTest(entries)

            useCase.execute(listOf(PictureOfTheDay(dateString = "2021-02-15")))

            val allEntriesCount = entries.size
            val standardDayCount = entries.filterIsInstance<CalendarEntry.Day>().size
            val pastDayCount = entries.filterIsInstance<CalendarEntry.Util.EmptyDay.PastDay>().size
            val futureDayCount =
                entries.filterIsInstance<CalendarEntry.Util.EmptyDay.FutureDay>().size
            val paddingCount = entries.filterIsInstance<CalendarEntry.Util.Padding>().size

            /*
            * - Month's 1st day is a Monday
            * - No "Padding" expected at the beginning or end
            * - Standard day expected on position 15 (index 14)
            */
            assertEquals(CalendarEntry.Day::class.java, entries[14].javaClass)
            assertEquals(CalendarEntry.Util.EmptyDay.PastDay::class.java, entries[0].javaClass)
            assertEquals(CalendarEntry.Util.EmptyDay.PastDay::class.java, entries.last().javaClass)

            assertEquals(28, allEntriesCount)
            assertEquals(1, standardDayCount)
            assertEquals(27, pastDayCount)
            assertEquals(0, paddingCount)
            assertEquals(0, futureDayCount)
        }

    @Test
    fun `execute - Check Received data for month expecting over 28 and below 35 calendar entries`() =
        runBlocking {
            val entries = mutableListOf<CalendarEntry>()
            useCase = prepareUseCaseTest(entries)

            useCase.execute(listOf(PictureOfTheDay(dateString = "2021-03-15")))

            val allEntriesCount = entries.size
            val standardDayCount = entries.filterIsInstance<CalendarEntry.Day>().size
            val pastDayCount = entries.filterIsInstance<CalendarEntry.Util.EmptyDay.PastDay>().size
            val futureDayCount =
                entries.filterIsInstance<CalendarEntry.Util.EmptyDay.FutureDay>().size
            val paddingCount = entries.filterIsInstance<CalendarEntry.Util.Padding>().size

            /*
            * - Month's 1st day is a Monday
            * - No "Padding"  expected at the beginning
            * - Standard day expected on position 15 (index 14)
            * - Last entry expected to be Padding
            */
            assertEquals(CalendarEntry.Day::class.java, entries[14].javaClass)
            assertEquals(CalendarEntry.Util.EmptyDay.PastDay::class.java, entries[0].javaClass)
            assertEquals(CalendarEntry.Util.Padding::class.java, entries.last().javaClass)

            assertEquals(35, allEntriesCount)
            assertEquals(1, standardDayCount)
            assertEquals(30, pastDayCount)
            assertEquals(4, paddingCount)
            assertEquals(0, futureDayCount)
        }

    @Test
    fun `execute - Check Received data for month expecting 35 calendar entries`() =
        runBlocking {
            val entries = mutableListOf<CalendarEntry>()
            useCase = prepareUseCaseTest(entries)

            useCase.execute(listOf(PictureOfTheDay(dateString = "2021-01-15")))

            val allEntriesCount = entries.size
            val standardDayCount = entries.filterIsInstance<CalendarEntry.Day>().size
            val pastDayCount = entries.filterIsInstance<CalendarEntry.Util.EmptyDay.PastDay>().size
            val futureDayCount =
                entries.filterIsInstance<CalendarEntry.Util.EmptyDay.FutureDay>().size
            val paddingCount = entries.filterIsInstance<CalendarEntry.Util.Padding>().size

            /*
            * - Month's 1st day is a Friday, Padding expected for Monday - Thursday
            * - No "Padding"  expected at the end of the Month
            * - Standard day expected on position 19 (index 18) (usual 15 + the initial padding[4])
            * - Last entry expected to be PastDay
            */
            assertEquals(CalendarEntry.Day::class.java, entries[18].javaClass)
            assertEquals(CalendarEntry.Util.Padding::class.java, entries[0].javaClass)
            assertEquals(CalendarEntry.Util.EmptyDay.PastDay::class.java, entries.last().javaClass)
            assertEquals(35, allEntriesCount)
            assertEquals(1, standardDayCount)
            assertEquals(30, pastDayCount)
            assertEquals(4, paddingCount)
            assertEquals(0, futureDayCount)
        }

    @Test
    fun `execute - Check Received data for month expecting over 35 calendar entries`() =
        runBlocking {
            val entries = mutableListOf<CalendarEntry>()
            useCase = prepareUseCaseTest(entries)

            useCase.execute(listOf(PictureOfTheDay(dateString = "2021-05-15")))

            val allEntriesCount = entries.size
            val standardDayCount = entries.filterIsInstance<CalendarEntry.Day>().size
            val pastDayCount = entries.filterIsInstance<CalendarEntry.Util.EmptyDay.PastDay>().size
            val futureDayCount =
                entries.filterIsInstance<CalendarEntry.Util.EmptyDay.FutureDay>().size
            val paddingCount = entries.filterIsInstance<CalendarEntry.Util.Padding>().size

            /*
            * - Month's 1st day is a Saturday, Padding expected for Monday - Friday
            * - Extra "Padding" expected at the end of the Month
            * - Standard day expected on position 20 (index 19) (usual 15 + the initial padding[5])
            * - Last entry expected to be Padding
            */
            assertEquals(CalendarEntry.Day::class.java, entries[19].javaClass)
            assertEquals(CalendarEntry.Util.Padding::class.java, entries[0].javaClass)
            assertEquals(CalendarEntry.Util.Padding::class.java, entries.last().javaClass)
            assertEquals(42, allEntriesCount)
            assertEquals(1, standardDayCount)
            assertEquals(30, pastDayCount)
            assertEquals(11, paddingCount)
            assertEquals(0, futureDayCount)
        }
}
