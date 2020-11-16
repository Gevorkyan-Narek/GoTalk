package com.example.gotalk

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gotalk.dagger.App
import com.example.gotalk.model.User
import com.example.gotalk.storage.CallbackAdapter
import com.example.gotalk.storage.setImage
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.chat.*
import org.jetbrains.annotations.Nullable
import javax.inject.Inject

class ChatFragment : Fragment(R.layout.chat), CallbackAdapter {

    @Inject
    lateinit var reference: DatabaseReference
    private lateinit var recipientsReference: DatabaseReference

    @Inject
    @Nullable
    lateinit var user: FirebaseUser
    private val userList = arrayListOf<User>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        App.getComponent().inject(this)
        recipientsReference = reference.child("users")
        recipientsReference.addValueEventListener(recipientsValueEventListener)

        avatar.setImage(user.photoUrl)
        chatRecycler.adapter = ChatAdapter(userList, this)
    }

    private val recipientsValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            userList.clear()
            for (ds in snapshot.children) {
                val recipient = ds.getValue(User::class.java)
                if (recipient != null)
                    userList.add(recipient)
            }
            chatRecycler.adapter?.notifyDataSetChanged()
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("ChatFragment", error.message)
        }
    }


    override fun changeFragment(recipient: User) {
        val dialogFragment = DialogFragment()
        dialogFragment.arguments = Bundle().apply {
            putString("recipientId", recipient.id)
        }

        val recipientReference = recipientsReference.child(user.uid).child("recipients")

        recipientReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val res = snapshot.children.firstOrNull { ds -> ds.key == recipient.id }
                if (res == null) recipientReference.child(recipient.id).setValue(recipient)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ChatFragment", error.message)
            }
        })

        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment, dialogFragment)?.addToBackStack("ChatFragment")
            ?.commit()
    }
}