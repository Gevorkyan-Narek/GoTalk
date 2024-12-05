package com.example.gotalk.dagger

import android.app.Application
import com.example.gotalk.dagger.component.DaggerSignInComponent
import com.example.gotalk.dagger.component.SignInComponent
import com.example.gotalk.dagger.modules.GoogleSignInModule

class App: Application() {

    companion object {
        private lateinit var signInComponent: SignInComponent
        fun getComponent() = signInComponent
    }

    override fun onCreate() {
        super.onCreate()
        signInComponent = buildComponent()
    }

    private fun buildComponent(): SignInComponent {
        return DaggerSignInComponent.builder()
            .googleSignInModule(GoogleSignInModule(applicationContext))
            .build()
    }
}