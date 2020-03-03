package com.ugc.supervisor.supervisor.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ugc.supervisor.R
import com.ugc.supervisor.websocket.model.Message
import kotlinx.android.synthetic.main.simple_message_item_layout.view.*
import java.util.*

class SimpleMessageAdapter(val context: Context)
    : RecyclerView.Adapter<SimpleMessageAdapter.ViewHolder>() {

    private val messageList: MutableList<Message> = ArrayList()

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.simple_message_item_layout, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message: Message = messageList[position]

        holder.bind(message)
    }


    override fun getItemCount(): Int {
        return if (messageList.size > 0) messageList.size else 0
    }

    fun clearData() {
        messageList.clear()
    }

    fun addData(message: Message) {
        messageList.add(message)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView = itemView.message_textview
        val infoTextView = itemView.info_textview

        fun bind(message : Message) {
            if (!message.isServerMessage) {
                // TODO
            } else {
                //TODO
            }
            messageTextView.setText(message.message)
            infoTextView.setText(message.getFormatedDate() + " Message envoyé")
        }
    }
}