package com.example.aiart.interfaces

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface DezgoApiService {
    @Headers(
        "X-RapidAPI-Key: API-KEY",
        "X-RapidAPI-Host: dezgo.p.rapidapi.com"
    )
    @POST("text2image")
    @FormUrlEncoded
    fun generateImage(
        @Field("prompt") prompt: String,
        @Field("guidance") guidance: Int,
        @Field("steps") steps: Int,
        @Field("sampler") sampler: String,
        @Field("upscale") upscale: Int,
        @Field("model") model: String
    ): Call<ResponseBody>
}
