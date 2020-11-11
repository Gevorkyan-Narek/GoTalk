package com.example.gotalk

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gotalk.storage.Storage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.sign_in.*


class SignInFragment : Fragment(R.layout.sign_in), View.OnClickListener {

    companion object {
        private const val GOOGLE_REQ_CODE = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build()

        val signGoogle = GoogleSignIn.getClient(activity!!, gso)


        signInGoogle.setOnClickListener {
            val intent: Intent = signGoogle.signInIntent
            startActivityForResult(intent, GOOGLE_REQ_CODE)
        }
        signInApple.setOnClickListener(this)
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
                    R.id.signInApple -> {
                        R.string.sign_in_with_apple
                    }
                    else -> R.string.sign_in
                }
            )

        }

        fragment.arguments = bundle
        fragmentManager?.beginTransaction()?.addToBackStack("signIn")
            ?.replace(R.id.fragment, fragment)?.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GOOGLE_REQ_CODE -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                if (task.isSuccessful) {
                    val account = task.result!!
                    Storage.setPerson(account.displayName, account.photoUrl)
                    fragmentManager?.beginTransaction()?.addToBackStack("signInSocial")
                        ?.replace(R.id.fragment, SignUpProfile())?.commit()
                } else {
                    Log.d("ActivityResult", task.exception.toString())
                }
            }
        }
    }
}