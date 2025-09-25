package com.moez.QKSMS.model

data class SendPingRequest(
    val pingTime : String,
    val deviceInfo :DeviceInfoDataModel?,
)
