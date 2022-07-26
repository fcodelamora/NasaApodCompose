package com.training.nasa.apod.features.gallery.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.training.nasa.apod.common.resources.ResetImageValueAnimator
import com.training.nasa.apod.common.resources.ui.AppDrawable
import com.training.nasa.apod.common.resources.ui.catalogs.views.ImageAnimatedPlaceholder

// FIXME Improve pinch/Drag experience
// https://developer.android.com/jetpack/compose/gestures#multitouch
@ExperimentalCoilApi
@Composable
fun ZoomableImage(
    modifier: Modifier,
    imageUrl: String?,
    minScale: Float = 0.7f,
    onImageError: () -> Unit = {},
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .crossfade(true)
            .scale(Scale.FILL)
            .build()
    )

    val state = rememberTransformableState { zoomChange, offsetChange, _ ->

        if (painter.state !is AsyncImagePainter.State.Success) {
            return@rememberTransformableState
        }

        val newScale = scale * zoomChange

        if (newScale > minScale) {
            scale = newScale
            offset = Offset((offset.x * zoomChange), (offset.y * zoomChange))
        } else {
            offset += offsetChange
        }
    }

    Box(
        modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()

                    if (painter.state !is AsyncImagePainter.State.Success) {
                        return@detectDragGestures
                    }
                    offset += dragAmount
                }
            }
            .transformable(
                state = state,
                lockRotationOnZoomPan = true
            )
            .background(Color.Black)
    ) {
        Image(
            painter = painter,
            contentDescription = "ZoomableImage",
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y,
                )
                .fillMaxSize(),
        )
        when (painter.state) {
            AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Loading -> {
                ImageAnimatedPlaceholder(state = painter.state, modifier = Modifier.align(Alignment.Center))
            }
            is AsyncImagePainter.State.Error -> {
                LaunchedEffect(painter.state) {
                    onImageError.invoke()
                }
                Text(
                    text = "Could not load image",
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is AsyncImagePainter.State.Success -> {
            }
        }
        FloatingActionButton(
            onClick = {
                ResetImageValueAnimator.getValueAnimator(
                    scale,
                    offset
                ).apply {
                    duration = 150
                    start()

                    addUpdateListener {
                        val array = it.animatedValue as FloatArray
                        scale = array[0]
                        offset = Offset(array[1], array[2])
                    }
                }
            },

            backgroundColor = MaterialTheme.colors.secondaryVariant,
            contentColor = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp)
                .align(Alignment.BottomEnd)
        ) {
            Image(
                painter = painterResource(id = (AppDrawable.ic_restart)),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                contentDescription = "Reset image"
            )
        }
    }
}
