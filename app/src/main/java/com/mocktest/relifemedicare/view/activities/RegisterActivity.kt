package com.mocktest.relifemedicare.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import com.mocktest.relifemedicare.R
import com.mocktest.relifemedicare.repository.FirebaseAuthRepository
import com.mocktest.relifemedicare.viewmodel.FirebaseViewModelFactory
import com.mocktest.relifemedicare.viewmodel.LoginRegistrationViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class RegisterActivity : AppCompatActivity() {

    lateinit var loginRegistrationViewModel: LoginRegistrationViewModel
    lateinit var firebaseUser: FirebaseUser
    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        setTimeOfTheDay()

        btnRegistrationDo.setOnClickListener(View.OnClickListener {
            val firstname: String = etRegsFirstName?.text?.trim().toString()
            val lastName: String = etRegsLastName?.text?.trim().toString()
            val contactNo: String = etRegsContactNo?.text?.trim().toString()
            val email: String = etRegsEmail?.text?.trim().toString()
            val password: String = etRegsPassword?.text?.trim().toString()

            if (firstname.isEmpty())
            {
                etRegsEmail.error = "Required"
                return@OnClickListener
            }

            if (lastName.isEmpty())
            {
                etRegsLastName.error = "Required"
                return@OnClickListener
            }

            if (contactNo.isEmpty())
            {
                etRegsContactNo.error = "Required"
                return@OnClickListener
            }

            if(contactNo.length < 10)
            {
                etRegsContactNo.error = "Enter valid number"
                return@OnClickListener
            }

            if (email.isEmpty())
            {
                etRegsEmail.error = "Required"
                return@OnClickListener
            }

            if (password.isEmpty())
            {
                etRegsPassword.error = "Required"
                return@OnClickListener
            }

            pRegBar.visibility = View.VISIBLE

            val repository = FirebaseAuthRepository(application)
            loginRegistrationViewModel = ViewModelProvider(this, FirebaseViewModelFactory(repository)).get(LoginRegistrationViewModel::class.java)

            GlobalScope.launch {
                loginRegistrationViewModel.register(firstname, lastName, contactNo, email, password)
            }

            loginRegistrationViewModel.getUserLiveData().observe(this, Observer {
                firebaseUser = it
                if (firebaseUser != null)
                {
                    pRegBar.visibility = View.GONE
                    val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                    editor.putString("email",email)
                    editor.apply()
                    editor.commit()
                    val intent = Intent(this, OtpVerificationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
        })

        btnRegsLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    private fun setTimeOfTheDay() {
        var calendar: Calendar = Calendar.getInstance()
        val timeOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12)
        {
            tvRegTiming.text = "MORNING"
        }
        else if (timeOfDay >= 12 && timeOfDay < 16)
        {
            tvRegTiming.text = "AFTERNOON"
        }
        else if (timeOfDay >= 16 && timeOfDay < 24)
        {
            tvRegTiming.text = "EVENING"
        }
    }
}