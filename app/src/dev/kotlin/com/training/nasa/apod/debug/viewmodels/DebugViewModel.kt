package com.training.nasa.apod.debug.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.training.nasa.apod.common.resources.Event
import com.training.nasa.apod.provide.mocks.api.debugflags.ApiErrorType
import com.training.nasa.apod.provide.mocks.api.debugflags.MockDebugFlags
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    companion object {
        const val DEFAULT_DELAY_MILLIS = "250"
        const val DEFAULT_API_ERROR_TYPE_INDEX = 0
    }

    @Inject
    lateinit var mockDebugFlags: MockDebugFlags

    val availableErrorList = ApiErrorType.values().map { it.displayName }

    val delay = mutableStateOf(DEFAULT_DELAY_MILLIS)

    val apiErrorTypeSelectionIndex: MutableState<Int> = mutableStateOf(DEFAULT_API_ERROR_TYPE_INDEX)

    private val _closeDebugView: MutableLiveData<Event<Unit>?> = MutableLiveData(null)
    val closeDebugView: LiveData<Event<Unit>?> = _closeDebugView

    fun updateDelayInMillis(millisString: String) {
        delay.value = millisString
    }

    fun updateFlags() {
        mockDebugFlags.apply {
            delayInMillis = try {
                delay.value.toLong()
            } catch (e: Exception) {
                Toast.makeText(
                    getApplication(),
                    "Invalid delay time, reset to ${DEFAULT_DELAY_MILLIS}ms",
                    Toast.LENGTH_SHORT
                ).show()
                DEFAULT_DELAY_MILLIS.toLong()
            }
            apiErrorType = ApiErrorType.fromIndex(apiErrorTypeSelectionIndex.value)
        }

        Timber.d("New Flags: $mockDebugFlags")

        _closeDebugView.value = Event(Unit)
    }
}
