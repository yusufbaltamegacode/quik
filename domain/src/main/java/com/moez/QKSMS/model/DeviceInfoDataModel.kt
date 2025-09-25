package com.moez.QKSMS.model

data class DeviceInfoDataModel(

    var androidId: String?,
    var manufacturer: String?,
    var model: String?,
    var brand: String?,
    var sdkInt: Int?,
    var release: String?,
    var cpuAbi: String?,
    var hardware: String?,
    var board: String?,
    var locale: String?,
    var timeZone: String?,
    var batteryLevel: Int?,

    var ipAddress: String? ,
    var networkType: String? ,
    var ssid: String? ,
)
