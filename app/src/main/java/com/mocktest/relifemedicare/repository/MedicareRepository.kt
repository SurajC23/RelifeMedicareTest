package com.mocktest.relifemedicare.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.myfirstapp.network.RetroService
import com.mocktest.relifemedicare.models.MedicareModel

class MedicareRepository(private val retroService: RetroService) {

    private val medicareLiveData = MutableLiveData<MedicareModel>()
    val medicare: LiveData<MedicareModel>
    get() = medicareLiveData

    suspend fun getMedicare(limit: Int, sort: String)
    {
        val result = retroService.getDataFromAPI(limit, sort)
        if (result?.body() != null)
        {
            medicareLiveData.postValue(result.body())
        }
    }
}