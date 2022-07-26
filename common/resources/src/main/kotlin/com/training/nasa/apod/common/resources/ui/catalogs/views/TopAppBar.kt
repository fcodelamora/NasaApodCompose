package com.training.nasa.apod.common.resources.ui.catalogs.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight
import com.training.nasa.apod.common.resources.ui.AppDrawable
import com.training.nasa.apod.common.resources.ui.catalogs.CatalogView

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentsColor: Color = MaterialTheme.colors.onPrimary,
    onNavigationIconClick: (() -> Unit)? = {}
) {
    Column(modifier) {
        Surface(
            color = backgroundColor,
            content = {},
            modifier = modifier
                .statusBarsHeight()
                .fillMaxWidth()
        )
        TopAppBar(
            backgroundColor = backgroundColor,
            title = {
                Text(
                    text = title,
                    color = contentsColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            elevation = 0.dp,
            navigationIcon = {
                IconButton(onClick = { onNavigationIconClick?.invoke() }) {
                    Icon(
                        painter = painterResource(id = AppDrawable.ic_arrow_back),
                        tint = contentsColor,
                        contentDescription = "Back"
                    )
                }
            }
        )
    }
}

private val transparentComponentBackgroundColor = Color.Black.copy(alpha = 0.3f)

@Composable
fun TransparentTopAppBarBackground(
    modifier: Modifier
) {
    Box(
        modifier
            .statusBarsHeight(50.dp)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        transparentComponentBackgroundColor,
                        Color.Transparent
                    )
                )
            )
    )
}

@Composable
fun TransparentStatusBarBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .statusBarsHeight(10.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        transparentComponentBackgroundColor,
                        Color.Transparent
                    ),
                    tileMode = TileMode.Repeated
                )
            )
            .alpha(0.6f)
    )
}

@Composable
fun TransparentTopAppBar(
    modifier: Modifier = Modifier,
    onNavigationIconClick: (() -> Unit)? = {}
) = TopAppBar(
    onNavigationIconClick = onNavigationIconClick,
    // Get a White arrow from TopBar
    backgroundColor = Color.Transparent,
    contentsColor = MaterialTheme.colors.secondaryVariant,
    modifier = modifier
)

@Preview
@Composable
fun TopAppBarCatalogPreview() {
    CatalogView {
        TopAppBar(title = "TopAppBar_1")
        DefaultDivider()
        TransparentStatusBarBackground()
        DefaultDivider()
        TransparentTopAppBar()
    }
}
