package com.example.gotalk

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.fragment, SplashScreen()).commit()

        Handler {
            supportFragmentManager.beginTransaction().replace(R.id.fragment, SignInFragment())
                .commit()
            true
        }.sendEmptyMessageDelayed(0, 1500)
    }
}