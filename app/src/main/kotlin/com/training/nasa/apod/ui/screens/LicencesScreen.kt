package com.training.nasa.apod.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.common.resources.ui.catalogs.theme.Gray400
import com.training.nasa.apod.common.resources.ui.catalogs.views.ScrollableTabRow
import com.training.nasa.apod.common.resources.ui.catalogs.views.TopAppBar

object LicencesScreen {

    object Default {
        @Composable
        fun Screen(navController: NavController) {
            Content(onBackClick = { navController.popBackStack() })
        }

        @Composable
        fun Content(
            onBackClick: () -> Unit = {},
        ) {

            val selectedIndex = rememberSaveable { mutableStateOf(0) }

            Column(Modifier.fillMaxSize()) {
                TopAppBar(
                    title = "Licenses",
                    onNavigationIconClick = onBackClick
                )

                ScrollableTabRow(
                    tabItems = listOf("SOFTWARE", "ASSETS"),
                    selectedIndex = selectedIndex.value,
                    onTabSelected = { index -> selectedIndex.value = index }
                )

                when (selectedIndex.value) {
                    0 -> SoftwareLicenses()
                    1 -> AssetsLicenses()
                }
            }
        }
    }
}

@Composable
private fun SoftwareLicenses() {
    /* Force-change the surface color on dark themes as the HTML is
      unreadable otherwise */
    val materialColors =
        if (MaterialTheme.colors.isLight.not())
            MaterialTheme.colors.copy(surface = Gray400)
        else
            MaterialTheme.colors

    MaterialTheme(colors = materialColors) {
        Column(Modifier.padding(horizontal = 8.dp)) {
            LibrariesContainer(Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun AssetsLicenses() {

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {
        AssetLicenseEntry(
            "Google Material Icons\n" +
                "https://fonts.google.com/icons\n" +
                "Apache License Version 2.0"
        )

        Spacer(Modifier.size(4.dp))

        AssetLicenseEntry(
            "Icon Kitchen\n" +
                "https://icon.kitchen/\n" +
                "Apache License Version 2.0"
        )

        Spacer(Modifier.size(4.dp))

        AssetLicenseEntry(
            "Louiemantia、すけじょ\n" +
                "Hanafuda August Hikari.svgの色違いバージョン（伝統色バージョン）\n" +
                "CC-BY-SA 4.0"
        )

        Spacer(Modifier.size(4.dp))

        AssetLicenseEntry(
            text = "Ision Industries\n" +
                "https://lottiefiles.com/93228-fading-cubes-loader-1 \n" +
                "https://lottiefiles.com/40251-network-activity-icon \n" +
                "Lottie Simple License (FL 9.13.21)"
        )
    }
}

@Composable
private fun AssetLicenseEntry(text: String) =
    Row {
        Text("・")
        Text(text)
    }

@Preview(showSystemUi = true)
@Composable
private fun LicencesScreenDefaultPreview() {
    AppUserInterface {
        LicencesScreen.Default.Content()
    }
}
