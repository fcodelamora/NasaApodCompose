package com.training.nasa.apod.provide.mocks.api.di

import com.training.nasa.apod.core.api.INasaApodApi
import com.training.nasa.apod.provide.mocks.api.MockNasaApodApi
import com.training.nasa.apod.provide.mocks.api.debugflags.IMockDebugFlagsRepository
import com.training.nasa.apod.provide.mocks.api.debugflags.MockDebugFlags
import com.training.nasa.apod.provide.mocks.api.debugflags.MockDebugFlagsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockApiModule {

    @Singleton
    @Provides
    fun provideMockDebugFlags() = MockDebugFlags()

    @Singleton
    @Provides
    fun provideNasaApodApi(mockDebugFlagsRepository: IMockDebugFlagsRepository): INasaApodApi =
        MockNasaApodApi(mockDebugFlagsRepository)
}

@Module
@InstallIn(SingletonComponent::class)
interface MockDebugFlagsRepositoryModule {

    @Binds
    fun bindMockDebugFlagsRepository(
        repository: MockDebugFlagsRepository
    ): IMockDebugFlagsRepository
}
