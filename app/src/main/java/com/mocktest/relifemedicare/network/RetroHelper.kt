package com.demo.myfirstapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroHelper {
    private const val baseURL = "https://spaceflightnewsapi.net/api/v2/"
    fun getInsance(): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}