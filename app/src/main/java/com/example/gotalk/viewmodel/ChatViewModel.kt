package com.example.gotalk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gotalk.data.entities.User
import com.example.gotalk.data.model.ChatCallback
import com.example.gotalk.data.model.ChatModel

class ChatViewModel : BaseViewModel(), ChatCallback {

    override val repoModel: ChatModel = ChatModel()

    lateinit var user: User
    var userList = arrayListOf<User>()
    var recipientList = MutableLiveData<ArrayList<User>>()

    override fun onCreate() {
        repoModel.init()
        repoModel.setReferences()
        user = repoModel.getUser()
    }

    fun onCreateView() {
        repoModel.getRecipientValues(this)
    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onBackPressed() {

    }

    override fun getRecipient(recipient: User) {
        if (!userList.contains(recipient)) {
            userList.add(recipient)
            recipientList.value = userList
        }
    }
}