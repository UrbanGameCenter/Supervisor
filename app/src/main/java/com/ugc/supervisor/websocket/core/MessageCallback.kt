package com.ugc.supervisor.websocket.core

import android.os.Message

interface MessageCallback{

    public fun onMessageReceived(message : Message)
}