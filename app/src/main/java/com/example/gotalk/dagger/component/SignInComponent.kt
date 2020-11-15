package com.example.gotalk.dagger.component

import com.example.gotalk.ChatFragment
import com.example.gotalk.DialogFragment
import com.example.gotalk.MainActivity
import com.example.gotalk.SignInFragment
import com.example.gotalk.dagger.modules.FirebaseModule
import com.example.gotalk.dagger.modules.GoogleSignInModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [GoogleSignInModule::class, FirebaseModule::class])
@Singleton
interface SignInComponent {

    fun inject(signInFragment: SignInFragment)
    fun inject(chatFragment: ChatFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(dialogFragment: DialogFragment)
}