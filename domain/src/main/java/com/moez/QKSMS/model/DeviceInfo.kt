package com.moez.QKSMS.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Locale
import java.util.TimeZone

object DeviceInfo {
    var androidId: String? = null
    var manufacturer: String? = null
    var model: String? = null
    var brand: String? = null
    var sdkInt: Int? = null
    var release: String? = null
    var cpuAbi: String? = null
    var hardware: String? = null
    var board: String? = null
    var locale: String? = null
    var timeZone: String? = null
    var batteryLevel: Int? = null

    // Network bilgileri
    var ipAddress: String? = null
    var networkType: String? = null
    var ssid: String? = null

    fun init(context: Context) {
        androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        manufacturer = Build.MANUFACTURER
        model = Build.MODEL
        brand = Build.BRAND
        sdkInt = Build.VERSION.SDK_INT
        release = Build.VERSION.RELEASE
        cpuAbi = Build.SUPPORTED_ABIS.joinToString()
        hardware = Build.HARDWARE
        board = Build.BOARD
        locale = Locale.getDefault().toString()
        timeZone = TimeZone.getDefault().id

        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        batteryLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)


        // Network bilgileri
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        val capabilities = cm.getNetworkCapabilities(network)

        networkType = when {
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> "WiFi"
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "Cellular"
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true -> "VPN"
            else -> "Unknown"
        }

        ipAddress = getLocalIpAddress()

        if (networkType == "WiFi") {
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            ssid = wifiManager.connectionInfo.ssid?.replace("\"", "")
        }


        deviceInfo = DeviceInfoDataModel(androidId, manufacturer, model, brand, sdkInt, release, cpuAbi, hardware, board, locale, timeZone, batteryLevel, ipAddress,
            networkType,
            ssid)
    }

     lateinit var deviceInfo : DeviceInfoDataModel




    private fun getLocalIpAddress(): String? {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (intf in interfaces) {
                val addrs = intf.inetAddresses
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress && addr is InetAddress) {
                        return addr.hostAddress
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

}
