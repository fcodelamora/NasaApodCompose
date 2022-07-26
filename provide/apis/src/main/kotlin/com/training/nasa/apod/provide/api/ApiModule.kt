package com.training.nasa.apod.provide.api

import com.training.nasa.apod.core.api.INasaApodApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideNasaApodApi(): INasaApodApi =
        INasaApodApi.provide(
            baseUrl = ApisBuildConfig.API_BASE_URL,
            isOutputEnabled = ApisBuildConfig.DEBUG
        )
}
