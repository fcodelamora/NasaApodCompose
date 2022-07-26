package com.training.nasa.apod.features.gallery.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.training.nasa.apod.common.resources.ui.catalogs.CatalogView

@Composable
fun ColorStripes(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        val color = MaterialTheme.colors.secondary.copy(alpha = 0.85F)
        Surface(
            color = color,
            content = {},
            modifier = Modifier
                .height(5.dp)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Surface(
            color = color,
            content = {},
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview(showSystemUi = true)
private fun PreviewColorStripes() {
    CatalogView {
        ColorStripes()
    }
}
