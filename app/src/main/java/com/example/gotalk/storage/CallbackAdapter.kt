package com.example.gotalk.storage

import com.example.gotalk.model.User

interface CallbackAdapter {

    fun changeFragment(recipient: User)
}