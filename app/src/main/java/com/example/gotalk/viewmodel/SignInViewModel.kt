package com.example.gotalk.viewmodel

import com.example.gotalk.data.storage.SignInInterface
import com.example.gotalk.data.model.SignInModel

class SignInViewModel(private val signInInterface: SignInInterface) : BaseViewModel() {

    override val repoModel: SignInModel = SignInModel()

    override fun onCreate() {
        repoModel.init()
        repoModel.setReferences()
    }

    override fun onResume() {

    }

    override fun onBackPressed() {

    }

    override fun onStart() {

    }

    fun signInGoogle() {
        signInInterface.signInGoogle(repoModel.signInGoogle())
    }

    fun signUpProfile(idToken: String) {
        repoModel.firebaseAuthWithGoogle(idToken, signInInterface)
    }
}