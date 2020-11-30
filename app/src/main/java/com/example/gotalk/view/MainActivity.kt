package com.example.gotalk.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.gotalk.R
import com.example.gotalk.data.storage.Navigator
import com.example.gotalk.databinding.ActivityMainBinding
import com.example.gotalk.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main), Navigator {

    val viewModel = MainViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
    }

    override fun onStart() {
        super.onStart()
        viewModel.repoModel.state.observe(this, {
            viewModel.changeFragment(it)
        })
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getInt("state").let {
            viewModel.repoModel.state.value = it
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.repoModel.state.value?.let {
            outState.putInt("state", it)
        }
    }

    override fun toSplashScreen() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, SplashScreen()).commit()
    }

    override fun toChat() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, ChatFragment()).commit()
    }

    override fun toSign() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, SignInFragment()).commit()
    }
}