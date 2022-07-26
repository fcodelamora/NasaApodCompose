package com.training.nasa.apod

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.StrictMode
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.training.nasa.apod.common.resources.ui.AppDrawable
import com.training.nasa.apod.debug.DebugActivity
import timber.log.Timber
import timber.log.debug

class DebugNasaApodApplication : NasaApodApplication() {

    override fun onCreate() {
        // StrictMode only active in "dev"
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
        super.onCreate()

        createDebugNotificationChannel()
        showDebugSettingsNotification()
    }

    private fun createDebugNotificationChannel() {

        val channelDebug = NotificationChannel(
            CHANNEL_ID,
            "Debug",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Provides access to debug screen"
            setSound(null, null)
        }

        // Register channel with the system
        val notificationManager: NotificationManagerCompat =
            NotificationManagerCompat.from(applicationContext)

        if (channelDebug !in notificationManager.notificationChannels) {
            notificationManager.createNotificationChannel(channelDebug).also {
                Timber.debug { "Created Notification Channel: ${channelDebug.name}" }
            }
        }
    }

    private fun showDebugSettingsNotification() {
        val notifyIntent = Intent(this, DebugActivity::class.java)
        val notifyPendingIntent = PendingIntent.getActivity(
            this,
            0,
            notifyIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(AppDrawable.ic_debug_notification)
            .setContentTitle("Debug options")
            .setContentText("Open debug screen")
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(notifyPendingIntent)
            .setAutoCancel(false)
            .build()
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val CHANNEL_ID = "debug_options_channel"
        const val NOTIFICATION_ID = 9999
    }
}
