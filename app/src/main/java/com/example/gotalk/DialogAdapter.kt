package com.example.gotalk

import android.app.Activity
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gotalk.model.Message
import kotlinx.android.synthetic.main.item_dialog.view.*


class DialogAdapter(
    resource: Int,
    private val activity: Activity,
    private val messages: List<Message>
) :
    ArrayAdapter<Message>(activity, resource, messages) {

    companion object {
        private const val MINE = 0
        private const val NOT_MINE = 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message: TextView = itemView.message
        val time: TextView = itemView.messageTime
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val layoutInflater =
            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val message = getItem(position)
        val layoutResource = if (getItemViewType(position) == MINE) R.layout.item_dialog
        else R.layout.item_dialog_2
        val newConvertView: View
        if (convertView != null) {
            newConvertView = convertView
            viewHolder = newConvertView.tag as ViewHolder
        } else {
            newConvertView = layoutInflater.inflate(layoutResource, parent, false)
            viewHolder = ViewHolder(newConvertView)
            newConvertView.tag = viewHolder
        }

        onBindViewHolder(viewHolder, message)

        return newConvertView
    }

    private fun onBindViewHolder(holder: ViewHolder, message: Message?) {
        holder.message.text = message?.text
        holder.time.text = message?.time
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isMine) {
            MINE
        } else NOT_MINE
    }
}
// class DialogAdapter(private val messages: List<Message>) :
//    RecyclerView.Adapter<DialogAdapter.ViewHolder>() {
//
//    companion object {
//        private const val MINE = 0
//        private const val NOT_MINE = 1
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val message: TextView = itemView.message
//        val time: TextView = itemView.messageTime
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(
//            if (viewType == MINE) R.layout.item_dialog
//            else R.layout.item_dialog_2,
//            parent,
//            false
//        )
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.message.text = messages[position].text
//        holder.time.text = messages[position].time
//    }
//
//    override fun getItemCount() = messages.size
//
//    override fun getItemViewType(position: Int): Int {
//        return if (messages[position].isMine) {
//            MINE
//        } else NOT_MINE
//    }
//}