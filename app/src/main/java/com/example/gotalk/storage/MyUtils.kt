package com.example.gotalk.storage

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImage(avatar: String?) {
    Glide.with(context!!)
        .load(avatar)
        .circleCrop()
        .into(this)
}

fun ImageView.setImage(avatar: Uri?) {
    Glide.with(context!!)
        .load(avatar)
        .circleCrop()
        .into(this)
}