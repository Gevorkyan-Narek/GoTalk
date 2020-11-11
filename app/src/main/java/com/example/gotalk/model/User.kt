package com.example.gotalk.model

import android.net.Uri

data class User (
    val name: String,
    val lastMessage: String?,
    val lastMessageTime: String?,
    val avatar: Uri?
)