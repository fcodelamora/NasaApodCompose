package com.training.nasa.apod.common.resources.ui.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import com.training.nasa.apod.core.entities.PictureOfTheDay

fun PictureOfTheDay.getThumbnailColorFilter(tintColor: Color) = if (thumbnail == null)
    ColorFilter.tint(tintColor.copy(alpha = 0.8f)) // Use color filter instead of the image. This targets placeholders.
else
    null // Show the image "as is", instead of modifying it.
