package com.training.nasa.apod.features.gallery.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.navigationBarsPadding
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.common.resources.ui.catalogs.theme.Gray200
import com.training.nasa.apod.common.resources.ui.catalogs.views.TransparentTopAppBar
import com.training.nasa.apod.common.resources.ui.catalogs.views.TransparentStatusBarBackground
import com.training.nasa.apod.core.entities.UserPreferences
import com.training.nasa.apod.features.gallery.ui.widgets.ZoomableImage
import com.training.nasa.apod.features.gallery.viewmodel.FullImageViewModel

@ExperimentalCoilApi
object FullImageScreen {

    object Default {
        @Composable
        fun Screen(
            viewModel: FullImageViewModel,
            navController: NavController,
            userPreferences: UserPreferences,
            url: String? = null
        ) {
            Content(
                url = url,
                showBackButton = userPreferences.showBackButtonOnImages,
                onImageError = { viewModel.onImageError() },
                onBackClick = { navController.popBackStack() }
            )
        }

        @Composable
        fun Content(
            url: String? = null,
            showBackButton: Boolean = false,
            onBackClick: () -> Unit = {},
            onImageError: () -> Unit = {},
        ) {
            Box(Modifier.fillMaxSize()) {
                if (url != null) {
                    ZoomableImage(
                        imageUrl = url,
                        onImageError = onImageError,
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                    )
                    if (showBackButton) {
                        TransparentStatusBarBackground(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter)
                        )
                        TransparentTopAppBar(
                            onNavigationIconClick = onBackClick,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                        )
                    }
                } else {
                    Text(
                        text = "Invalid data received",
                        color = Gray200,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Preview(showSystemUi = true)
@Composable
private fun FullImageScreenDefaultPreview() {
    AppUserInterface {
        FullImageScreen.Default.Content(url = "")
    }
}
