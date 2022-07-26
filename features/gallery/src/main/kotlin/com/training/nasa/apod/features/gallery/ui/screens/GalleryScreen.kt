package com.training.nasa.apod.features.gallery.ui.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.features.gallery.ui.screens.gallery.GallerySingleMonth

@ExperimentalMaterialApi
@ExperimentalCoilApi
object GalleryScreen {
    object SingleMonth : GallerySingleMonth()
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Preview(name = "SingleMonth", showSystemUi = true)
@Composable
fun GallerySingleMonthScreen() {
    AppUserInterface {
        GalleryScreen.SingleMonth.Content()
    }
}
