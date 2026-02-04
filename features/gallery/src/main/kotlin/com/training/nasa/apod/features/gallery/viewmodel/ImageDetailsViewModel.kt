package com.training.nasa.apod.features.gallery.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.training.nasa.apod.common.di.UseCaseProvider
import com.training.nasa.apod.common.resources.utils.mutableStateOf
import com.training.nasa.apod.common.resources.viewmodels.ProgressViewModel
import com.training.nasa.apod.core.entities.PictureOfTheDay
import com.training.nasa.apod.core.entities.exception.AppException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val useCaseProvider: UseCaseProvider
) : ProgressViewModel(application, savedStateHandle) {

    var pictureOfTheDay by savedStateHandle.mutableStateOf<PictureOfTheDay?>(null)
        private set

    fun showSimilarImages(context: Context) {
        pictureOfTheDay?.let {
            val googleImageSearch = "https://www.google.com/searchbyimage?image_url="

            val url = googleImageSearch + it.thumbnail

            showInExternalBrowser(context, url)
        }
    }

    fun showInExternalBrowser(context: Context, url: String? = null) {
        pictureOfTheDay?.let {
            val checkedUrl = url ?: it.url

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(checkedUrl))

            try {
                context.startActivity(intent)
            } catch (exception: Exception) {
                Timber.d(exception.toString())
                handleException(AppException.ExternalBrowserLaunchException)
            }
        }
    }

    fun shareDocument(context: Context) {
        pictureOfTheDay?.let {
            Intent(Intent.ACTION_SEND).run {
                type = "text/plain"
                addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                putExtra(Intent.EXTRA_SUBJECT, it.title)
                putExtra(Intent.EXTRA_TEXT, it.url)
                context.startActivity(Intent.createChooser(this, "Share link"))
            }
        }
    }

    fun setSelectedPictureOfTheDay(pictureOfTheDay: PictureOfTheDay) {
        this.pictureOfTheDay = pictureOfTheDay
    }
}
