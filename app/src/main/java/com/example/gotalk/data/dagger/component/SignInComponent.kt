package com.example.gotalk.data.dagger.component

import com.example.gotalk.data.dagger.modules.FirebaseModule
import com.example.gotalk.data.dagger.modules.GoogleSignInModule
import com.example.gotalk.data.model.ActivityModel
import com.example.gotalk.data.model.RepoModel
import com.example.gotalk.view.SignUpProfile
import dagger.Component
import javax.inject.Singleton

@Component(modules = [GoogleSignInModule::class, FirebaseModule::class])
@Singleton
interface SignInComponent {

    fun inject(signUpProfile: SignUpProfile)
    fun inject(repoModel: RepoModel)
    fun inject(activityModel: ActivityModel)
}