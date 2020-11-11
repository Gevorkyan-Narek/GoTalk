package com.example.gotalk

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment, SplashScreen()).commit()

        Handler {
            supportFragmentManager.beginTransaction().replace(R.id.fragment, SignInFragment())
                .commit()
            true
        }.sendEmptyMessageDelayed(0, 1500)
    }
}