package com.training.nasa.apod.common.resources.ui.catalogs.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.training.nasa.apod.common.resources.ui.catalogs.CatalogView

// Compose Text

@Composable
fun Title1Text(
    modifier: Modifier = Modifier,
    text: String = ""
) {
    Text(
        text = text,
        color = MaterialTheme.colors.secondary.copy(alpha = 0.9f),
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}

@Composable
fun Title2Text(
    modifier: Modifier = Modifier,
    text: String = ""
) {
    Text(
        text = text,
        color = MaterialTheme.colors.secondary.copy(alpha = 0.9f),
        style = MaterialTheme.typography.h5,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}

@Composable
fun Caption1Text(
    modifier: Modifier = Modifier,
    text: String = ""
) = Text(
    text = text,
    fontSize = 15.sp,
    color = MaterialTheme.colors.contentColorFor(
        MaterialTheme.colors.background
    ).copy(alpha = 0.7f),
    style = MaterialTheme.typography.caption.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.0.sp
    ),
    textAlign = TextAlign.Justify,
    modifier = modifier,
)

@Composable
fun Contents2Text(
    modifier: Modifier = Modifier,
    text: String = ""
) = Text(
    text = text,
    style = MaterialTheme.typography.body2,
    textAlign = TextAlign.Justify,
    modifier = modifier,
)

@Composable
fun Information1Text(
    modifier: Modifier = Modifier,
    text: String = ""
) = Text(
    text = "ⓘ  $text",
    color = MaterialTheme.colors.secondary,
    fontSize = 10.sp,
    textAlign = TextAlign.Center,
    modifier = modifier
        .fillMaxWidth()
        .padding(20.dp),
)

@Composable
fun Information2Text(
    modifier: Modifier = Modifier,
    text: String = ""
) = Text(
    text = text,
    color = MaterialTheme.colors.secondary.copy(alpha = 0.5f),
    fontSize = 10.sp,
    textAlign = TextAlign.Center,
    modifier = modifier
)

@Composable
@Preview(showSystemUi = true)
private fun TextCatalogPreview() {
    CatalogView {
        Title1Text(text = "Title1Text")
        DefaultDivider()
        Caption1Text(text = "Caption1Text")
        DefaultDivider()
        Contents2Text(text = "Contents2Text")
        DefaultDivider()
        Information1Text(text = "Information1Text")
    }
}
