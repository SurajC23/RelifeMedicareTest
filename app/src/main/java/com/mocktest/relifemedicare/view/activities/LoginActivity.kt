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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class LoginActivity : AppCompatActivity() {

    lateinit var loginRegistrationViewModel: LoginRegistrationViewModel
    lateinit var firebaseUser: FirebaseUser
    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)

        setTimeOfTheDay()

        btnLogin.setOnClickListener(View.OnClickListener {
            val email: String = etEmail?.text?.trim().toString()
            val password: String = etPassword?.text?.trim().toString()

            if (email.isEmpty())
            {
                etEmail.error = "Required"
                return@OnClickListener
            }

            if(password.isEmpty())
            {
                etPassword.error = "Required"
                return@OnClickListener
            }

            pBar.visibility = View.VISIBLE

            val repository = FirebaseAuthRepository(application)
            loginRegistrationViewModel = ViewModelProvider(this, FirebaseViewModelFactory(repository)).get(LoginRegistrationViewModel::class.java)

            GlobalScope.launch {
                loginRegistrationViewModel.login(email, password)
            }

            loginRegistrationViewModel.getUserLiveData().observe(this, Observer {
                firebaseUser = it
                if (firebaseUser != null)
                {
                    pBar.visibility = View.GONE
                    val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                    editor.putString("email",email)
                    editor.apply()
                    editor.commit()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
        })

        btnRegistration.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        })
    }

    private fun setTimeOfTheDay() {
        var calendar: Calendar = Calendar.getInstance()
        val timeOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12)
        {
            tvTiming.text = "MORNING"
        }
        else if (timeOfDay >= 12 && timeOfDay < 16)
        {
            tvTiming.text = "AFTERNOON"
        }
        else if (timeOfDay >= 16 && timeOfDay < 24)
        {
            tvTiming.text = "EVENING"
        }
    }
}