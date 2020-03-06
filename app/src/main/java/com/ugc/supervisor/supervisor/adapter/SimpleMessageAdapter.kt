package com.ugc.supervisor.supervisor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ugc.supervisor.R
import com.ugc.supervisor.websocket.model.MessageFrom
import kotlinx.android.synthetic.main.simple_message_item_layout.view.*
import java.util.*

class SimpleMessageAdapter(val context: Context)
    : RecyclerView.Adapter<SimpleMessageAdapter.ViewHolder>() {

    private val messageFromList: MutableList<MessageFrom> = ArrayList()

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.simple_message_item_layout, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messageFrom: MessageFrom = messageFromList[position]

        holder.bind(messageFrom)
    }


    override fun getItemCount(): Int {
        return if (messageFromList.size > 0) messageFromList.size else 0
    }

    fun clearData() {
        messageFromList.clear()
    }

    fun addData(messageFrom: MessageFrom) {
        messageFromList.add(messageFrom)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView = itemView.message_textview
        val infoTextView = itemView.info_textview

        fun bind(messageFrom : MessageFrom) {
            if (!messageFrom.isServerMessage) {
                // TODO
            } else {
                //TODO
            }
            messageTextView.setText(messageFrom.message)
            infoTextView.setText(messageFrom.getFormatedDate() + " Message envoyé")
        }
    }
}