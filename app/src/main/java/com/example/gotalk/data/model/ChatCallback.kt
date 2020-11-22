package com.example.gotalk.data.model

import com.example.gotalk.data.entities.User

interface ChatCallback {

    fun getRecipient(recipient: User)
}
