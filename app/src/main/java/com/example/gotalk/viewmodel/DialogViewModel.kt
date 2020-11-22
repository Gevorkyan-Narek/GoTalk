package com.example.gotalk.viewmodel

import androidx.databinding.ObservableField
import com.example.gotalk.data.entities.Message
import com.example.gotalk.data.entities.User
import com.example.gotalk.data.model.DialogModel
import com.example.gotalk.data.storage.FragmentNavigator
import com.example.gotalk.data.model.MessageCallback
import com.example.gotalk.data.storage.SmoothScrollToPositionCallback
import com.example.gotalk.view.adapter.DialogAdapter

class DialogViewModel(private val navigator: FragmentNavigator, private val smoothScrollToPositionCallback: SmoothScrollToPositionCallback) : BaseViewModel(), MessageCallback {

    lateinit var recipientId: String
    lateinit var recipient: User
    private val messages = arrayListOf<Message>()

    val inputMessage = ObservableField<String>()
    val dialogAdapter = DialogAdapter(messages)

    override val repoModel: DialogModel = DialogModel()

    fun sendMessage() {
        val message = inputMessage.get()
        if (message != null) {
            repoModel.sendMessage(message, recipientId)
            inputMessage.set("")
        }
    }

    override fun getNewMessage(message: Message) {
        messages.add(message)
        dialogAdapter.notifyDataSetChanged()
        smoothScrollToPositionCallback.scrollToPos(dialogAdapter.itemCount)
    }

    override fun getRecipient(recipient: User) {
        this.recipient = recipient
    }

    override fun onCreate() {
        repoModel.init()
        repoModel.setReferences(recipientId)
        repoModel.getRecipient(this)
    }

    override fun onStart() {
        repoModel.refreshMessages(this, recipientId)
    }

    override fun onResume() {

    }

    override fun onBackPressed() {
        navigator.navigateToBack()
    }
}