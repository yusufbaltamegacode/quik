package com.moez.QKSMS.manager

import com.moez.QKSMS.service.SmsApiService
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object NetworkModule {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://ekranyayin-api.acente365.com/api/") // kendi server URL’in
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val smsApiService: SmsApiService = retrofit.create(SmsApiService::class.java)
}