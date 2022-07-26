package com.training.nasa.apod.common.resources.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.palette.graphics.Palette
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Drawable.calculatePictureAccentColor(
    coroutineScope: CoroutineScope,
    onColorFound: (Color) -> Unit
) {
    coroutineScope.launch(Dispatchers.IO) {
        val bmp = (this@calculatePictureAccentColor as BitmapDrawable).bitmap.copy(
            Bitmap.Config.ARGB_8888,
            true
        )

        Palette.from(bmp).generate { palette ->
            val swatch = palette?.vibrantSwatch ?: palette?.dominantSwatch

            val color = (
                swatch?.let {

                    // Adjust lightness to get somewhat compatible colors while changing pictures
                    val hslColor = floatArrayOf(0f, 0f, 0f)
                    ColorUtils.RGBToHSL(
                        it.rgb.red,
                        it.rgb.green,
                        it.rgb.blue,
                        hslColor
                    )
                    hslColor[2] = 0.7f

                    Color(ColorUtils.HSLToColor(hslColor))
                } ?: Color(android.R.color.white)
                )

            onColorFound.invoke(color)
        }
    }
}
