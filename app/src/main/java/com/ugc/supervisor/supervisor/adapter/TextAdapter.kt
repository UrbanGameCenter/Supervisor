package com.ugc.ugctv.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ugc.supervisor.R
import com.ugc.supervisor.model.Room
import com.ugc.supervisor.supervisor.callback.SelectTextCallback
import kotlinx.android.synthetic.main.simple_item_view.view.*

class TextAdapter(val room : Room, val callback: SelectTextCallback, val context : Context)
    : RecyclerView.Adapter<TextAdapter.ViewHolder>() {

    private val textList: Array<String> = getMessagesForRoom(room)

    private fun getMessagesForRoom(room: Room): Array<String> {
        return context.resources.getStringArray(room.listMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.simple_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text: String = textList[position]

        holder.textview.text = text

        holder.itemView.setOnClickListener{
            callback.onTextSelected(text)
        }
    }


    override fun getItemCount(): Int {
        return textList.size;
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textview = itemView.simple_textview

    }
}