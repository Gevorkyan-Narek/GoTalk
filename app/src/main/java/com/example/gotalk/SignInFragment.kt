package com.example.gotalk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gotalk.dagger.App
import com.example.gotalk.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.sign_in.*
import javax.inject.Inject
import javax.inject.Named


class SignInFragment : Fragment(R.layout.sign_in) {

    companion object {
        private const val GOOGLE_REQ_CODE = 1
    }

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var signGoogle: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInGoogle.setOnClickListener {
            val intent: Intent = signGoogle.signInIntent
            startActivityForResult(intent, GOOGLE_REQ_CODE)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        GoogleAuthProvider.getCredential(idToken, null).apply {
            auth.signInWithCredential(this)
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        fragmentManager?.beginTransaction()?.addToBackStack("signInSocial")
                            ?.replace(R.id.fragment, SignUpProfile())?.commit()
                    } else {
                        Log.w("AuthWithGoogle", "signInWithCredential:failure", task.exception)
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GOOGLE_REQ_CODE -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                if (task.isSuccessful) {
                    val account = task.result!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } else {
                    Log.d("ActivityResult", task.exception.toString())
                }
            }
        }
    }
}