package com.example.gotalk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gotalk.data.model.RepoModel

abstract class BaseViewModel: ViewModel() {

    abstract val repoModel: RepoModel

    abstract fun onCreate()
    abstract fun onStart()
    abstract fun onResume()
    abstract fun onBackPressed()
}

