package com.example.initial.helpers

import android.content.Context
import android.content.Intent
import com.example.initial.services.WebNotificationService

fun sendNotificationToWeb(context: Context, title: String, data: String) {
    val message = "${title}---${data}"
    val intent = Intent(context, WebNotificationService::class.java).apply {
        putExtra(signalr_notification_label, message)
    }
    context.startService(intent)
}