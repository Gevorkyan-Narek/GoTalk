package com.example.gotalk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gotalk.dagger.App
import com.example.gotalk.storage.setImage
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.sign_in_social.back
import kotlinx.android.synthetic.main.sign_up_profile.*
import javax.inject.Inject

class SignUpProfile : Fragment(R.layout.sign_up_profile) {

    @Inject
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        back.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        inputFullName.setText(user.displayName)
        avatar.setImage(user.photoUrl)

        signIn.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.fragment, ChatFragment())?.commit()
        }
    }
}