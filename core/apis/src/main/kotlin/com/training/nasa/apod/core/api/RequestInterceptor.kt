package com.training.nasa.apod.core.api

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import timber.log.debug

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder()
            .method(original.method, original.body)
            .addHeader("Content-Type", "application/json;charset=UTF-8")
        Timber.debug { request.toString() }
        return chain.proceed(request.build())
    }
}
