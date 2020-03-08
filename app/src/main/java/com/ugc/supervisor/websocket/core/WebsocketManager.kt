package com.ugc.supervisor.websocket.core

import android.os.Handler
import android.util.Log

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ugc.supervisor.core.WEB_SERVICE_BASE_URL
import com.ugc.supervisor.model.Room
import com.ugc.supervisor.websocket.model.EventType
import com.ugc.supervisor.websocket.model.MessageFrom
import com.ugc.supervisor.websocket.model.MessageTo
import io.socket.client.IO
import io.socket.emitter.Emitter
import org.json.JSONObject

class WebsocketManager private constructor() {

    private object HOLDER {
        val INSTANCE = WebsocketManager()
    }

    companion object {
        val instance: WebsocketManager by lazy { HOLDER.INSTANCE }
    }

    private val WEBSOCKET_LOGGER_TAG = "Websocket manager Logger"
    private var socket = IO.socket(WEB_SERVICE_BASE_URL)
    private val gson: Gson


    init {
        Log.d(
            WEBSOCKET_LOGGER_TAG,
            "init Websocket Manager "
        )
        gson = GsonBuilder()
            .setLenient()
            .create()

        initSocket()
    }


    public fun subscribeEvent(event: String, listener: Emitter.Listener) {
        Log.d(WEBSOCKET_LOGGER_TAG, "Event " + event + " now listened")
        socket.on(event, listener)
    }

    private fun initSocket() {
        Log.d(WEBSOCKET_LOGGER_TAG, "init Socket.io connection ... ")

        //create connection
        socket.connect()

        if (socket.connected()) {
            Log.d(
                WEBSOCKET_LOGGER_TAG,
                "Socket.io connection success !"
            )
        } else {
            Log.e(
                WEBSOCKET_LOGGER_TAG,
                "Socket.io connection failed, retry"
            )
            val handler = Handler()
            val r = Runnable { initSocket() }
            handler.postDelayed(r, 1000)
        }
    }


    fun sendMessage(message: MessageTo) {
        socket.emit(
            EventType.supervisorMessage.name,
            gson.toJson(message)
        )
    }

    fun onDestroy() {
        socket.disconnect()
    }
}