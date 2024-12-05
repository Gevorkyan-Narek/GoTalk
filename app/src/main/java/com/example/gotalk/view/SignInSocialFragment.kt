package com.example.gotalk.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gotalk.R
import kotlinx.android.synthetic.main.sign_in_social.*

class SignInSocialFragment : Fragment(R.layout.sign_in_social) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.apply {
            if (this != null)
                labelGoSocial.text = getString(getInt("Social", R.string.sign_in_with_google))
        }

        signIn.setOnClickListener {
            fragmentManager?.beginTransaction()?.addToBackStack("signInSocial")
                ?.replace(R.id.fragment, SignUpProfile())?.commit()
        }

        back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
}