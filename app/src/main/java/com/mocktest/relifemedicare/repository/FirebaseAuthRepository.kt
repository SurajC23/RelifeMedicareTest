package com.mocktest.relifemedicare.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.mocktest.relifemedicare.models.UserModel

class FirebaseAuthRepository(private val application: Application) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userLiveData = MutableLiveData<FirebaseUser>()
    var boolean: Boolean = false

    suspend fun login(email: String, password: String)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful)
                {
                    userLiveData.postValue(firebaseAuth.currentUser)
                }
                else
                {
                    val toast = Toast.makeText(application, "Login Failed", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
    }

    suspend fun registration(firstName: String, lastName:String, contactNo: String, email: String, password: String)
    {
        firebaseAuth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener(OnCompleteListener {
                boolean = it.result?.signInMethods?.isEmpty() == true
                if (boolean)
                {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener{
                            if (it.isSuccessful)
                            {
                                val userModel: UserModel = UserModel(firstName, lastName, contactNo)
                                val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
                                val refrence = firebaseDatabase.getReference("Users")
                                refrence.child(FirebaseAuth.getInstance().currentUser?.uid!!)
                                    .setValue(userModel).addOnCompleteListener(OnCompleteListener {
                                        if (it.isSuccessful)
                                        {
                                            userLiveData.postValue(firebaseAuth.currentUser)
                                        }
                                    })
                            }
                            else
                            {
                                val toast = Toast.makeText(application, "Registration Failed", Toast.LENGTH_SHORT)
                                toast.show()
                            }
                        }
                }
                else
                {
                    val toast = Toast.makeText(application, "User already Presents", Toast.LENGTH_SHORT)
                    toast.show()

                }
            })
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser>
    {
        return userLiveData
    }
}