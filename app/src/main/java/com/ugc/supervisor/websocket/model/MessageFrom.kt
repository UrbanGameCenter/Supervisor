package com.ugc.supervisor.websocket.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MessageFrom {
    var emitter: MessageEmitter
    var message: String
    private var date: String
    var isServerMessage : Boolean = false

    constructor(emmiter: MessageEmitter, message: String) {

        this.emitter = emmiter
        this.message = message
        this.date = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("HH:mm:ss")
        )
    }


    fun getFormatedDate() : String{
        return date
    }

}