package com.training.nasa.apod

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.hilt.android.HiltAndroidApp
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import timber.log.LogcatTree
import timber.log.Timber
import timber.log.Tree

@HiltAndroidApp
open class NasaApodApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        // Setup Libraries
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(SingleTagTree())
        }
    }

    private class SingleTagTree : Tree() {

        private val baseTree = LogcatTree("NasaAPOD")

        override fun isLoggable(priority: Int, tag: String?): Boolean {
            return BuildConfig.DEBUG
        }

        override fun performLog(
            priority: Int,
            tag: String?,
            throwable: Throwable?,
            message: String?
        ) {
            baseTree.log(priority, tag, throwable, message)
        }
    }

    // Set maxRequest to prevent connection issues to the image server due to overload
    override fun newImageLoader(): ImageLoader {
        val imageCacheDir = "image_cache"

        val okHttpDispatcher = Dispatcher().apply { maxRequests = 3 }

        return ImageLoader.Builder(this)
            .respectCacheHeaders(false)
            .crossfade(true)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.5)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve(imageCacheDir))
                    .build()
            }
            .okHttpClient {
                OkHttpClient.Builder()
                    .dispatcher(okHttpDispatcher)
                    .build()
            }
            .build()
    }
}
