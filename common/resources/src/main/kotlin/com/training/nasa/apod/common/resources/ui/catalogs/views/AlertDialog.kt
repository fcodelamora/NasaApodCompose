package com.training.nasa.apod.common.resources.ui.catalogs.views

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.training.nasa.apod.common.resources.ui.catalogs.CatalogView
import com.training.nasa.apod.core.entities.ErrorViewData

// AlertDialogs

@Composable
fun ErrorDialog(errorViewData: ErrorViewData, onOkPressed: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = { errorViewData.title?.let { Text(it) } },
        text = { Text(errorViewData.errorMessage) },
        confirmButton = {
            TextButton(
                onClick = {
                    onOkPressed.invoke()
                }
            ) {
                Text("OK", color = MaterialTheme.colors.secondary)
            }
        }
    )
}

// FIXME - Preview not showing
@Preview(showSystemUi = true)
@Composable
private fun AlertDialogsCatalogPreview() {
    CatalogView {
        ErrorDialog(ErrorViewData("title", "msg", "1234")) {}
    }
}
