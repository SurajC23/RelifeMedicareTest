package com.mocktest.relifemedicare.view.activities

import `in`.aabhasjindal.otptextview.OTPListener
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mocktest.relifemedicare.R
import kotlinx.android.synthetic.main.activity_otp_verification.*

class OtpVerificationActivity : AppCompatActivity() {

    var boolean: Boolean = false
    val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)

        otp_view?.requestFocusOTP()
        otp_view?.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                if (otp.length == 6)
                {
                    callMainActivity()
                }
            }
        }
    }

    private fun callMainActivity() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putBoolean("verify",true)
        editor.apply()
        editor.commit()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}