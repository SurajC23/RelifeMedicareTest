package com.mocktest.relifemedicare.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mocktest.relifemedicare.R

class SplashActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        val sharedEmailValue = sharedPreferences.getString("email","")
        val verify = sharedPreferences.getBoolean("verify", false)

        Handler().postDelayed({
            if (!sharedEmailValue.equals(""))
            {
                if (verify)
                {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    val intent = Intent(this, OtpVerificationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            else
            {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}