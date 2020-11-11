package com.example.gotalk.storage

import android.net.Uri

class Storage {

    companion object {

        private val person = object {
            var name: String? = null
            var avatar: Uri? = null
        }

        fun setPerson(name: String?, avatar: Uri?) {
            person.name = name
            person.avatar = avatar
        }

        fun getPersonName() = person.name
        fun getPersonAvatar() = person.avatar
    }
}