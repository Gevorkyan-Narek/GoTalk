package com.example.gotalk.data.model

import com.example.gotalk.data.entities.User
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class ChatModel : RepoModel() {

    override fun setReferences(vararg params: String) {
        usersReferenceDB = reference.child("users")
    }

    fun getUser() = User(
        user.uid,
        user.displayName,
        user.email,
        user.photoUrl.toString()
    )

    fun getRecipientValues(chatCallback: ChatCallback) {
        usersReferenceDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue(User::class.java)?.let {
                    chatCallback.getRecipient(it) }
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
}