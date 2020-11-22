package com.example.gotalk.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.gotalk.R
import com.example.gotalk.data.storage.FragmentNavigator
import com.example.gotalk.databinding.ActivityMainBinding
import com.example.gotalk.viewmodel.MainViewModel
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : AppCompatActivity(R.layout.activity_main), FragmentNavigator {

    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(MainViewModel::class.java)
        AndroidThreeTen.init(this)
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment, SplashScreen())
            .commit()

        Handler {
            if (viewModel.checkUser()) {
                Log.d("MainActivity", viewModel.repoModel.user.displayName ?: "No name")
                navigateTo()
//                auth.currentUser!!.delete()
//                finish()
            } else {
                supportFragmentManager.beginTransaction().replace(R.id.fragment, SignInFragment())
                    .commit()
            }
            true
        }.sendEmptyMessageDelayed(0, 1500)
    }

    override fun navigateTo(vararg params: String) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, ChatFragment())
            .commit()
    }

    override fun navigateToBack() {

    }
}