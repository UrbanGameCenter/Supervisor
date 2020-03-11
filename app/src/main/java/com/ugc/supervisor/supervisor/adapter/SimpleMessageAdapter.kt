package com.ugc.supervisor.supervisor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ugc.supervisor.R
import com.ugc.supervisor.common.ui.BaseViewHolder
import com.ugc.supervisor.supervisor.ui.MessageViewHolder
import com.ugc.supervisor.supervisor.ui.SystemViewHolder
import com.ugc.supervisor.websocket.model.MessageEmitter
import com.ugc.supervisor.websocket.model.MessageFrom
import java.util.*


class SimpleMessageAdapter(val context: Context) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val messageFromList: MutableList<MessageFrom> = ArrayList()

    private val TYPE_MESSAGE = 0
    private val TYPE_SYSTEM = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        when (viewType) {
            TYPE_SYSTEM -> return SystemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.system_item_layout, parent, false)
            )
            else -> return MessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.message_item_layout, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bind(messageFromList[position])
    }

    override fun getItemViewType(position: Int): Int {
        when (messageFromList.get(position).emitter) {
            MessageEmitter.system -> return TYPE_SYSTEM
            MessageEmitter.supervisor -> return TYPE_MESSAGE
            else -> return -1
        }
    }

    override fun getItemCount(): Int {
        return if (messageFromList.size > 0) messageFromList.size else 0
    }

    fun addData(messageFrom: MessageFrom) {
        messageFromList.add(messageFrom)
        notifyItemChanged(messageFromList.size)
    }

    fun setData(roomMessages: List<MessageFrom>) {
        messageFromList.addAll(roomMessages)
    }
}