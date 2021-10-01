package com.mocktest.relifemedicare.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mocktest.relifemedicare.R
import kotlinx.android.synthetic.main.activity_detailed.*

class DetailedActivity : AppCompatActivity() {

    var webUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        webUrl = intent.getStringExtra("web_url")!!
        webView.loadUrl(webUrl)
    }
}