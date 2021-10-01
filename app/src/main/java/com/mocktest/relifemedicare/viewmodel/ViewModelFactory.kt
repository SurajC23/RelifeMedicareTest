package com.mocktest.relifemedicare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mocktest.relifemedicare.repository.MedicareRepository

class ViewModelFactory(private val repository: MedicareRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MedicareViewModel(repository) as T
    }
}