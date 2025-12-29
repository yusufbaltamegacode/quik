package com.moez.QKSMS.manager


import com.moez.QKSMS.service.SmsApiService
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object NetworkModule {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val headerInterceptor = Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoic3RhdGlrIHRva2VuIn0.Tz-yDyqWhvc0HFOzcnnZzBcEaMGYnde9NXB51omqi00") // Örnek: Token
            .header("X-Device-Source", "QKSMS-App") // Örnek: Özel bir header
            .method(original.method, original.body)
            .build()

        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()

        .addInterceptor(logging)
        .addInterceptor(headerInterceptor)
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