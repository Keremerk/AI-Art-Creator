package com.example.aiart.interfaces

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dezgo.p.rapidapi.com/")
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: DezgoApiService = retrofit.create(DezgoApiService::class.java)
}