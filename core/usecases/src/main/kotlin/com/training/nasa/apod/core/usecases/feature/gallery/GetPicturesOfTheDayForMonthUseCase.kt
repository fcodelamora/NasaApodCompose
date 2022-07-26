package com.training.nasa.apod.core.usecases.feature.gallery

import com.training.nasa.apod.core.entities.PictureOfTheDay
import com.training.nasa.apod.core.repository.IPictureOfTheDayRepository
import com.training.nasa.apod.core.usecases.IErrorView
import com.training.nasa.apod.core.usecases.IProgressView
import timber.log.Timber
import timber.log.debug
import java.text.ParseException
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

class GetPicturesOfTheDayForMonthUseCase(
    private val view: IGetPicturesOfTheDayForMonthView,
    private val pictureOfTheDayRepository: IPictureOfTheDayRepository
) {
    // Due to server limitations, search date range must be between
    // 1995/06/16 - Current date @ Nasa time zone
    suspend fun execute(
        year: Int,
        month: Month,
        firstAvailableNasaEntryDate: LocalDate,
        currentNasaDate: LocalDate
    ) {

        view.showProgressView()

        try {
            var searchDateStart = LocalDate.of(year, month, 1)
            var searchDateEnd = LocalDate.of(year, month, searchDateStart.lengthOfMonth())

            searchDateStart =
                if (searchDateStart > firstAvailableNasaEntryDate) {
                    // Helps handle month changes for timezones ahead of the NASA server
                    // Ex. Search in Japan Timezone 2021/01/01 may have a NASA date of 2020/12/31
                    if (searchDateStart < currentNasaDate) {
                        searchDateStart
                    } else {
                        LocalDate.of(currentNasaDate.year, currentNasaDate.month, 1)
                    }
                } else {
                    firstAvailableNasaEntryDate
                }

            searchDateEnd =
                if (searchDateEnd > currentNasaDate) {
                    currentNasaDate
                } else {
                    searchDateEnd
                }

            val startDate = searchDateStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val endDate = searchDateEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            Timber.debug { "Formatted startDate : $startDate" }
            Timber.debug { "Formatted endDate : $endDate" }

            val picturesOfTheDay = pictureOfTheDayRepository.getPicturesOfTheDayByDateRange(
                startDate = startDate,
                endDate = endDate
            )

            val pictureToDisplay = if (picturesOfTheDay.isNotEmpty()) {
                if (currentNasaDate.year == searchDateStart.year &&
                    currentNasaDate.month == searchDateStart.month
                ) {
                    picturesOfTheDay.last()
                } else {
                    picturesOfTheDay.first()
                }
            } else {
                PictureOfTheDay()
            }

            view.onPicturesOfTheDayReceived(searchDateStart, picturesOfTheDay, pictureToDisplay)
        } catch (parseException: ParseException) {
            view.displayInvalidDateErrorView()
        } catch (exception: Exception) {
            view.handleException(exception)
        }

        view.hideProgressView()
    }
}

interface IGetPicturesOfTheDayForMonthView : IErrorView, IProgressView {
    fun onPicturesOfTheDayReceived(
        searchDateStart: LocalDate,
        receivedPictures: List<PictureOfTheDay>,
        pictureToDisplay: PictureOfTheDay
    )

    fun displayInvalidDateErrorView()
}
