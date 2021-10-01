package com.mocktest.relifemedicare.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mocktest.relifemedicare.models.MedicareModel
import com.mocktest.relifemedicare.repository.MedicareRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicareViewModel(private val repository: MedicareRepository): ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.getMedicare(10, "medicare")
        }
    }

    val medicare: LiveData<MedicareModel>
    get() = repository.medicare
}