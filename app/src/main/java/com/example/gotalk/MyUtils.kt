package com.example.gotalk

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImage(avatar: Uri?) {
    Glide.with(context!!)
        .load(avatar)
        .circleCrop()
        .into(this)
}