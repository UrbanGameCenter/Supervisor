package com.ugc.supervisor.supervisor.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ugc.supervisor.R
import com.ugc.supervisor.core.LOGGER_TAG
import com.ugc.supervisor.core.WEB_SERVICE_BASE_URL
import com.ugc.supervisor.model.Room
import com.ugc.supervisor.supervisor.adapter.SimpleMessageAdapter
import com.ugc.supervisor.websocket.core.WebsocketManager
import com.ugc.supervisor.websocket.model.EventType
import com.ugc.supervisor.websocket.model.MessageFrom
import com.ugc.supervisor.websocket.model.MessageTo
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.room_fragment.message_listview
import kotlinx.android.synthetic.main.room_fragment.send_button
import kotlinx.android.synthetic.main.room_fragment.textmessage_edittext
import org.json.JSONObject

class RoomFragment(val room: Room, context: Context) : Fragment() {


    private var socket = IO.socket(WEB_SERVICE_BASE_URL)

    private val adapter: SimpleMessageAdapter

    companion object {

        fun newInstance(room: Room, context: Context): RoomFragment {
            return RoomFragment(room, context)
        }
    }

    init {
        adapter = SimpleMessageAdapter(context)
        initSocket()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.room_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val linearLayoutManager = LinearLayoutManager(activity);

        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true)

        message_listview.addItemDecoration(
            DividerItemDecoration(
                context,
                linearLayoutManager.getOrientation()
            )
        )

        message_listview.setLayoutManager(linearLayoutManager);
        message_listview.setAdapter(adapter);

        send_button.setOnClickListener {

            socket.emit(
                EventType.supervisorMessage.name,
                Gson().toJson(
                    MessageTo(
                        textmessage_edittext.getText().toString(),
                        room.name
                    )
                )
            )
            textmessage_edittext.getText().clear()
        }

    }


    private fun initSocket() {
        Log.d(LOGGER_TAG, "init Socket.io connection ... ")

        //create connection
        socket.connect()

        if (socket.connected()) {
            Log.d(
                LOGGER_TAG,
                "Socket.io connection success !"
            )
            socket.emit(EventType.join.name, "Supervisor")
            subscribeEvent()
        } else {
            Log.e(
                LOGGER_TAG,
                "Socket.io connection failed, retry"
            )
            val handler = Handler()
            val r = Runnable { initSocket() }
            handler.postDelayed(r, 1000)
        }
    }

    private fun subscribeEvent() {
        socket.on(room.name, onMessage)
        socket.on(EventType.serverMessage.name, onServerMessage)
    }

    private val onMessage = Emitter.Listener { args: Array<Any?> ->
        activity?.runOnUiThread {
            addMessage(
                Gson().fromJson(args[0] as String, MessageFrom::class.java)
            )
        }
    }

    private val onServerMessage =
        Emitter.Listener { args: Array<Any?> ->
            activity?.runOnUiThread {
                addMessage(
                    Gson().fromJson(args[0] as String, MessageFrom::class.java)
                )
            }
        }

    private fun addMessage(messageFrom: MessageFrom) {
        Log.d(
            LOGGER_TAG,
            "Message from " + messageFrom.emitter + " : " + messageFrom.message
        )

        adapter.addData(messageFrom)
        adapter.notifyDataSetChanged()
    }

}
