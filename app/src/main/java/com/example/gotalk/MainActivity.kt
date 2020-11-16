package com.example.gotalk

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.gotalk.dagger.App
import com.example.gotalk.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.threetenabp.AndroidThreeTen
import javax.inject.Inject


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().inject(this)
        AndroidThreeTen.init(this)
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment, SplashScreen()).commit()

        Handler {
            if (auth.currentUser != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment, ChatFragment())
                    .commit()
//                auth.currentUser!!.delete()
//                finish()
            } else {
                supportFragmentManager.beginTransaction().replace(R.id.fragment, SignInFragment())
                    .commit()
            }
            true
        }.sendEmptyMessageDelayed(0, 1500)
    }
}