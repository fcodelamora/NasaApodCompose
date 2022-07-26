package com.training.nasa.apod.core.api

import com.squareup.moshi.Moshi
import com.training.nasa.apod.core.api.error.ApiException
import com.training.nasa.apod.core.api.error.DefaultApiError
import okhttp3.Interceptor
import okhttp3.Response

class ErrorResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.isSuccessful.not()) {
            val errorBody = response.body?.string()
            if (errorBody != null && errorBody.isNotEmpty()) {
                val jsonAdapter = Moshi.Builder().build().adapter(DefaultApiError::class.java)
                val error = jsonAdapter.fromJson(errorBody)
                throw ApiException(response.code, error)
            } else {
                throw ApiException(response.code)
            }
        }
        return response
    }
}
