package com.example.gotalk.model

data class User (
    val id: String,
    val name: String?,
    val email: String?,
    val avatar: String?
){
    constructor(): this("",null,null, null)
}