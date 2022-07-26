package com.training.nasa.apod.common.resources.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.training.nasa.apod.common.resources.ui.catalogs.theme.NasaApodMultimoduleComposeTheme
import com.training.nasa.apod.core.entities.AppTheme

@Composable
fun AppUserInterface(
    selectedTheme: AppTheme = AppTheme.DYNAMIC,
    dynamicColor: Color = Color.LightGray,
    content: @Composable () -> Unit
) = NasaApodMultimoduleComposeTheme(
    theme = selectedTheme,
    dynamicColor = dynamicColor
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        content()
    }
}
