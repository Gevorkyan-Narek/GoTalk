package com.example.gotalk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gotalk.storage.CallbackAdapter
import com.example.gotalk.storage.Storage
import kotlinx.android.synthetic.main.chat.*

class ChatFragment: Fragment(R.layout.chat), CallbackAdapter {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        avatar.setImage(Storage.getPersonAvatar())
        chatRecycler.adapter = ChatAdapter(this)
    }

    override fun changeFragment() {
        fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.fragment, DialogFragment())?.commit()
    }
}