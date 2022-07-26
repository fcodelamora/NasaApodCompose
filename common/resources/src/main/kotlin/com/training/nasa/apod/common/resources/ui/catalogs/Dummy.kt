package com.training.nasa.apod.common.resources.ui.catalogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.training.nasa.apod.common.resources.ui.catalogs.views.DefaultDivider

// Dummies Catalog for Previews.

@Composable
fun DummyText() {
    Text(
        text = "Dummy Text",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun DummyCatalogPreview() {
    CatalogView {
        DummyText()
        DefaultDivider()
    }
}
