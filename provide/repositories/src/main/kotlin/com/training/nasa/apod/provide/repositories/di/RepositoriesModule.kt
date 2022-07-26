package com.training.nasa.apod.provide.repositories.di

import com.training.nasa.apod.core.repository.IPictureOfTheDayRepository
import com.training.nasa.apod.core.repository.IUserPreferencesRepository
import com.training.nasa.apod.provide.UserPreferencesRepository
import com.training.nasa.apod.provide.repositories.PictureOfTheDayRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindPictureOfTheDayRepository(
        repository: PictureOfTheDayRepository
    ): IPictureOfTheDayRepository

    @Binds
    fun bindUserPreferencesRepository(
        repository: UserPreferencesRepository
    ): IUserPreferencesRepository
}
