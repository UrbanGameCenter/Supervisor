package com.ugc.supervisor.websocket.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MessageFrom {
    var emitter: String
    var message: String
    private var date: String
    var isServerMessage : Boolean = false

    constructor(nickname: String, message: String) {

        this.emitter = nickname
        this.message = message
        this.date = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("HH:mm:ss")
        )
    }

    fun setIsServerMessage(isServerMessage: Boolean): MessageFrom {
        this.isServerMessage = isServerMessage
        return this
    }

    fun getFormatedDate() : String{
        return date
    }

}