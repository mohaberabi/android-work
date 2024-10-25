package com.mohaberabi.androidworkmanager.domain.model

import android.app.NotificationManager

enum class AppNotificationChannel(
    val label: String,
    val id: String,
    val importance: Int
) {
    Default(
        "Default",
        "default",
        NotificationManager.IMPORTANCE_DEFAULT
    )

}