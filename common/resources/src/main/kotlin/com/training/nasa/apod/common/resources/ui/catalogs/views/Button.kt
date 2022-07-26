package com.training.nasa.apod.common.resources.ui.catalogs.views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.training.nasa.apod.common.resources.ui.AppDrawable
import com.training.nasa.apod.common.resources.ui.catalogs.CatalogView

// Button Catalog

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit
) = Button(
    onClick = onClick,
    modifier = modifier
) {
    Text(text = buttonText, color = MaterialTheme.colors.secondaryVariant)
}

@Composable
fun OutlineButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit
) = Button(
    onClick = onClick,
    border = BorderStroke(2.dp, MaterialTheme.colors.secondary),
    modifier = modifier
) {
    Text(text = buttonText, color = MaterialTheme.colors.secondaryVariant)
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        contentColor = MaterialTheme.colors.secondary.copy(alpha = 0.5f),
        modifier = modifier
            .clip(shape = RoundedCornerShape(percent = 30))
            .clickable { onClick.invoke() }

    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.caption.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondary.copy(alpha = 0.7f)
            ),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        )
    }
}

// From JetCaster sample
@Composable
fun TabButton(
    modifier: Modifier = Modifier,
    text: String = "",
    isEnabled: Boolean = false,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {

    val strokeColor = if (selected) MaterialTheme.colors.secondary else Color.Transparent
    val shape = remember { RoundedCornerShape(percent = 50) }

    var updatedModifier = modifier
        .clip(shape)
        .border(BorderStroke(2.dp, strokeColor), shape)

    if (isEnabled) {
        updatedModifier = updatedModifier.clickable { onClick.invoke() }
    }

    Surface(
        color = Color.Transparent,
        contentColor = when {
            selected -> MaterialTheme.colors.secondaryVariant
            else -> MaterialTheme.colors.secondary.copy(alpha = 0.5f)
        },
        modifier = updatedModifier

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2.copy(
                fontWeight = if (isEnabled) FontWeight.Bold else FontWeight.Normal,
                color = if (isEnabled) MaterialTheme.colors.secondary else MaterialTheme.colors.secondary.copy(
                    alpha = 0.5f
                ),
                textDecoration = if (isEnabled.not()) TextDecoration.LineThrough else null,
            ),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        )
    }
}

@ExperimentalCoilApi
@Composable
fun RoundedSquareImageButton(
    modifier: Modifier = Modifier,
    @DrawableRes drawableResId: Int,
    onImageClick: () -> Unit = {}
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(drawableResId)
            .crossfade(true)
            .scale(Scale.FIT)
            .build()
    )
    val cornerRadius = 30f

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                shape = RoundedCornerShape(cornerRadius),
                color = MaterialTheme.colors.secondary
            )
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(1.5.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .background(color = MaterialTheme.colors.background)
                .clickable { onImageClick.invoke() }
        )
    }
}

@ExperimentalCoilApi
@Preview(showSystemUi = true)
@Composable
private fun ButtonCatalogPreview() {
    CatalogView {
        DefaultButton(buttonText = "DefaultButton") {}
        DefaultDivider()
        OutlineButton(buttonText = "OutlineButton") {}
        DefaultDivider()
        TextButton(buttonText = "TextButton") {}
        DefaultDivider()
        TabButton(text = "TabButton", selected = false, isEnabled = false)
        TabButton(text = "TabButton", selected = false, isEnabled = true)
        TabButton(text = "TabButton", selected = true, isEnabled = true)
        DefaultDivider()
        RoundedSquareImageButton(drawableResId = AppDrawable.galaxy2, modifier = Modifier.size(50.dp))
    }
}
