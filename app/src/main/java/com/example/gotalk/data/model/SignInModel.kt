package com.example.gotalk.data.model

import android.content.Intent
import android.util.Log
import com.example.gotalk.data.entities.User
import com.example.gotalk.data.storage.SignInInterface
import com.google.firebase.auth.GoogleAuthProvider
import java.util.concurrent.Executors

class SignInModel : RepoModel() {

    override fun setReferences(vararg params: String) {
        usersReferenceDB = reference.child("users")
    }

    fun signInGoogle(): Intent = signGoogle.signInIntent

    fun firebaseAuthWithGoogle(idToken: String, signInInterface: SignInInterface) {
        GoogleAuthProvider.getCredential(idToken, null).apply {
            auth.signInWithCredential(this)
                .addOnCompleteListener(Executors.newSingleThreadExecutor()) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser!!
                        usersReferenceDB.child(user.uid).setValue(
                            User(
                                user.uid,
                                user.displayName,
                                user.email,
                                user.photoUrl.toString()
                            )
                        )
                        signInInterface.signUpProfile()
                    } else {
                        Log.w("AuthWithGoogle", "signInWithCredential:failure", task.exception)
                    }
                }
        }
    }
}