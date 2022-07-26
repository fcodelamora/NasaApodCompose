package com.training.nasa.apod.common.resources

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import androidx.compose.ui.geometry.Offset
import kotlin.math.abs
import timber.log.Timber
import timber.log.debug

object ResetImageValueAnimator {

    fun getValueAnimator(scale: Float, offset: Offset): ValueAnimator = ValueAnimator.ofObject(
        object : TypeEvaluator<FloatArray> {
            override fun evaluate(
                fraction: Float,
                startValue: FloatArray?,
                endValue: FloatArray?
            ): FloatArray {

                val forcedEndValue = floatArrayOf(1f, 0f, 0f)

                startValue?.let {

                    val scaleUpdateDirection =
                        if (startValue[0] > forcedEndValue[0])
                            -1 // Reduce
                        else
                            1 // Increase

                    val scaleValuesDistance =
                        abs(forcedEndValue[0] - abs(startValue[0])) * scaleUpdateDirection

                    val invertedFraction = 1 - fraction

                    return floatArrayOf(
                        (startValue[0] + (scaleValuesDistance * fraction)),
                        (startValue[1] * invertedFraction),
                        (startValue[2] * invertedFraction)
                    ).also {
                        Timber.debug {
                            "fraction: $fraction, " +
                                "startValue: ${startValue.toList()}, " +
                                "endValue: ${it.toList()}"
                        }
                    }
                } ?: return forcedEndValue
            }
        },
        floatArrayOf(scale, offset.x, offset.y),
        floatArrayOf(1f, 0f, 0f)
    )
}
