package com.training.nasa.apod.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.navigationBarsPadding
import com.training.nasa.apod.BuildConfig
import com.training.nasa.apod.common.resources.ui.AppDrawable
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.common.resources.ui.catalogs.views.Information2Text
import com.training.nasa.apod.common.resources.ui.catalogs.views.RoundedSquareImageButton
import com.training.nasa.apod.common.resources.ui.catalogs.views.TextButton
import com.training.nasa.apod.common.resources.ui.catalogs.views.TopAppBar
import com.training.nasa.apod.common.resources.ui.catalogs.views.VerticalRadioButtonGroup
import com.training.nasa.apod.core.entities.AppTheme
import com.training.nasa.apod.core.entities.AppTheme.DARK
import com.training.nasa.apod.core.entities.AppTheme.DYNAMIC
import com.training.nasa.apod.core.entities.AppTheme.LIGHT
import com.training.nasa.apod.core.entities.AppTheme.RED
import com.training.nasa.apod.core.entities.AppTheme.SYSTEM
import com.training.nasa.apod.core.entities.UserPreferences
import com.training.nasa.apod.features.gallery.ui.screens.GalleryScreen
import java.time.Month

object SettingsScreen {

    @ExperimentalMaterialApi
    @ExperimentalCoilApi
    object Default {
        @Composable
        fun Screen(
            navController: NavController,
            userPreferences: UserPreferences,
            onThemeSelected: (AppTheme) -> Unit,
            onDynamicColorSelected: (Color) -> Unit,
            onNavigateToLicenses: () -> Unit,
            onShowBackButtonOnImagesChange: (Boolean) -> Unit,
        ) {
            Content(
                selectedTheme = userPreferences.selectedTheme,
                showBackButton = userPreferences.showBackButtonOnImages,
                onThemeSelected = { theme -> onThemeSelected.invoke(theme) },
                onPictureSelected = { color -> onDynamicColorSelected.invoke(color) },
                onNavigationIconClick = { navController.popBackStack() },
                onNavigateToLicenses = onNavigateToLicenses,
                onShowBackButtonOnImagesChange = onShowBackButtonOnImagesChange,
            )
        }

        @Composable
        fun Content(
            selectedTheme: AppTheme = DYNAMIC,
            showBackButton: Boolean = true,
            onThemeSelected: (AppTheme) -> Unit = {},
            onPictureSelected: (Color) -> Unit = {},
            onNavigationIconClick: () -> Unit = {},
            onNavigateToLicenses: () -> Unit = {},
            onShowBackButtonOnImagesChange: (Boolean) -> Unit = {},
        ) {

            val scrollState = rememberScrollState()

            Column(Modifier.fillMaxSize()) {
                TopAppBar(
                    title = "Settings",
                    onNavigationIconClick = onNavigationIconClick
                )
                Column(
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(10.dp)
                ) {
                    val cornerRadius = 30.dp

                    Box(
                        Modifier
                            .width(300.dp)
                            .height(310.dp)
                            .align(CenterHorizontally)
                            .background(
                                shape = RoundedCornerShape(cornerRadius),
                                color = MaterialTheme.colors.secondary
                            )
                    ) {
                        // Here, another screen composable is used as a demo screen
                        GalleryScreen.SingleMonth.Content(
                            isDemoMode = true,
                            disabledMonths = Month.values()
                                .filter {
                                    it !in listOf(Month.NOVEMBER, Month.DECEMBER)
                                },
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(RoundedCornerShape(cornerRadius))
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    RadioGroupThemes(
                        selectedTheme = selectedTheme,
                        onThemeSelected = { theme ->
                            onThemeSelected.invoke(theme)
                        },
                        onDynamicColorSelected = onPictureSelected
                    )

                    Spacer(Modifier.height(30.dp))

                    Row {
                        Row(Modifier.weight(2f)) {
                            Checkbox(
                                checked = showBackButton,
                                onCheckedChange = onShowBackButtonOnImagesChange
                            )
                            Text(
                                text = "Show back button on images",
                                modifier = Modifier.align(CenterVertically)
                            )
                            Spacer(Modifier.size(40.dp))
                        }
                        Box(Modifier.weight(1f)) {
                            Box(
                                Modifier
                                    .padding(start = 8.dp)
                                    .aspectRatio(1f)
                                    .background(
                                        shape = RoundedCornerShape(cornerRadius),
                                        color = MaterialTheme.colors.secondary
                                    )
                            ) {
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .padding(2.dp)
                                        .clip(RoundedCornerShape(cornerRadius))
                                ) {
                                    Image(
                                        painter = painterResource(AppDrawable.galaxy2),
                                        contentDescription = "",
                                        contentScale = ContentScale.FillBounds,
                                        modifier = Modifier
                                            .fillMaxSize()

                                    )
                                    if (showBackButton) {
                                        Icon(
                                            painter = painterResource(id = AppDrawable.ic_arrow_back),
                                            tint = MaterialTheme.colors.secondaryVariant,
                                            contentDescription = "Back",
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .size(28.dp)
                                                .align(Alignment.TopStart)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(30.dp))

                    AppInfo(Modifier.fillMaxWidth(), onNavigateToLicenses)

                    Spacer(Modifier.navigationBarsPadding())
                }
            }
        }
    }

    @Composable
    private fun AppInfo(
        modifier: Modifier = Modifier,
        onNavigateToLicenses: () -> Unit = {}
    ) {
        Column(modifier) {

            val sharedModifier = Modifier.align(CenterHorizontally)

            TextButton(buttonText = "SHOW LICENSES", modifier = sharedModifier) {
                onNavigateToLicenses.invoke()
            }

            Information2Text(
                text = "Version: ${BuildConfig.VERSION_NAME}",
                modifier = sharedModifier
            )
            if (BuildConfig.DEBUG) {
                Information2Text(text = BuildConfig.FLAVOR, modifier = sharedModifier)
            }
        }
    }

    @ExperimentalCoilApi
    @Composable
    private fun RadioGroupThemes(
        selectedTheme: AppTheme = DYNAMIC,
        onThemeSelected: (AppTheme) -> Unit = {},
        onDynamicColorSelected: (Color) -> Unit
    ) {

        val availableThemes = AppTheme.values()

        VerticalRadioButtonGroup(
            items = availableThemes.map { it.name },
            itemSupportComposables = availableThemes.map { theme ->
                // Test adding anonymous composables.
                when (theme) {
                    DYNAMIC -> {
                        @Composable {
                            DynamicThemeEntryDetails(
                                selectedTheme = selectedTheme,
                                onImageClick = { selectedColor ->
                                    onDynamicColorSelected.invoke(selectedColor)
                                }
                            )
                        }
                    }
                    SYSTEM,
                    LIGHT,
                    DARK,
                    RED -> {
                        @Composable {
                            val string = when (theme) {
                                SYSTEM -> "Follow system light/dark Mode"
                                LIGHT -> "Light theme"
                                DARK -> "Dark theme"
                                RED -> "Custom theme"
                                else -> throw RuntimeException("${theme.name} should use a different composable")
                            }
                            Text(
                                text = string,
                                style = TextStyle(fontStyle = FontStyle.Italic),
                                color = MaterialTheme.colors.secondary.copy(alpha = 0.75F)
                            )
                        }
                    }
                }
            },
            selectedItemIndex = availableThemes.indexOf(selectedTheme),
            onItemSelected = { index ->
                onThemeSelected.invoke(availableThemes[index])
            }
        )
    }

    @ExperimentalCoilApi
    @Composable
    fun DynamicThemeEntryDetails(
        selectedTheme: AppTheme,
        onImageClick: (Color) -> Unit
    ) {

        Text(
            text = "Adapt to the currently selected image",
            style = TextStyle(fontStyle = FontStyle.Italic),
            color = MaterialTheme.colors.secondary.copy(alpha = 0.75F)
        )

        if (selectedTheme == DYNAMIC) {
            Text(
                text = "Try below examples:",
                style = TextStyle(fontStyle = FontStyle.Italic),
                color = MaterialTheme.colors.secondary.copy(alpha = 0.75F)
            )

            Spacer(Modifier.height(5.dp))

            Row(Modifier.fillMaxWidth()) {
                val imageModifier = Modifier
                    .size(50.dp)

                val spacerModifier = Modifier
                    .width(5.dp)

                RoundedSquareImageButton(
                    modifier = imageModifier,
                    drawableResId = AppDrawable.juno,
                    onImageClick = {
                        onImageClick.invoke(Color(0xFFFFC87F))
                    }
                )

                Spacer(spacerModifier)

                RoundedSquareImageButton(
                    modifier = imageModifier,
                    drawableResId = AppDrawable.galaxy1,
                    onImageClick = {
                        onImageClick.invoke(Color(0xFFFF9999))
                    }
                )

                Spacer(spacerModifier)

                RoundedSquareImageButton(
                    modifier = imageModifier,
                    drawableResId = AppDrawable.galaxy2,
                    onImageClick = {
                        onImageClick.invoke(Color(0xFFFF8000))
                    }
                )

                Spacer(spacerModifier)

                RoundedSquareImageButton(
                    modifier = imageModifier,
                    drawableResId = AppDrawable.galaxy3,
                    onImageClick = {
                        onImageClick.invoke(Color(0xFFB19CD9))
                    }
                )
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showSystemUi = true)
@Composable
private fun SettingScreenDefaultPreview() {
    AppUserInterface {
        SettingsScreen.Default.Content()
    }
}
