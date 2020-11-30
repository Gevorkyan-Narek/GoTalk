package com.example.gotalk.data.dagger

import android.app.Application
import com.example.gotalk.data.dagger.component.DaggerSignInComponent
import com.example.gotalk.data.dagger.component.SignInComponent
import com.example.gotalk.data.dagger.modules.GoogleSignInModule
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen

class App : Application() {

    companion object {
        private lateinit var signInComponent: SignInComponent
        fun getComponent() = signInComponent
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        signInComponent = buildComponent()
    }

    private fun buildComponent(): SignInComponent {
        return DaggerSignInComponent.builder()
            .googleSignInModule(GoogleSignInModule(applicationContext))
            .build()
    }
}