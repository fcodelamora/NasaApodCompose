package com.training.nasa.apod.features.gallery.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.features.gallery.ui.screens.imagedetails.ImageDetailsCircular
import com.training.nasa.apod.features.gallery.ui.screens.imagedetails.ImageDetailsSquared

// Setting up the object to provide alternative versions of the same screen.
object ImageDetailsScreen {
    object Circular : ImageDetailsCircular()
    object Squared : ImageDetailsSquared() // WIP
}

@Preview(showSystemUi = true)
@Composable
private fun ImageDetailsDefaultScreenPreview() {
    AppUserInterface {
        ImageDetailsScreen.Circular.Content()
    }
}
