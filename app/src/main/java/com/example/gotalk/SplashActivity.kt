package com.example.gotalk

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            true
        }.sendEmptyMessageDelayed(0, 500)
    }
}