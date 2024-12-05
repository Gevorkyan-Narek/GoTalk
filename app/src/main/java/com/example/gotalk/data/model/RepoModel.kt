package com.example.gotalk.data.model

import androidx.lifecycle.MutableLiveData
import com.example.gotalk.data.dagger.App
import com.example.gotalk.viewmodel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

abstract class RepoModel {

    @Inject
    lateinit var reference: DatabaseReference

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var signGoogle: GoogleSignInClient

    @Inject
    lateinit var user: FirebaseUser

    val state: MutableLiveData<Int> = MutableLiveData(MainViewModel.SPLASH_SCREEN)

    lateinit var usersReferenceDB: DatabaseReference

    lateinit var messageReferenceDB: DatabaseReference

    fun init() {
        App.getComponent().inject(this)
    }

    abstract fun setReferences(vararg params: String)
}