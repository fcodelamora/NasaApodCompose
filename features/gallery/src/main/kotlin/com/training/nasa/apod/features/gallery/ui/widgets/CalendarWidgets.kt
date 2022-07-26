package com.training.nasa.apod.features.gallery.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.training.nasa.apod.common.resources.ui.AppDrawable
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.common.resources.ui.catalogs.views.ImageAnimatedPlaceholder
import com.training.nasa.apod.common.resources.ui.catalogs.views.MediaTypeIcon
import com.training.nasa.apod.common.resources.ui.catalogs.views.ScrollableTabRow
import com.training.nasa.apod.common.resources.ui.extensions.getThumbnailColorFilter
import com.training.nasa.apod.core.entities.CalendarEntry
import com.training.nasa.apod.core.entities.PictureOfTheDay
import timber.log.Timber
import timber.log.debug
import java.time.LocalDate

@ExperimentalCoilApi
@Composable
fun SingleMonthCalendarWidget(
    modifier: Modifier = Modifier,
    calendarEntries: List<CalendarEntry> = listOf(),
    onPreviewImage: (PictureOfTheDay, AsyncImagePainter) -> Unit = { _, _ -> }
) {

    if (calendarEntries.isEmpty()) {
        return
    }

    val calendarEntriesFiltered = remember {
        calendarEntries.filter {
            it is CalendarEntry.Day ||
                it is CalendarEntry.Util.EmptyDay ||
                it is CalendarEntry.Util.Padding
        }
    }
    Column(
        modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        calendarEntriesFiltered.let {
            CalendarWeek(it.subList(0, 7), onPreviewImage)
            CalendarWeek(it.subList(7, 14), onPreviewImage)
            CalendarWeek(it.subList(14, 21), onPreviewImage)
            CalendarWeek(it.subList(21, 28), onPreviewImage)
            if (it.size > 28) {
                CalendarWeek(it.subList(28, 35), onPreviewImage)
            }
            if (it.size > 35) {
                CalendarWeek(it.subList(35, it.size), onPreviewImage)
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CalendarWeek(
    calendarEntities: List<CalendarEntry> = listOf(),
    onPreviewImage: (PictureOfTheDay, AsyncImagePainter) -> Unit = { _, _ -> }

) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 1.dp)
    ) {
        calendarEntities.forEach {
            when (it) {
                is CalendarEntry.Day -> CalendarDay(
                    pictureOfTheDay = it.pictureOfTheDay,
                    onPreviewImage = onPreviewImage,
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp)
                )
                is CalendarEntry.Util.EmptyDay.PastDay -> CalendarEmptyDay(
                    Modifier
                        .weight(1f)
                        .padding(1.dp)
                )
                is CalendarEntry.Util.EmptyDay.FutureDay -> CalendarEmptyFutureDay(
                    Modifier
                        .weight(1f)
                        .padding(1.dp)
                )
                is CalendarEntry.Util.Padding -> CalendarDayPadding(
                    Modifier
                        .weight(1f)
                        .padding(1.dp)
                )
                else -> {
                    Timber.debug { "Received an unsupported CalendarEntry type: ${it.javaClass.name}" }
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CalendarDay(
    modifier: Modifier = Modifier,
    pictureOfTheDay: PictureOfTheDay? = null,
    onPreviewImage: (PictureOfTheDay, AsyncImagePainter) -> Unit = { _, _ -> }
) {
    val cornerRadiusSize = 30.0f

    // FIXME
    // Force an image size of aspect ratio 1:1 to prevent a glitch when loading images from cache.
    // The image would be loaded in its original aspect ratio, and then updated into the cropped image.
    // This was displayed as if the image expanded into the view right after it was loaded each time it was displayed.
    val thumbnailSize: Int = with(LocalDensity.current) {
        // 7 = number of images to display along the screen width ( 7 images per week taking the full screen width)
        //     this ignores the spacing between images at is not necessary to be that precise.
        (LocalConfiguration.current.screenWidthDp.dp.roundToPx() / 7)
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pictureOfTheDay?.thumbnail)
            .fallback(AppDrawable.ic_cloud)
            .size(thumbnailSize, thumbnailSize)
            .build()
    )

    Box(
        modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(cornerRadiusSize))
            .background(MaterialTheme.colors.secondary)
    ) {

        pictureOfTheDay?.let {
            Image(
                painter = painter,
                contentDescription = "Picture of the Day",
                contentScale = ContentScale.Crop,
                colorFilter = it.getThumbnailColorFilter(MaterialTheme.colors.secondary),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.5.dp)
                    .clip(RoundedCornerShape(cornerRadiusSize))
                    .background(color = MaterialTheme.colors.background)
                    .clickable { onPreviewImage.invoke(it, painter) }
            )

            // Loading placeholder should be displayed in front of the image and behind the text.
            ImageAnimatedPlaceholder(state = painter.state, modifier = Modifier.align(Alignment.Center))

            Text(
                text = it.dayOfMonthFormatted,
                fontSize = 10.sp,
                color = Color.White,
                style = MaterialTheme.typography.h4.copy(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 8f
                    )
                ),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 3.dp, end = 4.dp)
            )

            MediaTypeIcon(
                mediaType = it.mediaType,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 4.dp, end = 4.dp)
                    .size(10.dp)
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CalendarEmptyDay(
    modifier: Modifier = Modifier,
    placeholderResId: Int = AppDrawable.ic_close
) {

    Box(
        modifier
            .aspectRatio(1f)
            .padding(0.5.dp)
    ) {
        Icon(
            painter = painterResource(id = placeholderResId),
            contentDescription = null,
            tint = MaterialTheme.colors.secondary.copy(alpha = 0.7f),
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Center)
        )
    }
}

@ExperimentalCoilApi
@Composable
fun CalendarEmptyFutureDay(
    modifier: Modifier = Modifier
) {
    CalendarEmptyDay(
        modifier = modifier,
        placeholderResId = AppDrawable.ic_dot
    )
}

@ExperimentalCoilApi
@Composable
fun CalendarDayPadding(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(0.5.dp)
    )
}

@Composable
fun DateYearTabs(
    modifier: Modifier = Modifier,
    years: List<Int> = (1995..2020).toList(),
    selectedYear: Int = 2020,
    onYearSelected: (Int) -> Unit = {}
) {
    val yearsAsString = remember { years.map { it.toString() } }
    val selectedIndex = years.indexOfFirst { it == selectedYear }

    ScrollableTabRow(
        tabItems = yearsAsString,
        selectedIndex = selectedIndex,
        modifier = modifier,
        onTabSelected = { index -> onYearSelected.invoke(years[index]) }
    )
}

@ExperimentalCoilApi
@Preview(showSystemUi = true)
@Composable
private fun SingleMonthCalendarPreview() {
    AppUserInterface {
        SingleMonthCalendarWidget(
            calendarEntries = listOf(
                CalendarEntry.Util.Padding,
                CalendarEntry.Util.EmptyDay.PastDay,
                CalendarEntry.Util.EmptyDay.FutureDay,
                CalendarEntry.Day(
                    LocalDate.now(),
                    PictureOfTheDay(
                        dateString = "2021-12-01",
                        explanation = "",
                    )
                )
            )
        )
    }
}
