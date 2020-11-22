package com.example.gotalk.data.entities

data class Message(
    var senderId: String,
    var recipientId: String,
    val text: String,
    var time: String,
    var isMine: Boolean = false
) {
    constructor() : this("", "", "", "")
}