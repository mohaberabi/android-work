package com.mohaberabi.androidworkmanager.data.sync

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.mohaberabi.androidworkmanager.R
import com.mohaberabi.androidworkmanager.domain.model.AppNotificationChannel
import com.mohaberabi.androidworkmanager.domain.sync.SyncNotifier
import kotlin.random.Random

class AndroidNotificationManagerSyncNotifier(
    private val context: Context,
) : SyncNotifier {


    private val notificationManager by lazy {
        context.getSystemService<NotificationManager>()!!
    }

    override fun show(
        title: String,
        body: String
    ) {
        if (canShow()) {
            val id = Random.nextInt()
            val notification =
                NotificationCompat.Builder(context, AppNotificationChannel.Default.id)
                    .setContentText(body)
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .build()
            notificationManager.notify(id, notification)
        }

    }

    override fun canShow(): Boolean {
        return if (Build.VERSION.SDK_INT >= 33) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    override fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= 33) {
            val channels = AppNotificationChannel.entries.map {
                NotificationChannel(it.id, it.name, it.importance)
            }
            notificationManager.createNotificationChannels(channels)
        }
    }
}