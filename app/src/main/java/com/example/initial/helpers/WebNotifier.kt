package com.example.initial.helpers

import android.content.Context
import android.content.Intent
import com.example.initial.services.WebNotificationService

fun sendNotificationToWeb(context: Context, data: String) {
   try {
       val intent = Intent(context, WebNotificationService::class.java).apply {
           putExtra(signalr_notification_label, data)
       }
       context.startService(intent)
   }
   catch (ex: Exception) {
       val e = ex
   }
}