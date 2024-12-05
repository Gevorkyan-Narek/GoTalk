package com.example.gotalk.data.storage

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide

@BindingAdapter("android:src")
fun setImage(imageView: ImageView, avatar: String?) {
    Glide.with(imageView.context)
        .load(avatar)
        .circleCrop()
        .into(imageView)
}

fun ImageView.setImage(avatar: Uri?) {
    Glide.with(context!!)
        .load(avatar)
        .circleCrop()
        .into(this)
}

operator fun <T> MutableLiveData<ArrayList<T>>.plusAssign(value: T) {
    val values = this.value ?: arrayListOf()
    values.contains(value).let {
        if (!it)
            values.add(value)
    }
    this.value = values
}