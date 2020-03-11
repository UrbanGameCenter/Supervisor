package com.ugc.supervisor.websocket.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MessageFrom(val emitter: MessageEmitter, var message: String) {

    private var date: String

    init {
        this.date = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("HH:mm:ss")
        )
    }

    fun getFormatedDate() : String{
        return date
    }

}