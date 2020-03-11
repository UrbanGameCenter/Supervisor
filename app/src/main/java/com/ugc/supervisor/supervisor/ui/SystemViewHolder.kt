package com.ugc.supervisor.supervisor.ui

import android.view.View
import com.ugc.supervisor.common.ui.BaseViewHolder
import com.ugc.supervisor.websocket.model.MessageFrom
import kotlinx.android.synthetic.main.message_item_layout.view.*

class SystemViewHolder(itemView: View) : BaseViewHolder<MessageFrom>(itemView) {

    val messageTextView = itemView.message_textview
    val infoTextView = itemView.info_textview

    override fun bind(item: MessageFrom) {
        messageTextView.setText(item.message)
        infoTextView.setText(item.getFormatedDate())
    }
}