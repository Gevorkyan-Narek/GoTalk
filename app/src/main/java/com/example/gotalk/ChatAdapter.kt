package com.example.gotalk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gotalk.model.User
import com.example.gotalk.storage.CallbackAdapter
import com.example.gotalk.storage.setImage
import kotlinx.android.synthetic.main.chat.view.avatar
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter(private val callbackAdapter: CallbackAdapter) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.avatar
        val personName: TextView = itemView.personName
//        val lastMessage: TextView = itemView.lastMessage
//        val lastMessageTime: TextView = itemView.lastMessageTime
    }

    private val userList = arrayListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.avatar.setImage(userList[position].avatar)
//        holder.lastMessage.text = userList[position].lastMessage
//        holder.lastMessageTime.text = userList[position].lastMessageTime
        holder.personName.text = userList[position].name
        holder.itemView.setOnClickListener {
            callbackAdapter.changeFragment(userList[position].id)
        }
    }

    override fun getItemCount() = userList.size

    fun add(user: User) {
        userList.add(user)
        notifyItemInserted(itemCount)
    }
}