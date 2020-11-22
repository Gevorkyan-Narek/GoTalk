package com.example.gotalk.data.dagger.component

import com.example.gotalk.data.dagger.modules.FirebaseModule
import com.example.gotalk.data.dagger.modules.GoogleSignInModule
import com.example.gotalk.data.model.DialogModel
import com.example.gotalk.data.model.RepoModel
import com.example.gotalk.data.model.SignInModel
import com.example.gotalk.view.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [GoogleSignInModule::class, FirebaseModule::class])
@Singleton
interface SignInComponent {

    fun inject(signInFragment: SignInFragment)
    fun inject(chatFragment: ChatFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(signUpProfile: SignUpProfile)
    fun inject(repoModel: RepoModel)
}