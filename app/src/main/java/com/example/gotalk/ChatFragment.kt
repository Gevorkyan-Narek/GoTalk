package com.example.gotalk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gotalk.dagger.App
import com.example.gotalk.model.User
import com.example.gotalk.storage.CallbackAdapter
import com.example.gotalk.storage.setImage
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.chat.*
import org.jetbrains.annotations.Nullable
import javax.inject.Inject

class ChatFragment : Fragment(R.layout.chat), CallbackAdapter {

    @Inject
    lateinit var reference: DatabaseReference

    @Inject
    @Nullable
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        avatar.setImage(user.photoUrl)
        chatRecycler.adapter = ChatAdapter(this)

        reference.child("users").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val recipient = snapshot.getValue(User::class.java)
                if (recipient != null && user.uid != recipient.id) {
                    (chatRecycler.adapter as ChatAdapter).add(recipient)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun changeFragment(id: String) {
        val dialogFragment = DialogFragment()
        dialogFragment.arguments = Bundle().apply {
            putString("recipientId", id)
        }

        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment, dialogFragment, id)?.addToBackStack("ChatFragment")?.commit()
    }
}