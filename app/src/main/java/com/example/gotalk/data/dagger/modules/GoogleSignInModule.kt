package com.example.gotalk.data.dagger.modules

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GoogleSignInModule(private val appContext: Context) {

    companion object {
        private const val SERVER_CLIENT_ID =
            "919234681341-vlil986dkb6nh8rn8knp3ve6v1f6v7q8.apps.googleusercontent.com"
    }

    @Provides
    @Singleton
    fun googleSignInOptions(): GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(SERVER_CLIENT_ID)
            .requestEmail()
            .build()

    @Provides
    @Singleton
    fun googleSignInClient(googleSignInOptions: GoogleSignInOptions)
            : GoogleSignInClient = GoogleSignIn.getClient(appContext, googleSignInOptions)
}