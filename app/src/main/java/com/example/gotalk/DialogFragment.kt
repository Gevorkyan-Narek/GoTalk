package com.example.gotalk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.gotalk.dagger.App
import com.example.gotalk.databinding.ActivityMainBinding
import com.example.gotalk.databinding.DialogBinding
import com.example.gotalk.model.Message
import com.example.gotalk.model.User
import com.example.gotalk.storage.setImage
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog.*
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class DialogFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var reference: DatabaseReference

    @Inject
    lateinit var user: FirebaseUser
    lateinit var recipient: User

    private lateinit var recipientReferenceDB: DatabaseReference
    private lateinit var messageReferenceDB: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().inject(this)
        val recipientId = arguments?.getString("recipientId")
        if (recipientId != null) {
            recipientReferenceDB =
                reference.child("users").child(user.uid).child("recipients").child(recipientId)
            messageReferenceDB = recipientReferenceDB.child("messages")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog, container, false)
        val view = binding.root
        recipientReferenceDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val probablyRecipient = snapshot.getValue(User::class.java)
                if (probablyRecipient != null) {
                    recipient = probablyRecipient
                    binding.recipient = recipient
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = DialogAdapter(R.layout.dialog, activity!!, arrayListOf())
        dialogListView.adapter = adapter

        messageChildListener(adapter)
        send.setOnClickListener(this)
        back.setOnClickListener { fragmentManager?.popBackStack() }
    }

    private fun messageChildListener(adapter: DialogAdapter) {
        messageReferenceDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)

                if (message != null) {
                    try {
                        message.isMine = message.senderId == user.uid
                        adapter.add(message)
                        if (dialogListView != null)
                            dialogListView.smoothScrollToPosition(dialogListView.count - 1)

                    } catch (nlp: NullPointerException) {
                        nlp.printStackTrace()
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