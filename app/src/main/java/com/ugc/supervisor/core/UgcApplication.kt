package com.ugc.supervisor.core

import android.app.Application
import com.ugc.supervisor.websocket.core.WebsocketManager

class UgcApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        WebsocketManager.instance
    }
}