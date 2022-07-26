package com.training.nasa.apod.provide.datasources.di

import com.training.nasa.apod.core.datasources.IUserPreferencesDataSource
import com.training.nasa.apod.provide.datasources.sharedprefs.SharedPreferencesUserPreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourcesModule {

    @Binds
    fun bindUserPreferencesDataSource(
        dataSource: SharedPreferencesUserPreferencesDataSource
    ): IUserPreferencesDataSource
}
