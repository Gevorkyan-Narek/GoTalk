package com.example.gotalk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.gotalk.storage.Storage
import kotlinx.android.synthetic.main.sign_in_social.back
import kotlinx.android.synthetic.main.sign_up_profile.*

class SignUpProfile : Fragment(R.layout.sign_up_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        back.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        inputFullName.setText(Storage.getPersonName())
        avatar.setImage(Storage.getPersonAvatar())

        signIn.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.fragment, ChatFragment())?.commit()
        }
    }
}