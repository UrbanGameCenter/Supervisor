package com.ugc.supervisor.supervisor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ugc.supervisor.R
import com.ugc.supervisor.common.ui.BaseViewHolder
import com.ugc.supervisor.supervisor.ui.MessageViewHolder
import com.ugc.supervisor.supervisor.ui.SystemViewHolder
import com.ugc.supervisor.websocket.model.MessageEmitter
import com.ugc.supervisor.websocket.model.MessageFrom
import kotlinx.android.synthetic.main.message_item_layout.view.*
import java.util.*


class SimpleMessageAdapter(val context: Context)
    : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val messageFromList: MutableList<MessageFrom> = ArrayList()

    private val TYPE_MESSAGE = 0
    private val TYPE_SYSTEM = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        if(viewType == TYPE_MESSAGE) {
             return MessageViewHolder(
                 LayoutInflater.from(context).inflate(R.layout.message_item_layout, parent, false)
             )

        }else {
            return SystemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.system_item_layout, parent, false)
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
            if (messageFrom.emitter.equals(MessageEmitter.system)) {
                // TODO
            } else {
                messageTextView.setText(messageFrom.message)
                infoTextView.setText(messageFrom.getFormatedDate() + " Message envoyé")
            }
        }
    }
}