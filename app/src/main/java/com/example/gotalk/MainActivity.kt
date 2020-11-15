package com.example.gotalk

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.gotalk.dagger.App
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().inject(this)
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment, SplashScreen()).commit()

        Handler {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment, ChatFragment())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction().replace(R.id.fragment, SignInFragment())
                    .commit()
            }
            true
        }.sendEmptyMessageDelayed(0, 1500)
    }
}