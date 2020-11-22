package com.example.gotalk.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gotalk.data.entities.User
import com.example.gotalk.data.storage.FragmentNavigator
import com.example.gotalk.databinding.ItemChatBinding

class ChatAdapter(
    private var recipientList: ArrayList<User>,
    private var listener: FragmentNavigator
) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(private var binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipient: User, listener: FragmentNavigator) {
            binding.recipient = recipient
            binding.root.setOnClickListener { listener.navigateTo(recipient.id) }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemChatBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(recipientList[position], listener)

    override fun getItemCount(): Int = recipientList.size
}