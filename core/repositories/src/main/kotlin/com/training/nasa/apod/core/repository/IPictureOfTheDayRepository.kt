package com.training.nasa.apod.core.repository

import com.training.nasa.apod.core.entities.PictureOfTheDay

interface IPictureOfTheDayRepository {

    // dates : YYYY-MM-DD
    suspend fun getPictureOfTheDay(
        date: String
    ): PictureOfTheDay

    suspend fun getPicturesOfTheDayByDateRange(
        startDate: String,
        endDate: String
    ): List<PictureOfTheDay>
}
