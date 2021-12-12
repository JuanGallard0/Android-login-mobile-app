package com.pdm.ndv.network

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.conscrypt.Conscrypt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.Security
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://ndvapi.herokuapp.com/api/"

private val interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val client = OkHttpClient.Builder()
    .readTimeout(6, TimeUnit.MINUTES)
    .connectTimeout(6, TimeUnit.MINUTES)
    .addInterceptor(interceptor).build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

object NDVAPI {
    val service by lazy {
        retrofit.create(NDVService::class.java)
    }
}