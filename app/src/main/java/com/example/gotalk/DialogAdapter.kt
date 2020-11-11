package com.example.gotalk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gotalk.model.Message
import kotlinx.android.synthetic.main.item_dialog.view.*


class DialogAdapter : RecyclerView.Adapter<DialogAdapter.ViewHolder>() {

    private val messages = arrayListOf<Message>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message: TextView = itemView.message
        val time: TextView = itemView.messageTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_dialog,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.message.text = messages[position].text
        holder.time.text = messages[position].time
    }

    override fun getItemCount() = messages.size

    fun add(message: Message) {
        messages.add(message)
        notifyItemInserted(itemCount)
    }
}