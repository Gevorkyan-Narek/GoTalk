package com.example.gotalk.data.model

import android.util.Log
import com.example.gotalk.data.entities.Message
import com.example.gotalk.data.entities.User
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

class DialogModel : RepoModel() {

    fun refreshMessages(messageCallback: MessageCallback, recipientId: String) {
        messageReferenceDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                if (message != null) {
                    if ((message.senderId == user.uid && message.recipientId == recipientId) ||
                        (message.senderId == recipientId && message.recipientId == user.uid)
                    ) {
                        message.isMine = message.senderId == user.uid
                        messageCallback.getNewMessage(message)
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

    fun sendMessage(message: String, recipientId: String) {
        messageReferenceDB.push().setValue(
            Message(
                user.uid,
                recipientId,
                message,
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            )
        )
    }

    fun getRecipient(messageCallback: MessageCallback) {
        usersReferenceDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Recipient", "Searching")
                val probablyRecipient = snapshot.getValue(User::class.java)
                if (probablyRecipient != null) {
                    Log.d("Recipient", "Found")
                    messageCallback.getRecipient(probablyRecipient)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun setReferences(vararg params: String) {
        usersReferenceDB = reference.child("users").child(params.first())
        messageReferenceDB = reference.child("messages")
    }
}