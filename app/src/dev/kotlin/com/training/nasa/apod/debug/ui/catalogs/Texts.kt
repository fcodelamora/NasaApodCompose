package com.training.nasa.apod.debug.ui.catalogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.training.nasa.apod.common.resources.ui.catalogs.CatalogView
import com.training.nasa.apod.common.resources.ui.catalogs.views.DefaultDivider

// dev-only TextStyles

@Composable
fun SectionTitleText(text: String) = Text(
    text = text,
    modifier = Modifier
        .clip(RoundedCornerShape(30.0f))
        .background(MaterialTheme.colors.primary)
        .fillMaxWidth()
        .padding(5.dp),
    color = MaterialTheme.colors.secondary,
    fontSize = 23.sp,
    textAlign = TextAlign.Center
)

@Composable
fun SectionSubtitleText(text: String) = Text(
    text = text,
    modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
    color = MaterialTheme.colors.secondary,
    fontSize = 20.sp,
    textAlign = TextAlign.Start
)

@Preview
@Composable
fun TextStylesPreview() {
    CatalogView {
        SectionTitleText("SectionTitle")
        DefaultDivider()
        SectionSubtitleText("SectionSubtitle")
    }
}
