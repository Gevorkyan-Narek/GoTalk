package com.example.gotalk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gotalk.dagger.App
import com.example.gotalk.model.Message
import com.example.gotalk.model.User
import com.example.gotalk.storage.setImage
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.dialog.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DialogFragment : Fragment(R.layout.dialog), View.OnClickListener {

    @Inject
    lateinit var reference: DatabaseReference

    @Inject
    lateinit var user: FirebaseUser
    lateinit var recipient: User

    lateinit var usersReferenceDB: DatabaseReference
    lateinit var messageReferenceDB: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().inject(this)
        usersReferenceDB = reference.child("users")
        messageReferenceDB = reference.child("messages")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recipientId = arguments?.getString("recipientId")
        val messages = arrayListOf<Message>()
        val adapter = DialogAdapter(R.layout.dialog, activity!!, messages)
        dialogListView.adapter = adapter
        dialogListView.isSmoothScrollbarEnabled = true

        usersReferenceDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val possibleRecipient = snapshot.getValue(User::class.java)
                if (possibleRecipient != null && possibleRecipient.id == recipientId) {
                    recipient = possibleRecipient
                    avatar.setImage(recipient.avatar)
                    personName.text = recipient.name
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
        send.setOnClickListener(this)
        back.setOnClickListener { fragmentManager?.popBackStack() }

        messageReferenceDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)

                if (message != null) {
                    if (message.senderId == user.uid && message.recipientId == recipient.id) {
                        try {
                            message.isMine = true
                            adapter.add(message)
                            dialogListView.smoothScrollToPosition(dialogListView.count - 1)

                        } catch (nlp: NullPointerException) {
                            nlp.printStackTrace()
                        }
                    } else if ((message.senderId == user.uid && message.recipientId == recipient.id) ||
                        (message.senderId == recipient.id && message.recipientId == user.uid)
                    ) {
                        try {
                            message.isMine = false
                            adapter.add(message)
                            dialogListView.smoothScrollToPosition(dialogListView.count - 1)
                        } catch (nlp: NullPointerException) {
                            nlp.printStackTrace()
                        }
                    }
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send -> {
                if (inputMessage.text.isNotBlank()) {
                    messageReferenceDB.push().setValue(
                        Message(
                            inputMessage.text.toString(),
                            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                            user.displayName ?: "No name",
                            user.uid,
                            recipient.id
                        )
                    )
                    inputMessage.text.clear()
                }
            }
        }
    }
}