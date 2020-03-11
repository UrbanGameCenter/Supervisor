package com.ugc.supervisor.common.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ugc.supervisor.websocket.model.MessageFrom

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: MessageFrom)
}