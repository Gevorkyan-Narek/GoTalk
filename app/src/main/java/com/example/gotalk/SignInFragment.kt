package com.example.gotalk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gotalk.storage.Storage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.sign_in.*


class SignInFragment : Fragment(R.layout.sign_in), View.OnClickListener {

    companion object {
        private const val GOOGLE_REQ_CODE = 1
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    private lateinit var signGoogle: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        signGoogle = GoogleSignIn.getClient(activity!!, gso)

        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInGoogle.setOnClickListener {
            val intent: Intent = signGoogle.signInIntent
            startActivityForResult(intent, GOOGLE_REQ_CODE)
        }
    }

    override fun onClick(v: View?) {

        val fragment = SignInSocialFragment()
        val bundle = Bundle().apply {
            putInt(
                "Social",
                when (v?.id) {
                    R.id.signIn -> {
                        R.string.sign_in_with_google
                    }
                    R.id.signInFacebook -> {
                        R.string.sign_in_with_facebook
                    }
                    else -> R.string.sign_in
                }
            )

        }

        fragment.arguments = bundle
        fragmentManager?.beginTransaction()?.addToBackStack("signIn")
            ?.replace(R.id.fragment, fragment)?.commit()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Storage.setPerson(user?.displayName, user?.photoUrl)
                    fragmentManager?.beginTransaction()?.addToBackStack("signInSocial")
                        ?.replace(R.id.fragment, SignUpProfile())?.commit()
                } else {
                    Log.w("AuthWithGoogle", "signInWithCredential:failure", task.exception)
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