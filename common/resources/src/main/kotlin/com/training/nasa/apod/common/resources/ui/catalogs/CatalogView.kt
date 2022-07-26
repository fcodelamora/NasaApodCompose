package com.training.nasa.apod.common.resources.ui.catalogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.training.nasa.apod.common.resources.ui.catalogs.theme.DarkColorPalette

@Composable
fun CatalogView(content: @Composable () -> Unit) {
    // Not using AppUserInterface as the custom Theme entities are not recognized by
    // UI tooling preview, making all previews fail.
    MaterialTheme(colors = DarkColorPalette) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column {
                content()
            }
        }
    }
}
