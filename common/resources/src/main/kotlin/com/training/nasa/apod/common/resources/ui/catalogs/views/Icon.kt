package com.training.nasa.apod.common.resources.ui.catalogs.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.training.nasa.apod.common.resources.ui.AppDrawable
import com.training.nasa.apod.common.resources.ui.catalogs.CatalogView
import com.training.nasa.apod.core.entities.MediaType
import com.training.nasa.apod.core.entities.MediaType.IMAGE
import com.training.nasa.apod.core.entities.MediaType.UNKNOWN
import com.training.nasa.apod.core.entities.MediaType.VIDEO

// Icons

@Composable
fun MediaTypeIcon(
    modifier: Modifier = Modifier,
    mediaType: MediaType = IMAGE
) {

    lateinit var contentDescription: String
    lateinit var painterResource: Painter

    when (mediaType) {
        IMAGE -> {
            // Do Nothing
        }
        VIDEO -> {
            contentDescription = "Video Icon"
            painterResource = painterResource(AppDrawable.ic_video)
        }
        UNKNOWN -> {
            contentDescription = "URL Link Icon"
            painterResource = painterResource(AppDrawable.ic_url_link)
        }
    }

    if (mediaType != IMAGE) {
        Box(modifier) {
            Icon(
                painter = painterResource,
                tint = Color.Black.copy(alpha = 0.5f),
                contentDescription = "$contentDescription shadow",
                modifier = Modifier
                    .offset(x = 0.5.dp, y = 0.5.dp)
                    .align(Center)
            )
            Icon(
                painter = painterResource,
                tint = MaterialTheme.colors.secondaryVariant,
                contentDescription = contentDescription,
                modifier = Modifier.align(Center)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun IconsCatalogPreview() {
    CatalogView {
        MediaTypeIcon(mediaType = VIDEO)
        DefaultDivider()
        MediaTypeIcon(mediaType = UNKNOWN)
    }
}
