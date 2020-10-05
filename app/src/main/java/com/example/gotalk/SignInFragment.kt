package com.example.gotalk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.sign_in.*

class SignInFragment : Fragment(R.layout.sign_in), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInGoogle.setOnClickListener(this)
        signInFacebook.setOnClickListener(this)
        signInApple.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        val fragment = SignInSocialFragment()
        val bundle = Bundle()
        when (v?.id) {
            R.id.signIn -> {
                bundle.putInt("Social", R.string.sign_in_with_google)
            }
            R.id.signInFacebook -> {
                bundle.putInt("Social", R.string.sign_in_with_facebook)
            }
            R.id.signInApple -> {
                bundle.putInt("Social", R.string.sign_in_with_apple)
            }
        }

        fragment.arguments = bundle
        fragmentManager?.beginTransaction()?.addToBackStack("signIn")
            ?.replace(R.id.fragment, fragment)?.commit()
    }
}