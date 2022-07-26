package com.training.nasa.apod.common.di

import com.training.nasa.apod.core.repository.IPictureOfTheDayRepository
import com.training.nasa.apod.core.repository.IUserPreferencesRepository
import com.training.nasa.apod.core.usecases.feature.gallery.ConvertToCalendarEntriesUseCase
import com.training.nasa.apod.core.usecases.feature.gallery.GetPicturesOfTheDayForMonthUseCase
import com.training.nasa.apod.core.usecases.feature.gallery.IConvertToCalendarEntriesUseCaseView
import com.training.nasa.apod.core.usecases.feature.gallery.IGetPicturesOfTheDayForMonthView
import com.training.nasa.apod.core.usecases.feature.settings.GetUserPreferencesUseCase
import com.training.nasa.apod.core.usecases.feature.settings.IGetUserPreferencesView
import com.training.nasa.apod.core.usecases.feature.settings.IStoreUserPreferencesView
import com.training.nasa.apod.core.usecases.feature.settings.StoreUserPreferencesUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UseCaseProvider @Inject constructor(
    private val pictureOfTheDayRepository: IPictureOfTheDayRepository,
    private val userPreferencesRepository: IUserPreferencesRepository
) {

    fun provideGetPicturesOfTheDayForMonthUseCase(
        view: IGetPicturesOfTheDayForMonthView
    ) = GetPicturesOfTheDayForMonthUseCase(
        view,
        pictureOfTheDayRepository
    )

    fun provideConvertToCalendarEntriesUseCase(
        view: IConvertToCalendarEntriesUseCaseView
    ) = ConvertToCalendarEntriesUseCase(
        view
    )

    fun provideLoadUserPreferencesUseCase(
        view: IGetUserPreferencesView
    ) = GetUserPreferencesUseCase(
        view,
        userPreferencesRepository
    )

    fun provideStoreUserPreferencesUseCase(
        view: IStoreUserPreferencesView
    ) = StoreUserPreferencesUseCase(
        view,
        userPreferencesRepository
    )
}
