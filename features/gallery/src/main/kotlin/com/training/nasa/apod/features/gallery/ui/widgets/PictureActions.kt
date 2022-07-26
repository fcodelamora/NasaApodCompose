package com.training.nasa.apod.features.gallery.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.training.nasa.apod.common.resources.ui.AppDrawable
import com.training.nasa.apod.common.resources.ui.catalogs.CatalogView

@Composable
fun PictureActions(
    modifier: Modifier = Modifier,
    offsetDp: Float = 0f,
    onShareDocument: (() -> Unit)? = null,
    onDownloadToDevice: (() -> Unit)? = null,
    onRequestTranslate: (() -> Unit)? = null,
    onCheckSimilarImages: (() -> Unit)? = null,
) = Row(modifier = modifier) {

    val itemsBaseModifier = Modifier
        .weight(1f)
        .align(Alignment.CenterVertically)

    val enabledColorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
    val disabledColorFilter = ColorFilter.tint(MaterialTheme.colors.secondary.copy(0.3f))

    val isEnabled = listOf(
        onShareDocument != null,
        onDownloadToDevice != null,
        onRequestTranslate != null,
        onCheckSimilarImages != null
    )

    IconButton(
        onClick = onShareDocument ?: {},
        enabled = isEnabled[0],
        modifier = itemsBaseModifier.absoluteOffset(x = 0.dp, y = -offsetDp.dp)
    ) {
        Image(
            painter = painterResource(id = (AppDrawable.ic_share)),
            colorFilter = if (isEnabled[0]) enabledColorFilter else disabledColorFilter,
            contentDescription = "Share"
        )
    }
    IconButton(
        onClick = onDownloadToDevice ?: {},
        enabled = isEnabled[1],
        modifier = itemsBaseModifier
    ) {
        Image(
            painter = painterResource(id = (AppDrawable.ic_download)),
            colorFilter = if (isEnabled[1]) enabledColorFilter else disabledColorFilter,

            contentDescription = "Download"
        )
    }

    IconButton(
        onClick = onRequestTranslate ?: {},
        enabled = isEnabled[2],
        modifier = itemsBaseModifier
    ) {
        Image(
            painter = painterResource(id = (AppDrawable.g_translate)),
            colorFilter = if (isEnabled[2]) enabledColorFilter else disabledColorFilter,
            contentDescription = "Translate"
        )
    }
    IconButton(
        onClick = onCheckSimilarImages ?: {},
        enabled = isEnabled[3],

        modifier = itemsBaseModifier.absoluteOffset(x = 0.dp, y = -offsetDp.dp)
    ) {
        Image(
            painter = painterResource(id = (AppDrawable.search_web)),
            colorFilter = if (isEnabled[3]) enabledColorFilter else disabledColorFilter,
            contentDescription = "Image Search"
        )
    }
}

@Composable
@Preview(showSystemUi = true)
private fun PreviewPictureActions() {
    CatalogView {
        PictureActions()
    }
}
