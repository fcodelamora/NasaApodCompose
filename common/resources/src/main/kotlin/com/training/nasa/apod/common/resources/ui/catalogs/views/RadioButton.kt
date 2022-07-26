package com.training.nasa.apod.common.resources.ui.catalogs.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.training.nasa.apod.common.resources.ui.catalogs.CatalogView

// RadioButtons

@Composable
fun VerticalRadioButtonGroup(
    modifier: Modifier = Modifier,
    items: List<String> = emptyList(),
    itemSupportComposables: List<@Composable () -> Unit> = emptyList(),
    selectedItemIndex: Int = 0,
    onItemSelected: (Int) -> Unit = {},
) {

    require(items.size == itemSupportComposables.size) { "items and itemSupportComposable size must be equal" }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        items.forEachIndexed { index, text ->
            val isSelected = selectedItemIndex == index

            Row {
                RadioButton(
                    selected = isSelected,
                    modifier = Modifier
                        .wrapContentHeight()
                        .align(Alignment.Top),
                    onClick = { onItemSelected.invoke(index) }
                )
                Column {
                    Text(text)
                    itemSupportComposables[index]()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RadioButtonsCatalogPreview() {
    val testItems = listOf("ONE", "TWO", "THREE")
    CatalogView {
        VerticalRadioButtonGroup(
            items = testItems.map { it },
            itemSupportComposables = testItems.map { @Composable { Text("Support Composable for $it") } }
        )
    }
}
