package com.example.gotalk.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.gotalk.R
import com.example.gotalk.data.storage.SignInInterface
import com.example.gotalk.databinding.SignInBinding
import com.example.gotalk.viewmodel.SignInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn


class SignInFragment : MainFragment(), SignInInterface {

    companion object {
        private const val GOOGLE_REQ_CODE = 1
    }

    override val viewModel = SignInViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SignInBinding =
            DataBindingUtil.inflate(inflater, R.layout.sign_in, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GOOGLE_REQ_CODE -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                if (task.isSuccessful) {
                    val account = task.result!!
                    viewModel.signUpProfile(account.idToken!!)
                } else {
                    Log.d("ActivityResult", task.exception.toString())
                }
            }
        }
    }

    override fun signInGoogle(intent: Intent) {
        startActivityForResult(intent, GOOGLE_REQ_CODE)
    }

    override fun signUpProfile() {
        fragmentManager?.beginTransaction()?.addToBackStack("signInSocial")
            ?.replace(R.id.fragment, SignUpProfile())?.commit()
    }
}