package com.example.gotalk.viewmodel

import com.example.gotalk.data.model.ActivityModel

class MainViewModel : BaseViewModel() {

    override val repoModel = ActivityModel()

    override fun onCreate() {
        repoModel.init()
    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onBackPressed() {

    }

    fun checkUser(): Boolean = repoModel.auth.currentUser != null
}