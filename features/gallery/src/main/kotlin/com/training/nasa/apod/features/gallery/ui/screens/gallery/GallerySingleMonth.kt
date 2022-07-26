package com.training.nasa.apod.features.gallery.ui.screens.gallery

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.training.nasa.apod.common.resources.ui.AppDrawable
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.common.resources.ui.NasaApodScreens
import com.training.nasa.apod.common.resources.ui.catalogs.views.DateMonthTabs
import com.training.nasa.apod.common.resources.ui.catalogs.views.ImageAnimatedPlaceholder
import com.training.nasa.apod.common.resources.ui.catalogs.views.Information1Text
import com.training.nasa.apod.common.resources.ui.extensions.getThumbnailColorFilter
import com.training.nasa.apod.core.entities.CalendarEntry
import com.training.nasa.apod.core.entities.MediaType
import com.training.nasa.apod.core.entities.NasaServer
import com.training.nasa.apod.core.entities.PictureOfTheDay
import com.training.nasa.apod.features.gallery.R
import com.training.nasa.apod.features.gallery.ui.screens.GalleryScreen
import com.training.nasa.apod.features.gallery.ui.widgets.DateYearTabs
import com.training.nasa.apod.features.gallery.ui.widgets.SingleMonthCalendarWidget
import com.training.nasa.apod.features.gallery.viewmodel.GalleryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Month

@ExperimentalCoilApi
open class GallerySingleMonth {
    @Composable
    fun Screen(
        viewModel: GalleryViewModel,
        navController: NavController,
        onPreviewPictureLoadSuccess: (Drawable) -> Unit,
        onViewImageDetails: (PictureOfTheDay) -> Unit
    ) {

        val currentYear = remember { NasaServer.currentNasaDate.year }

        Content(
            isDemoMode = false,
            calendarEntries = viewModel.calendarEntries,
            disabledMonths = viewModel.disabledMonths,
            currentYear = currentYear,
            selectedYear = viewModel.selectedYear,
            selectedMonth = viewModel.selectedMonth,
            pictureOfTheDayToPreview = viewModel.pictureOfTheDay,
            pictureOfTheDayToPreviewPainter = viewModel.pictureOfTheDayPreviewPainter.value,
            onPreviewPicture = { pictureOfTheDay, painter ->
                viewModel.setSelectedPictureOfTheDay(pictureOfTheDay, painter)
            },
            onPreviewPictureLoadSuccess = onPreviewPictureLoadSuccess,
            onViewImageDetails = {

                // FIXME: Persist received data and share only the ID on the navigate function.
                val currentSelectedPicture = viewModel.pictureOfTheDay ?: return@Content
                onViewImageDetails.invoke(currentSelectedPicture)

                navController.navigate(NasaApodScreens.IMAGE_DETAILS.name)
            },
            onYearSelected = { year ->
                viewModel.onYearSelected(year)
            },
            onMonthSelected = { month ->
                viewModel.onMonthSelected(month)
            },
            onSettingsIconClick = { navController.navigate(NasaApodScreens.SETTINGS.name) }
        )

        DisposableEffect(null) {
            viewModel.getCurrentMonthPictures()
            onDispose {}
        }
    }

    @Composable
    fun Content(
        // Allow for a modifier to reuse this screen as a sample view in Settings
        modifier: Modifier = Modifier,
        isDemoMode: Boolean = false,
        coroutineScope: CoroutineScope = rememberCoroutineScope(),
        currentYear: Int = 2020,
        currentMonth: Month = Month.DECEMBER,
        calendarEntries: List<CalendarEntry> = listOf(),
        disabledMonths: List<Month> = listOf(),
        pictureOfTheDayToPreview: PictureOfTheDay? = null,
        pictureOfTheDayToPreviewPainter: AsyncImagePainter? = null,
        onPreviewPicture: (PictureOfTheDay?, AsyncImagePainter) -> Unit = { _, _ -> },
        onPreviewPictureLoadSuccess: (Drawable) -> Unit = { },
        onViewImageDetails: () -> Unit = {},
        selectedYear: Int = currentYear,
        selectedMonth: Month? = currentMonth,
        onYearSelected: (Int) -> Unit = {},
        onMonthSelected: (Month) -> Unit = {},
        onSettingsIconClick: () -> Unit = {}
    ) {

        val upperBackGroundColor = MaterialTheme.colors.primary
        val lowerBackGroundColor = MaterialTheme.colors.background

        val availableYears = remember { (1995..currentYear).toList() }

        val drawable = (pictureOfTheDayToPreviewPainter?.state as? AsyncImagePainter.State.Success)?.result?.drawable

        LaunchedEffect(drawable) {
            drawable?.let {
                onPreviewPictureLoadSuccess.invoke(it)
            }
        }

        val scrollState = rememberScrollState()

        Column(
            modifier
                .fillMaxSize()
                .background(lowerBackGroundColor)
                .verticalScroll(scrollState)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.60f)
                    .background(upperBackGroundColor) // .background(brush = brush)

            ) {

                val topPadding = if (isDemoMode)
                    0.dp else
                    with(LocalDensity.current) {
                        LocalWindowInsets.current.statusBars.top.toDp()
                    }

                Box(
                    Modifier
                        .padding(top = topPadding)
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.hanafuda_hill_bg),
                        contentDescription = "Background-BG",
                        colorFilter = ColorFilter.tint(lowerBackGroundColor),
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    )
                    Image(
                        painter = painterResource(R.drawable.hanafuda_hill_fg),
                        contentDescription = "Background-FG",
                        contentScale = ContentScale.FillWidth,
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)

                    )

                    // Stroke is 7.dp, divided by 2 as padding is applied at the top and bottom
                    val circleBorderStrokeWidthAsPadding = 3.5.dp

                    Box(
                        Modifier
                            .padding(vertical = 16.dp)
                            .align(Alignment.Center)
                            .aspectRatio(1.0f)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colors.secondary)
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(vertical = circleBorderStrokeWidthAsPadding)
                                .align(Alignment.Center)
                                .aspectRatio(1.0f)
                                .clip(CircleShape)
                                .background(color = lowerBackGroundColor)
                        ) {
                            val basePainter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(pictureOfTheDayToPreview?.thumbnail)
                                    .crossfade(true)
                                    .build()
                            )

                            val activePainter = if (
                                pictureOfTheDayToPreviewPainter == null ||
                                pictureOfTheDayToPreviewPainter.state is AsyncImagePainter.State.Success
                            ) {
                                basePainter
                            } else {
                                pictureOfTheDayToPreviewPainter
                            }

                            Image(
                                painter = activePainter,
                                contentScale = ContentScale.Crop,
                                contentDescription = "Picture of the Day",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .aspectRatio(1.0f)
                                    .clickable { onViewImageDetails.invoke() }
                            )

                            ImageAnimatedPlaceholder(
                                state = activePainter.state,
                                modifier = Modifier.align(Alignment.Center)
                            )

                            // Can not use a fallback image as the colorFilter is removed too quickly
                            // and the icon can be seen white for an instant.
                            if (pictureOfTheDayToPreview?.mediaType == MediaType.UNKNOWN) {
                                Image(
                                    painter = painterResource(id = AppDrawable.ic_cloud),
                                    contentDescription = "Picture of the Day for URL",
                                    colorFilter = pictureOfTheDayToPreview.getThumbnailColorFilter(
                                        MaterialTheme.colors.secondary
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .aspectRatio(1.0f)
                                        // Make the background somewhat transparent so the transition
                                        // animation is displayed behind
                                        .background(MaterialTheme.colors.background.copy(alpha = 0.5f))
                                        .clickable { onViewImageDetails.invoke() }
                                )
                            }
                        }
                    }
                    IconButton(
                        onClick = { onSettingsIconClick.invoke() },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            painter = painterResource(id = AppDrawable.ic_settings),
                            contentDescription = "Settings Icon",
                            tint = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.85f),
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            DateYearTabs(
                years = availableYears,
                selectedYear = selectedYear,
                onYearSelected = onYearSelected,
            )

            Spacer(Modifier.height(8.dp))

            DateMonthTabs(
                selectedMonth = selectedMonth,
                disabledMonths = disabledMonths,
                onMonthSelected = onMonthSelected,
            )

            // Allow for preview of Content without unnecessary further widget additions.
            if (isDemoMode.not()) {

                Spacer(Modifier.height(10.dp))

                if (selectedMonth != null) {
                    SingleMonthCalendarWidget(
                        calendarEntries = calendarEntries,
                        onPreviewImage = { pictureOfTheDay, painter ->
                            coroutineScope.launch {
                                onPreviewPicture.invoke(pictureOfTheDay, painter)
                            }
                        },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                } else {
                    Information1Text(
                        text = "SELECT A YEAR AND MONTH COMBINATION"
                    )
                }
                Spacer(
                    Modifier
                        .navigationBarsWithImePadding()
                        .height(18.dp)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Preview(name = "SingleMonth", widthDp = 300, heightDp = 800)
@Composable
private fun GallerySingleMonthScreenLong() {
    AppUserInterface {
        GalleryScreen.SingleMonth.Content()
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Preview(name = "SingleMonth", widthDp = 800, heightDp = 800)
@Composable
private fun GallerySingleMonthScreenSquared() {
    AppUserInterface {
        GalleryScreen.SingleMonth.Content()
    }
}
