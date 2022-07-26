package com.training.nasa.apod.debug.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.common.resources.ui.catalogs.views.DefaultButton
import com.training.nasa.apod.common.resources.ui.catalogs.views.Information1Text
import com.training.nasa.apod.debug.ui.catalogs.SectionSubtitleText
import com.training.nasa.apod.debug.ui.catalogs.SectionTitleText
import com.training.nasa.apod.debug.ui.catalogs.views.NumericTextField
import com.training.nasa.apod.debug.ui.catalogs.views.SimpleSpinner
import com.training.nasa.apod.debug.viewmodels.DebugViewModel

object DebugScreen {

    object Default {

        @Composable
        fun Screen(viewmodel: DebugViewModel) {

            Content(
                delayInMillis = viewmodel.delay.value,
                availableErrorList = viewmodel.availableErrorList,
                apiErrorTypeSelectedIndex = viewmodel.apiErrorTypeSelectionIndex.value,
                onDelayInMillisUpdated = { newValue -> viewmodel.updateDelayInMillis(newValue) },
                onApiErrorSelected = { index ->
                    viewmodel.apiErrorTypeSelectionIndex.value = index
                },
                onUpdateFlags = { viewmodel.updateFlags() }
            )
        }

        @Composable
        fun Content(
            delayInMillis: String = "250",
            availableErrorList: List<String> = listOf("No Error"),
            apiErrorTypeSelectedIndex: Int = 0,
            onDelayInMillisUpdated: (String) -> Unit = {},
            onApiErrorSelected: (Int) -> Unit = {},
            onUpdateFlags: () -> Unit = {}
        ) {

            val scrollState = rememberScrollState()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(8.dp, 4.dp, 8.dp, 4.dp)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                Information1Text(text = "PRESS BACK TO CANCEL")

                SectionTitleText(text = "API")
                SectionSubtitleText(text = "Errors")
                SimpleSpinner(
                    availableErrorList,
                    apiErrorTypeSelectedIndex,
                    onApiErrorSelected
                )

                SectionSubtitleText(text = "API Response Delay (ms)")
                NumericTextField(
                    value = delayInMillis.toString(),
                    onValueChange = onDelayInMillisUpdated
                )

                DefaultButton(buttonText = "SET FLAGS") { onUpdateFlags.invoke() }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DebugScreenPreview() {
    AppUserInterface {
        DebugScreen.Default.Content()
    }
}
