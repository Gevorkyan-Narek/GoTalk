package com.example.gotalk.model

data class Message (
    var text: String,
    var time: String,
    var userName: String
) {
    constructor(): this("","","")
}