package com.training.nasa.apod.core.api

import com.training.nasa.apod.core.api.requests.GetPictureRequestParams
import com.training.nasa.apod.core.api.requests.GetPicturesByDateRangeRequestParams
import com.training.nasa.apod.core.api.response.GetPictureResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface INasaApodApi {
    @GET("apod")
    suspend fun getPictureOfTheDay(
        @QueryMap(encoded = true) requestParams: GetPictureRequestParams
    ): GetPictureResponse

    @GET("apod")
    suspend fun getPictureOfTheDayByDateRange(
        @QueryMap(encoded = true) requestParams: GetPicturesByDateRangeRequestParams
    ): List<GetPictureResponse>

    companion object {
        fun provide(
            baseUrl: String,
            isOutputEnabled: Boolean
        ): INasaApodApi {
            val builder = OkHttpClient.Builder()
                .addInterceptor(RequestInterceptor())
                .addInterceptor(ErrorResponseInterceptor())
            if (isOutputEnabled) {
                builder.addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
            val httpClient = builder.build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(INasaApodApi::class.java)
        }
    }
}
