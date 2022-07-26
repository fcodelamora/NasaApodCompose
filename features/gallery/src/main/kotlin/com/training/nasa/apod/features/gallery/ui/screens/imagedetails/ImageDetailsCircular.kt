package com.training.nasa.apod.features.gallery.ui.screens.imagedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import com.training.nasa.apod.common.resources.ui.AppDrawable
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.common.resources.ui.NasaApodScreens
import com.training.nasa.apod.common.resources.ui.catalogs.views.Caption1Text
import com.training.nasa.apod.common.resources.ui.catalogs.views.Contents2Text
import com.training.nasa.apod.common.resources.ui.catalogs.views.ImageAnimatedPlaceholder
import com.training.nasa.apod.common.resources.ui.catalogs.views.MediaTypeIcon
import com.training.nasa.apod.common.resources.ui.catalogs.views.SecondaryDivider
import com.training.nasa.apod.common.resources.ui.catalogs.views.Title1Text
import com.training.nasa.apod.common.resources.ui.catalogs.views.TransparentTopAppBarBackground
import com.training.nasa.apod.common.resources.ui.extensions.getThumbnailColorFilter
import com.training.nasa.apod.core.entities.MediaType
import com.training.nasa.apod.core.entities.PictureOfTheDay
import com.training.nasa.apod.core.entities.UserPreferences
import com.training.nasa.apod.features.gallery.R
import com.training.nasa.apod.features.gallery.ui.widgets.ColorStripes
import com.training.nasa.apod.features.gallery.ui.widgets.PictureActions
import com.training.nasa.apod.features.gallery.viewmodel.ImageDetailsViewModel

open class ImageDetailsCircular {
    @Composable
    fun Screen(
        viewModel: ImageDetailsViewModel,
        navController: NavController,
        userPreferences: UserPreferences,
    ) {
        val context = LocalContext.current

        Content(
            pictureOfTheDay = viewModel.pictureOfTheDay,
            showBackButton = userPreferences.showBackButtonOnImages,
            onShowFullScreenMedia = {

                when (viewModel.pictureOfTheDay?.mediaType) {
                    MediaType.IMAGE -> {
                        navController.navigate(NasaApodScreens.IMAGE_VIEWER.name)
                    }
                    MediaType.VIDEO,
                    MediaType.UNKNOWN -> {
                        viewModel.showInExternalBrowser(context)
                    }
                }
            },
            onShareDocument = { viewModel.shareDocument(context) },
            onCheckSimilarImages = { viewModel.showSimilarImages(context) },
            onBackClick = { navController.popBackStack() }
        )
    }

    @Composable
    fun Content(
        pictureOfTheDay: PictureOfTheDay? = null,
        showBackButton: Boolean = false,
        onShareDocument: (() -> Unit)? = null,
        onShowFullScreenMedia: () -> Unit = { },
        onDownloadToDevice: (() -> Unit)? = null,
        onCheckSimilarImages: (() -> Unit)? = null,
        onBackClick: () -> Unit = {}
    ) = Box(Modifier.fillMaxSize()) {

        val scrollState = rememberScrollState()

        // Picture of the Day aspect ratio
        val aspectRatio = 1.0f

        val statusBarHeight = LocalWindowInsets.current.statusBars.top
        val screenHeightDp = LocalConfiguration.current.screenHeightDp
        val screenWidthDp = LocalConfiguration.current.screenWidthDp

        val imageHeightDp = (screenWidthDp / aspectRatio)

        // Screen animations are only concerned on the scrolling that occurs while the image is displayed
        val animationScrollPixels =
            with(LocalDensity.current) { imageHeightDp.dp.roundToPx() }.toFloat() - statusBarHeight

        val scrollCurrentValue =
            kotlin.math.min(scrollState.value.toFloat(), animationScrollPixels)

        // From 0.0 to 1.0, how much of the image has been scrolled by
        val scrollProgress = if (animationScrollPixels == 0f)
            0f
        else
            (scrollCurrentValue / animationScrollPixels)

        val remainingScrollProgress = 1 - scrollProgress

        // Change the circle asset Box Height to simulate the circle get flattened.
        val circleCollapsedFinalLineHeightDp = 2f // .dp conversion is done later.
        val semiCircleHeightDp = imageHeightDp / 4

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pictureOfTheDay?.thumbnail)
                .crossfade(true)
                .fallback(AppDrawable.ic_cloud)
                .scale(Scale.FILL)
                .build()
        )
        pictureOfTheDay?.let {
            Column(
                Modifier
                    .verticalScroll(state = scrollState)
            ) {

                var heightDp = (semiCircleHeightDp * (remainingScrollProgress))
                heightDp = if (heightDp <= circleCollapsedFinalLineHeightDp)
                    circleCollapsedFinalLineHeightDp
                else
                    heightDp

                Box(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(aspectRatio)
                        .background(MaterialTheme.colors.primary)
                ) {
                    Image(
                        painter = painter,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Picture of the Day",
                        colorFilter = it.getThumbnailColorFilter(MaterialTheme.colors.secondaryVariant),
                        modifier = Modifier
                            .matchParentSize()
                            .scale(1 + scrollProgress)
                            .alpha(remainingScrollProgress)
                            .clickable {
                                onShowFullScreenMedia.invoke()
                            }
                    )

                    ImageAnimatedPlaceholder(state = painter.state, modifier = Modifier.align(Center))

                    if (showBackButton) {
                        TransparentTopAppBarBackground(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter)
                        )
                    }

                    Box(
                        Modifier
                            .height(heightDp.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.im_half_circle_v2),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxSize()

                        )

                        // Makes sure that the minimum visible dynamic color line is 2dp
                        SecondaryDivider(
                            Modifier
                                .fillMaxWidth()
                                .height(circleCollapsedFinalLineHeightDp.dp)
                                .align(Alignment.BottomCenter)
                        )

                        Image(
                            painter = painterResource(R.drawable.im_half_circle_v2),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.background),
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(x = 0.dp, y = circleCollapsedFinalLineHeightDp.dp)
                        )
                    }
                }

                Box(Modifier.navigationBarsPadding()) {
                    Column(
                        Modifier
                            .defaultMinSize(minHeight = (screenHeightDp).dp)
                            .background(MaterialTheme.colors.background)
                            .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 48.dp)
                            .align(Alignment.TopCenter)
                    ) {

                        val isMediaTypeUnknown = MediaType.UNKNOWN == it.mediaType

                        PictureActions(
                            offsetDp = heightDp / 3,
                            onShareDocument = onShareDocument,
                            onDownloadToDevice = if (isMediaTypeUnknown) null else onDownloadToDevice,
                            onRequestTranslate = null,
                            onCheckSimilarImages = if (isMediaTypeUnknown) null else onCheckSimilarImages,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Caption1Text(
                            text = pictureOfTheDay.dateAsSimpleString ?: "-",
                            modifier = Modifier.align(CenterHorizontally)
                        )

                        Spacer(Modifier.size(4.dp))

                        Title1Text(
                            text = it.title,
                            modifier = Modifier.align(CenterHorizontally)
                        )

                        Spacer(Modifier.size(10.dp))

                        Text(it.explanation)

                        Spacer(Modifier.size(10.dp))

                        it.copyright?.let { copyright ->
                            Contents2Text(
                                text = "© $copyright",
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.End)
                            )
                        }
                    }

                    // Lower Stripes
                    ColorStripes(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    )
                }
            }

            if (showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .statusBarsPadding()
                        .align(Alignment.TopStart)
                        .offset(x = (-100 * scrollProgress).dp, y = 0.dp)
                        .size(56.dp)
                ) {
                    Icon(
                        painter = painterResource(id = AppDrawable.ic_arrow_back),
                        tint = MaterialTheme.colors.secondaryVariant,
                        contentDescription = "Back Button",
                    )
                }
            }

            MediaTypeIcon(
                mediaType = it.mediaType,
                modifier = Modifier
                    .statusBarsPadding()
                    .align(Alignment.TopEnd)
                    .offset(x = (100 * scrollProgress).dp, y = 0.dp)
                    .size(56.dp)
            )

            if (remainingScrollProgress == 0f) {
                Box(
                    Modifier
                        .statusBarsHeight()
                        .background(MaterialTheme.colors.primary)
                ) {
                    // Makes sure that the minimum visible dynamic color line is 2dp
                    SecondaryDivider(
                        Modifier
                            .fillMaxWidth()
                            .height(circleCollapsedFinalLineHeightDp.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ImageDetailsCircularPreview() {
    AppUserInterface {
        ImageDetailsCircular().Content()
    }
}
