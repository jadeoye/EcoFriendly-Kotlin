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
import com.microsoft.signalr.HubConnectionState
import io.reactivex.Completable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture

class WebNotificationService : Service() {
    private lateinit var hubConnection: HubConnection

    override fun onCreate() {
        super.onCreate()
        hubConnection = HubConnectionBuilder.create(signalr_web_url).build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intents = intent?.getStringExtra(signalr_notification_label) ?: "Unknown|0"
        val intentParts = intents.split('#')
        send(intentParts)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        hubConnection.stop()
        hubConnection.close()
        super.onDestroy()
    }

    private suspend fun awaitConnectionEstablished() {
        while (hubConnection.connectionState != HubConnectionState.CONNECTED) {
            delay(100)
        }
    }

    private fun send(intents: List<String>) {

        CoroutineScope(Dispatchers.IO).launch {
            if (hubConnection.connectionState == HubConnectionState.DISCONNECTED) {
                hubConnection.start()
                awaitConnectionEstablished()
            }

            intents.forEach {
                intent ->
                val messageParts = intent.split('|')
                val notificationType = messageParts[0]
                val value = messageParts[1].toDouble()

                hubConnection.invoke("Analytics", notificationType, value).subscribe({ stopSelf() },
                    { Log.e("WebNotificationService", "Error sending message") })
            }


        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}
