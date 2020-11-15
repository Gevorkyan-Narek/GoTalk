package com.example.gotalk.model

data class Message (
    var text: String,
    var time: String,
    var userName: String,
    var senderId: String,
    var recipientId: String,
    var isMine: Boolean = false
) {
    constructor(): this("","","", "", "", false)
}