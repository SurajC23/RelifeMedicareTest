package com.mocktest.relifemedicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.mocktest.relifemedicare.repository.FirebaseAuthRepository

class LoginRegistrationViewModel(private val repository: FirebaseAuthRepository): ViewModel() {

    suspend fun login(email: String, password: String)
    {
        repository.login(email, password)
    }

    suspend fun register(firstName: String, lastName:String, contactNo: String, email: String, password: String)
    {
        repository.registration(firstName, lastName, contactNo, email, password)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser>
    {
        return repository.getUserLiveData()
    }
}