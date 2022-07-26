package com.training.nasa.apod.features.gallery.ui.screens.imagedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.common.resources.ui.NasaApodScreens
import com.training.nasa.apod.core.entities.MediaType
import com.training.nasa.apod.core.entities.PictureOfTheDay
import com.training.nasa.apod.core.entities.UserPreferences
import com.training.nasa.apod.features.gallery.viewmodel.ImageDetailsViewModel

open class ImageDetailsSquared {
    @Composable
    fun Screen(
        viewModel: ImageDetailsViewModel,
        navController: NavController,
        userPreferences: UserPreferences,
    ) {
        val context = LocalContext.current

        Content(
            pictureOfTheDay = viewModel.pictureOfTheDay,
            showBackButton = userPreferences.showBackButtonOnImages,
            onShowFullScreenMedia = {
                when (viewModel.pictureOfTheDay?.mediaType) {
                    MediaType.IMAGE -> {
                        navController.navigate(NasaApodScreens.IMAGE_VIEWER.name)
                    }
                    MediaType.VIDEO,
                    MediaType.UNKNOWN -> {
                        viewModel.showInExternalBrowser(context)
                    }
                }
            },
            onShareDocument = { viewModel.shareDocument(context) },
            onCheckSimilarImages = { viewModel.showSimilarImages(context) },
            onBackClick = { navController.popBackStack() }

        )
    }

    @Composable
    fun Content(
        pictureOfTheDay: PictureOfTheDay? = null,
        showBackButton: Boolean = false,
        onShareDocument: (() -> Unit)? = null,
        onShowFullScreenMedia: () -> Unit = { },
        onDownloadToDevice: (() -> Unit)? = null,
        onCheckSimilarImages: (() -> Unit)? = null,
        onBackClick: () -> Unit = {}
    ) = Box(Modifier.fillMaxSize()) {
        Text("Under Construction", Modifier.align(Alignment.Center))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ImageDetailsSquaredPreview() {
    AppUserInterface {
        ImageDetailsSquared().Content()
    }
}
