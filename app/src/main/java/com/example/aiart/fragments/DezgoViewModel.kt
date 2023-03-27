package com.example.aiart.fragments

import androidx.lifecycle.ViewModel
import com.example.aiart.room.ImageDatabase
import com.example.aiart.interfaces.RetrofitService.apiService
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import retrofit2.Call

class DezgoViewModel : ViewModel() {
    fun generateImage(prompt: String): Flow<Call<ResponseBody>> = flow {
        val response = apiService.generateImage(
            prompt = prompt,
            guidance = 7,
            steps = 30,
            sampler = "euler_a",
            upscale = 1,
            model = "epic_diffusion_1_1"

        )
        emit(response)
    }

}
