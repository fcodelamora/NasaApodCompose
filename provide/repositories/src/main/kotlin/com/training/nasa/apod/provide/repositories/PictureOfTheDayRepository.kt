package com.training.nasa.apod.provide.repositories

import com.training.nasa.apod.core.api.INasaApodApi
import com.training.nasa.apod.core.api.error.ApiException
import com.training.nasa.apod.core.api.requests.GetPictureRequestParams
import com.training.nasa.apod.core.api.requests.GetPicturesByDateRangeRequestParams
import com.training.nasa.apod.core.entities.PictureOfTheDay
import com.training.nasa.apod.core.repository.IPictureOfTheDayRepository
import com.training.nasa.apod.core.repository.extensions.toApplicationException
import com.training.nasa.apod.core.repository.extensions.toPictureOfTheDay
import com.training.nasa.apod.core.repository.extensions.toPictureOfTheDayList
import com.training.nasa.apod.provide.RepositoriesBuildConfig
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class PictureOfTheDayRepository @Inject constructor(private val nasaApodApi: INasaApodApi) :
    IPictureOfTheDayRepository {

    private val apiKey = RepositoriesBuildConfig.API_KEY

    override suspend fun getPictureOfTheDay(
        date: String
    ): PictureOfTheDay =
        withContext(Dispatchers.IO) {
            try {
                nasaApodApi.getPictureOfTheDay(
                    GetPictureRequestParams(
                        date,
                        apiKey
                    )
                ).toPictureOfTheDay()
            } catch (exception: ApiException) {
                throw exception.toApplicationException()
            }
        }

    override suspend fun getPicturesOfTheDayByDateRange(
        startDate: String,
        endDate: String
    ): List<PictureOfTheDay> =
        withContext(Dispatchers.IO) {
            try {
                nasaApodApi.getPictureOfTheDayByDateRange(
                    GetPicturesByDateRangeRequestParams(
                        startDate,
                        endDate,
                        apiKey
                    )
                ).toPictureOfTheDayList()
            } catch (exception: ApiException) {
                throw exception.toApplicationException()
            }
        }
}
