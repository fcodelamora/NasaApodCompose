package com.training.nasa.apod.core.usecases.test.gallery

import com.training.nasa.apod.core.entities.NasaServer.currentNasaDate
import com.training.nasa.apod.core.entities.NasaServer.nasaApodFirstEntry
import com.training.nasa.apod.core.entities.PictureOfTheDay
import com.training.nasa.apod.core.repository.IPictureOfTheDayRepository
import com.training.nasa.apod.core.usecases.feature.gallery.GetPicturesOfTheDayForMonthUseCase
import com.training.nasa.apod.core.usecases.feature.gallery.IGetPicturesOfTheDayForMonthView
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.eq
import org.mockito.kotlin.given
import org.mockito.kotlin.willAnswer
import java.time.LocalDate
import java.time.Month

class GetPicturesOfTheDayForMonthUseCaseTest {

    companion object {
        private const val FIRST_PICTURE = "first"
        private const val LAST_PICTURE = "last"
    }

    private lateinit var mockRepository: IPictureOfTheDayRepository
    private lateinit var mockView: IGetPicturesOfTheDayForMonthView
    private lateinit var useCase: GetPicturesOfTheDayForMonthUseCase

    private val receivedPicturesMock =
        listOf(
            PictureOfTheDay(title = FIRST_PICTURE, dateString = "2022-06-01"),
            PictureOfTheDay(title = LAST_PICTURE, dateString = "2022-06-22")
        )

    @BeforeEach
    fun before() = runBlocking {
        mockRepository = mock(IPictureOfTheDayRepository::class.java).also {
            given(
                it.getPicturesOfTheDayByDateRange(any(), any())
            ) willAnswer { receivedPicturesMock }
        }

        mockView = mock(IGetPicturesOfTheDayForMonthView::class.java)

        useCase = GetPicturesOfTheDayForMonthUseCase(mockView, mockRepository)
    }

    @Test
    fun `execute - Date range for date in the past`() = runBlocking {

        useCase.execute(2020, Month.DECEMBER, nasaApodFirstEntry, currentNasaDate)

        verify(mockRepository, times(1)).getPicturesOfTheDayByDateRange(
            "2020-12-01",
            "2020-12-31"
        )

        verify(mockView, times(1)).showProgressView()
        verify(mockView, times(1)).hideProgressView()
        verify(mockView, times(1)).onPicturesOfTheDayReceived(
            searchDateStart = eq(LocalDate.of(2020, 12, 1)),
            receivedPictures = eq(receivedPicturesMock),
            pictureToDisplay = eq(receivedPicturesMock.first()),
        )
        verify(mockView, never()).displayInvalidDateErrorView()
        verify(mockView, never()).handleException(anyOrNull())
    }

    @Test
    fun `execute - Date range for date - NASA service first available entry`() = runBlocking {
        val firstEntryDate = nasaApodFirstEntry // 1995/06/16
        useCase.execute(
            firstEntryDate.year,
            firstEntryDate.month,
            nasaApodFirstEntry,
            currentNasaDate
        )

        verify(mockRepository, times(1)).getPicturesOfTheDayByDateRange(
            "1995-06-16",
            "1995-06-30"
        )

        verify(mockView, times(1)).showProgressView()
        verify(mockView, times(1)).hideProgressView()
        verify(mockView, times(1)).onPicturesOfTheDayReceived(
            searchDateStart = eq(LocalDate.of(1995, 6, 16)),
            receivedPictures = eq(receivedPicturesMock),
            pictureToDisplay = eq(receivedPicturesMock.first()),
        )
        verify(mockView, never()).displayInvalidDateErrorView()
        verify(mockView, never()).handleException(anyOrNull())
    }

    @Test
    fun `execute - Date range for date - Search date is in the future compared to NASA server time`() =
        runBlocking {

            val nasaCurrentDay = LocalDate.of(2020, 12, 31)
            val now = LocalDate.of(2021, 1, 1)

            useCase.execute(now.year, now.month, nasaApodFirstEntry, nasaCurrentDay)

            // Fallback to the current NASA server time as search range.
            verify(mockRepository, times(1)).getPicturesOfTheDayByDateRange(
                "2020-12-01",
                "2020-12-31"
            )

            verify(mockView, times(1)).showProgressView()
            verify(mockView, times(1)).hideProgressView()
            verify(mockView, times(1)).onPicturesOfTheDayReceived(
                searchDateStart = eq(LocalDate.of(2020, 12, 1)),
                receivedPictures = eq(receivedPicturesMock),
                pictureToDisplay = eq(receivedPicturesMock.last()),
            )
            verify(mockView, never()).displayInvalidDateErrorView()
            verify(mockView, never()).handleException(anyOrNull())
        }
}
