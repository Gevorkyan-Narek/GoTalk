package com.example.gotalk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gotalk.databinding.ItemChatBinding
import com.example.gotalk.model.User
import com.example.gotalk.storage.CallbackAdapter

class ChatAdapter(private val userList: List<User>, private val callbackAdapter: CallbackAdapter) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View,
        private val callbackAdapter: CallbackAdapter
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = DataBindingUtil.bind<ItemChatBinding>(itemView)

        fun bind(user: User) {
            binding?.recipient = user
            binding?.root?.setOnClickListener { callbackAdapter.changeFragment(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(itemView, callbackAdapter)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(userList[position])

    override fun getItemCount() = userList.size
}