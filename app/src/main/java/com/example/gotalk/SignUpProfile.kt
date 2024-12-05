package com.example.gotalk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.sign_in_social.*

class SignUpProfile : Fragment(R.layout.sign_up_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
}