package com.moez.QKSMS.service

import com.moez.QKSMS.model.SmsDataRequest

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SmsApiService {
    @POST("incoming-sms/create")
    suspend  fun sendSms(@Body smsData: SmsDataRequest): Response<Void>
}