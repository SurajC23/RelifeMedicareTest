package com.mocktest.relifemedicare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mocktest.relifemedicare.repository.FirebaseAuthRepository
import com.mocktest.relifemedicare.repository.MedicareRepository

class FirebaseViewModelFactory(private val repository: FirebaseAuthRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginRegistrationViewModel(repository) as T
    }
}