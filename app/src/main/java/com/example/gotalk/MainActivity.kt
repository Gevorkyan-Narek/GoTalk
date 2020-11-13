package com.example.gotalk

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.gotalk.storage.Storage
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(R.layout.activity_main) {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            FirebaseApp.initializeApp(this)
            auth = FirebaseAuth.getInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment, SplashScreen()).commit()

        Handler {

            val currentUser = auth.currentUser
            if (currentUser != null) {
                Storage.setPerson(currentUser.displayName, currentUser.photoUrl)
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