package com.training.nasa.apod.debug.ui.catalogs.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.training.nasa.apod.common.resources.ui.catalogs.CatalogView

// dev-only Spinners

@Composable
fun SimpleSpinner(listValues: List<String>, selectedIndex: Int, onSelected: (Int) -> Unit) = Column(
    Modifier.fillMaxWidth()
) {

    var expanded by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf(listValues[selectedIndex]) }

    Button(
        onClick = { expanded = !expanded }
    ) {
        Text(text = buttonText, color = MaterialTheme.colors.secondary)
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            tint = MaterialTheme.colors.secondary,
            contentDescription = null,
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        listValues.forEachIndexed { index, label ->
            DropdownMenuItem(onClick = {
                expanded = false
                onSelected(index)
                buttonText = label
            }) {
                Text(text = label)
            }
        }
    }
}

@Preview
@Composable
fun SpinnerCatalogPreview() {
    CatalogView {
        SimpleSpinner(listOf("text1", "text2", "text3"), 0) {}
    }
}
