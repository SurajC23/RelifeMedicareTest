package com.demo.myfirstapp.network

import com.mocktest.relifemedicare.models.MedicareModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {
    @GET("articles")
    suspend fun getDataFromAPI(@Query("_limit") limit: Int, @Query("_sort") sort: String): Response<MedicareModel>
}