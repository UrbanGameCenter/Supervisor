package com.ugc.supervisor.supervisor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ugc.supervisor.R
import com.ugc.supervisor.core.AbstractActivity
import com.ugc.supervisor.core.LOGGER_TAG
import com.ugc.supervisor.core.WEB_SERVICE_BASE_URL
import com.ugc.supervisor.supervisor.adapter.SimpleMessageAdapter
import com.ugc.supervisor.websocket.model.EventType
import com.ugc.supervisor.websocket.model.Message
import kotlinx.android.synthetic.main.supervisor_activity.*
import org.json.JSONObject


class SupervisorActivity : AbstractActivity() {

    companion object {

        public fun newIntent(context: Context): Intent {
            return Intent(context, SupervisorActivity::class.java)
        }
    }

    private var hasSubsribeToSocketEvent: Boolean = false
    private var socket: Socket = IO.socket(WEB_SERVICE_BASE_URL)

    private var adapter: SimpleMessageAdapter
    private val gson: Gson

    init {
        adapter = SimpleMessageAdapter(this)
        gson = GsonBuilder()
            .setLenient()
            .create()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.supervisor_activity)

        val linearLayoutManager = LinearLayoutManager(this);

        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true)

        message_listview.addItemDecoration(DividerItemDecoration(
            message_listview.getContext(),
            linearLayoutManager.getOrientation()
        ))

        message_listview.setLayoutManager(linearLayoutManager);
        message_listview.setAdapter(adapter);

        if (!hasSubsribeToSocketEvent) {
            subscribeEvent();
        }

        send_button.setOnClickListener {
            if (socket.connected()) {
                socket.emit(
                    EventType.supervisorMessage.name,
                    textmessage_edittext.getText().toString()
                )
                textmessage_edittext.getText().clear()
            }
        }

        initSocket();
    }

    private fun subscribeEvent() {
        socket.on(EventType.supervisorMessage.name, onSupervisorMessage)
        socket.on(EventType.serverMessage.name, onServerMessage)
        hasSubsribeToSocketEvent = true
    }

    private fun initSocket() {
        Log.d(
            LOGGER_TAG,
            "init Socket.io connection ... "
        )
        //create connection
        socket.connect()

        if (socket.connected()) {
            Log.d(
                LOGGER_TAG,
                "Socket.io connection success !"
            )
            socket.emit(EventType.join.name, "Supervisor")
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


    private val onSupervisorMessage =
        Emitter.Listener { args: Array<Any> ->
            runOnUiThread {
                addMessage(getMessage(args[0] as JSONObject))
            }
        }

    private val onServerMessage =
        Emitter.Listener { args: Array<Any?> ->
            runOnUiThread {
                addMessage(getMessage(args[0] as JSONObject).setIsServerMessage(true))
            }
        }

    private fun getMessage(data: JSONObject): Message {
        return Message(
            data["emitter"] as String,
            data["message"] as String,
            data["date"] as String
        )
    }

    private fun addMessage(message: Message) {
        Log.d(
            LOGGER_TAG,
            "Message from " + message.emitter +" : " + message.message)

        adapter.addData(message)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.off(EventType.supervisorMessage.name, onSupervisorMessage)
        socket.off(EventType.serverMessage.name, onServerMessage)
        hasSubsribeToSocketEvent = false
    }

}