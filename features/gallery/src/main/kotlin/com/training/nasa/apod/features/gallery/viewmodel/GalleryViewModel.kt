package com.training.nasa.apod.features.gallery.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImagePainter
import com.training.nasa.apod.common.di.UseCaseProvider
import com.training.nasa.apod.common.resources.utils.mutableStateOf
import com.training.nasa.apod.common.resources.viewmodels.ProgressViewModel
import com.training.nasa.apod.core.entities.CalendarEntry
import com.training.nasa.apod.core.entities.ErrorViewData
import com.training.nasa.apod.core.entities.NasaServer
import com.training.nasa.apod.core.entities.PictureOfTheDay
import com.training.nasa.apod.core.usecases.feature.gallery.IConvertToCalendarEntriesUseCaseView
import com.training.nasa.apod.core.usecases.feature.gallery.IGetPicturesOfTheDayForMonthView
import com.training.nasa.apod.features.gallery.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.debug
import java.time.LocalDate
import java.time.Month
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val useCaseProvider: UseCaseProvider
) : ProgressViewModel(application, savedStateHandle),
    IGetPicturesOfTheDayForMonthView,
    IConvertToCalendarEntriesUseCaseView {

    private val currentNasaDate = NasaServer.currentNasaDate

    var selectedYear by savedStateHandle.mutableStateOf(currentNasaDate.year)
        private set

    var selectedMonth by savedStateHandle.mutableStateOf<Month?>(null)
        private set

    var pictureOfTheDay by savedStateHandle.mutableStateOf<PictureOfTheDay?>(null)
        private set

    var calendarEntries by savedStateHandle.mutableStateOf(listOf<CalendarEntry>())
        private set

    var disabledMonths by savedStateHandle.mutableStateOf(listOf<Month>())
        private set

    var pictureOfTheDayPreviewPainter = mutableStateOf<AsyncImagePainter?>(null)
        private set

    fun getCurrentMonthPictures() {
        if (calendarEntries.isEmpty() && currentError == null)
            viewModelScope.launch {
                currentNasaDate.run {
                    delay(500)

                    // Update the UI as well using below methods.
                    onYearSelected(year)
                    onMonthSelected(month)
                }
            }
    }

    fun setSelectedPictureOfTheDay(pictureOfTheDay: PictureOfTheDay?, painter: AsyncImagePainter) {
        pictureOfTheDay?.let {
            this.pictureOfTheDay = it
            this.pictureOfTheDayPreviewPainter.value = painter
        }
    }

    private fun requestPicturesOfTheDayForMonth(year: Int, month: Month) {
        calendarEntries = emptyList()
        viewModelScope.launch {
            useCaseProvider.provideGetPicturesOfTheDayForMonthUseCase(this@GalleryViewModel)
                .execute(
                    year,
                    month,
                    NasaServer.nasaApodFirstEntry,
                    NasaServer.currentNasaDate
                )
        }
    }

    override fun onPicturesOfTheDayReceived(
        searchDateStart: LocalDate,
        receivedPictures: List<PictureOfTheDay>,
        pictureToDisplay: PictureOfTheDay
    ) {
        // Data received successfully, mark the selected year/month in UI
        searchDateStart.run {
            selectedYear = year
            selectedMonth = month
        }

        pictureOfTheDay = pictureToDisplay

        viewModelScope.launch {
            useCaseProvider.provideConvertToCalendarEntriesUseCase(this@GalleryViewModel)
                .execute(receivedPictures)
        }
    }

    override fun displayInvalidDateErrorView() {
        showErrorView(
            ErrorViewData(
                getString(R.string.error_invalid_date_title),
                getString(R.string.error_invalid_date_message)
            )
        )
    }

    override fun populateCalendar(calendarEntries: List<CalendarEntry>) {
        this.calendarEntries = calendarEntries
    }

    fun onYearSelected(year: Int) {
        Timber.debug { "onYearSelected $year" }
        disabledMonths = getDisabledMonthsForYear(year)
        selectedMonth = null
        selectedYear = year
    }

    private fun getDisabledMonthsForYear(year: Int): List<Month> {
        val disabledMonths: MutableList<Month> = mutableListOf()

        when (year) {
            1995 -> {
                disabledMonths.addAll(
                    Month.values().filter { it.ordinal < Month.JUNE.ordinal }
                )
            }
            currentNasaDate.year -> {
                disabledMonths.addAll(
                    Month.values().filter { it.ordinal > currentNasaDate.month.ordinal }
                )
            }
        }

        return disabledMonths.also {
            Timber.debug { "disabled months: $disabledMonths" }
        }
    }

    fun onMonthSelected(month: Month) {
        Timber.debug { "onMonthSelected $month.name" }
        viewModelScope.run {
            calendarEntries = emptyList()
            requestPicturesOfTheDayForMonth(selectedYear, month)
        }
    }

    override fun handleException(exception: Exception) {
        selectedMonth = null
        super.handleException(exception)
    }
}
