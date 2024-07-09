package com.example.initial.services;

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import coil.request.Disposable
import com.example.initial.helpers.signalr_notification_label
import com.example.initial.helpers.signalr_web_url
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import io.reactivex.Completable
import java.util.concurrent.CompletableFuture

class WebNotificationService : Service() {
    private lateinit var hubConnection: HubConnection

    override fun onCreate() {
        super.onCreate()
        hubConnection = HubConnectionBuilder.create(signalr_web_url).build()
        hubConnection.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val message = intent?.getStringExtra(signalr_notification_label) ?: ""
        send(message)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        hubConnection.stop()
        hubConnection.close()
        super.onDestroy()
    }

    private fun send(message: String) {
        hubConnection.invoke("analytics", message)
            .subscribe({ stopSelf() }, { Log.e("WebNotificationService", "Error sending message") })
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}
