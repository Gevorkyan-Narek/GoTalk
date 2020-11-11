package com.example.gotalk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gotalk.model.User
import com.example.gotalk.storage.CallbackAdapter
import com.example.gotalk.storage.Storage
import kotlinx.android.synthetic.main.chat.view.avatar
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter(val callbackAdapter: CallbackAdapter) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val dialogList = arrayListOf(
        User(
            "Вася Пупкин",
            "Как дела?",
            "5:55",
            Storage.getPersonAvatar()
        ),
        User(
            "Никита Пупкин",
            "Че, жи есть?",
            "5:55",
            Storage.getPersonAvatar()
        )
    )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.avatar
        val personName: TextView = itemView.personName
        val lastMessage: TextView = itemView.lastMessage
        val lastMessageTime: TextView = itemView.lastMessageTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.avatar.setImage(dialogList[position].avatar)
        holder.lastMessage.text = dialogList[position].lastMessage
        holder.lastMessageTime.text = dialogList[position].lastMessageTime
        holder.personName.text = dialogList[position].name

        holder.itemView.setOnClickListener {
            callbackAdapter.changeFragment()
        }
    }

    override fun getItemCount() = dialogList.size
}