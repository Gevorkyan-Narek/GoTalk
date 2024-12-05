package com.example.gotalk.data.model

import com.example.gotalk.data.entities.Message
import com.example.gotalk.data.entities.User

interface MessageCallback {

    fun getNewMessage(message: Message)

    fun getRecipient(recipient: User)
}