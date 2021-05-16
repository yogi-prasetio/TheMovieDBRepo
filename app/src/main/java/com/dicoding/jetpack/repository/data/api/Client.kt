package com.dicoding.jetpack.repository.data.api

import com.dicoding.jetpack.repository.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    val client = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiInstance = client.create(Api::class.java)
}