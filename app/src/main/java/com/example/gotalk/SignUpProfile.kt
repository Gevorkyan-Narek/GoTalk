package com.example.gotalk

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.sign_in_social.back
import kotlinx.android.synthetic.main.sign_up_profile.*

class SignUpProfile : Fragment(R.layout.sign_up_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        back.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        inputFullName.setText(arguments?.getString("displayName"))
        Glide.with(context!!)
            .load(Uri.decode(arguments?.getString("photoUri")))
            .circleCrop()
            .into(avatar)

        signIn.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.fragment, ChatFragment())?.commit()
        }
    }
}