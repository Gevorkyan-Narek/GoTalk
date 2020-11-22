package com.example.gotalk.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.gotalk.data.entities.Message
import com.example.gotalk.databinding.ItemDialog2Binding
import com.example.gotalk.databinding.ItemDialogBinding

class DialogAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<DialogAdapter.ViewHolder>() {

    companion object {
        private const val MINE = 0
        private const val NOT_MINE = 1
    }

    class ViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            if (itemViewType == MINE) {
                (binding as ItemDialogBinding).message = message
            } else {
                (binding as ItemDialog2Binding).message = message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == MINE) {
            ItemDialogBinding.inflate(inflater, parent, false)
        } else ItemDialog2Binding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(messages[position])

    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isMine) {
            MINE
        } else NOT_MINE
    }
}