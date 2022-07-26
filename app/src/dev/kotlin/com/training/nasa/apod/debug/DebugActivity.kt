package com.training.nasa.apod.debug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.training.nasa.apod.common.resources.ui.AppUserInterface
import com.training.nasa.apod.debug.ui.screens.DebugScreen
import com.training.nasa.apod.debug.viewmodels.DebugViewModel
import com.training.nasa.apod.provide.mocks.api.debugflags.MockDebugFlags
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DebugActivity : ComponentActivity() {

    private val debugViewModel: DebugViewModel by viewModels()

    @Inject
    lateinit var mockDebugFlags: MockDebugFlags

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppUserInterface {
                DebugScreen.Default.Screen(
                    debugViewModel
                )
            }
        }

        debugViewModel.closeDebugView.observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                closeAsModal()
            }
        }

        loadCurrentFlags()
    }

    private fun loadCurrentFlags() {
        debugViewModel.run {
            delay.value = mockDebugFlags.delayInMillis.toString()
            apiErrorTypeSelectionIndex.value = mockDebugFlags.apiErrorType.toIndex()
        }
    }

    private fun closeAsModal() {
        finish()
        overridePendingTransition(
            com.training.nasa.apod.R.anim.stay_in_place,
            com.training.nasa.apod.R.anim.slide_out_to_bottom,
        )
    }
}
