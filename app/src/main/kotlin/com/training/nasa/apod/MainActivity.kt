package com.training.nasa.apod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.training.nasa.apod.common.di.UseCaseProvider
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.common.resources.ui.NasaApodScreens.GALLERY
import com.training.nasa.apod.common.resources.ui.NasaApodScreens.IMAGE_DETAILS
import com.training.nasa.apod.common.resources.ui.NasaApodScreens.IMAGE_VIEWER
import com.training.nasa.apod.common.resources.ui.NasaApodScreens.LICENSES
import com.training.nasa.apod.common.resources.ui.NasaApodScreens.SETTINGS
import com.training.nasa.apod.common.resources.ui.catalogs.Animations
import com.training.nasa.apod.common.resources.ui.catalogs.views.ErrorDialog
import com.training.nasa.apod.common.resources.ui.catalogs.views.LoadingFromNetworkView
import com.training.nasa.apod.common.resources.viewmodels.ErrorViewModel
import com.training.nasa.apod.common.resources.viewmodels.ProgressViewModel
import com.training.nasa.apod.core.entities.PictureOfTheDay.Companion.EMPTY_PICTURE_OF_THE_DAY_ID
import com.training.nasa.apod.core.entities.UserPreferences
import com.training.nasa.apod.features.gallery.ui.screens.FullImageScreen
import com.training.nasa.apod.features.gallery.ui.screens.GalleryScreen
import com.training.nasa.apod.features.gallery.ui.screens.ImageDetailsScreen
import com.training.nasa.apod.features.gallery.viewmodel.FullImageViewModel
import com.training.nasa.apod.features.gallery.viewmodel.GalleryViewModel
import com.training.nasa.apod.features.gallery.viewmodel.ImageDetailsViewModel
import com.training.nasa.apod.ui.screens.LicencesScreen
import com.training.nasa.apod.ui.screens.SettingsScreen
import com.training.nasa.apod.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity(), NavController.OnDestinationChangedListener {

    @Inject
    lateinit var useCaseProvider: UseCaseProvider

    // FIXME(WIP): Check for a better approach for the viewModels to live only within their respective view.
    // Currently considering to make the required information available through Android Room + Pass the key in the URI,
    // or use a shared activity view model (This is in consideration of a future plan to allow the article view to
    // navigate to the previous/following pictureOfTheDay with out the need of returning to the calendar each time.)
    private val mainViewModel: MainViewModel by viewModels()
    private val galleryViewModel: GalleryViewModel by viewModels()
    private val imageDetailsViewModel: ImageDetailsViewModel by viewModels()
    private val fullImageDetailsViewModel: FullImageViewModel by viewModels()

    // FIXME(WIP): Check for a better approach for app-wide shared Progress/Error Views
    private var currentProgressViewModel = mutableStateOf<ProgressViewModel?>(null)
    private var currentErrorViewModel = mutableStateOf<ErrorViewModel?>(null)

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        mainViewModel.currentDestinationRoute = destination.route
        bindProgressAndErrorViews(mainViewModel.currentDestinationRoute)
    }

    private fun bindProgressAndErrorViews(currentDestinationRoute: String?) {
        val viewModel = when (currentDestinationRoute) {
            GALLERY.name -> galleryViewModel
            IMAGE_DETAILS.name -> imageDetailsViewModel
            IMAGE_VIEWER.name -> fullImageDetailsViewModel
            else -> null
        }

        setCurrentProgressViewModel(viewModel)
        setCurrentErrorViewModel(viewModel)
    }

    private fun setCurrentProgressViewModel(viewModel: ViewModel?) {
        currentProgressViewModel.value = if (viewModel is ProgressViewModel) {
            viewModel
        } else null
    }

    private fun setCurrentErrorViewModel(viewModel: ViewModel?) {
        currentErrorViewModel.value = if (viewModel is ErrorViewModel) {
            viewModel
        } else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Go Fullscreen
        WindowCompat.setDecorFitsSystemWindows(window, false)

        mainViewModel.loadUserPreferences()
        bindProgressAndErrorViews(mainViewModel.currentDestinationRoute)

        setContent {
            ProvideWindowInsets {
                NasaApodApp(
                    userPreferences = mainViewModel.userPreferences,
                    dynamicColor = mainViewModel.pictureOfTheDayDominantColor,
                    startScreenRoute = mainViewModel.startScreenRoute,
                    progressViewModel = currentProgressViewModel.value,
                    errorViewModel = currentErrorViewModel.value,
                )
            }
        }
    }

    @Composable
    fun NasaApodApp(
        userPreferences: UserPreferences,
        dynamicColor: Color,
        startScreenRoute: String,
        progressViewModel: ProgressViewModel?,
        errorViewModel: ErrorViewModel?,
    ) {
        AppUserInterface(
            selectedTheme = userPreferences.selectedTheme,
            dynamicColor = dynamicColor
        ) {

            val systemUiController = rememberSystemUiController()

            // All themes worked better with light icons, hardcoding "false".
            val useDarkIcons = false // default → MaterialTheme.colors.isLight
            val navBarColor = Color.Transparent

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = navBarColor,
                    darkIcons = useDarkIcons
                )
            }

            val navController = rememberAnimatedNavController()

            navController.addOnDestinationChangedListener(this)

            Box {
                NasaApodNavHost(
                    navController = navController,
                    userPreferences = userPreferences,
                    startScreenRoute = startScreenRoute
                )

                // Testing an approach for an app-wide progress view and error View
                progressViewModel?.isProgressViewVisible?.value?.let { isVisible ->
                    if (isVisible) {
                        LoadingFromNetworkView()
                    }
                }
                errorViewModel?.currentError?.let {
                    ErrorDialog(errorViewData = it) { errorViewModel.onErrorHandled() }
                }
            }
        }
    }

    @ExperimentalCoilApi
    @Composable
    fun NasaApodNavHost(
        navController: NavHostController,
        userPreferences: UserPreferences,
        startScreenRoute: String,
        modifier: Modifier = Modifier
    ) {

        AnimatedNavHost(
            navController = navController,
            startDestination = startScreenRoute,
            modifier = modifier
        ) {
            composable(
                GALLERY.name,
                enterTransition = Animations.enterTransition,
                exitTransition = Animations.exitTransition,
                popEnterTransition = Animations.popEnterTransition,
                popExitTransition = Animations.popExitTransition
            ) {
                GalleryScreen.SingleMonth.Screen(
                    viewModel = galleryViewModel,
                    navController = navController,
                    onViewImageDetails = { pictureOFTheDay ->
                        mainViewModel.updatePictureOfTheDay(
                            pictureOFTheDay
                        )
                    },
                    onPreviewPictureLoadSuccess = { drawable ->
                        mainViewModel.updateDynamicColor(
                            drawable
                        )
                    },
                )
            }
            composable(
                IMAGE_DETAILS.name,
                enterTransition = Animations.enterTransition,
                exitTransition = Animations.exitTransition,
                popEnterTransition = Animations.popEnterTransition,
                popExitTransition = Animations.popExitTransition
            ) {

                // FIXME: Use persisted data to only share an ID instead, or an ActivityViewModel.
                mainViewModel.pictureOfTheDay?.let {
                    LaunchedEffect(it.id) {
                        if (it.id != EMPTY_PICTURE_OF_THE_DAY_ID) {
                            imageDetailsViewModel.setSelectedPictureOfTheDay(it)
                        }
                    }
                }

                ImageDetailsScreen.Circular.Screen(
                    viewModel = imageDetailsViewModel,
                    navController = navController,
                    userPreferences = userPreferences,
                )
            }
            composable(
                IMAGE_VIEWER.name,
                enterTransition = Animations.enterTransition,
                exitTransition = Animations.exitTransition,
                popEnterTransition = Animations.popEnterTransition,
                popExitTransition = Animations.popExitTransition
            ) {

                // FIXME: Use persisted data to only share a PictureOfTheDayID instead, or an ActivityViewModel.
                FullImageScreen.Default.Screen(
                    viewModel = fullImageDetailsViewModel,
                    navController = navController,
                    userPreferences = userPreferences,
                    url = mainViewModel.pictureOfTheDay?.let { it.url } // it.hdUrl ?: it.url }
                )
            }
            composable(
                SETTINGS.name,
                enterTransition = Animations.enterTransition,
                exitTransition = Animations.exitTransition,
                popEnterTransition = Animations.popEnterTransition,
                popExitTransition = Animations.popExitTransition
            ) {
                SettingsScreen.Default.Screen(
                    navController = navController,
                    userPreferences = userPreferences,
                    onNavigateToLicenses = { navController.navigate(LICENSES.name) },
                    onThemeSelected = { theme ->
                        mainViewModel.updateUserPreferences(
                            mainViewModel.userPreferences.copy(
                                selectedTheme = theme
                            )
                        )
                    },
                    onDynamicColorSelected = { color ->
                        mainViewModel.updateDynamicColor(color)
                    },
                    onShowBackButtonOnImagesChange = { showButton ->
                        mainViewModel.updateUserPreferences(
                            mainViewModel.userPreferences.copy(
                                showBackButtonOnImages = showButton
                            )
                        )
                    },
                )
            }
            composable(
                LICENSES.name,
                enterTransition = Animations.enterTransition,
                exitTransition = Animations.exitTransition,
                popEnterTransition = Animations.popEnterTransition,
                popExitTransition = Animations.popExitTransition
            ) {
                LicencesScreen.Default.Screen(
                    navController = navController
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Preview(showSystemUi = true)
@Composable
private fun DefaultPreview() {
    AppUserInterface {
        GalleryScreen.SingleMonth.Content()
    }
}
