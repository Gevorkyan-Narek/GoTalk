package com.example.gotalk

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gotalk.model.Message
import com.example.gotalk.storage.Storage
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DialogFragment : Fragment(R.layout.dialog), View.OnClickListener {

    private val database = FirebaseDatabase.getInstance()
    private lateinit var messageReferenceDB: DatabaseReference
    private lateinit var messagesChildEventListener: ChildEventListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        personName.text = getString(R.string.personName, Storage.getPersonName())
        avatar.setImage(Storage.getPersonAvatar())
        messageReferenceDB = database.reference.child("messages")
        recyclerDialog.adapter = DialogAdapter()
        send.setOnClickListener(this)

        messagesChildEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)

                if (message != null) {
                    (recyclerDialog.adapter as DialogAdapter).add(message)
                    recyclerDialog.smoothScrollToPosition(recyclerDialog.adapter?.itemCount!!)
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
        }

        messageReferenceDB.addChildEventListener(messagesChildEventListener)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send -> {
                if (inputMessage.text.isNotBlank()) {
                    messageReferenceDB.push().setValue(
                        Message(
                            inputMessage.text.toString(),
                            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                            Storage.getPersonName()!!
                        )
                    )
                    inputMessage.text.clear()
                }
            }
        }
    }
}