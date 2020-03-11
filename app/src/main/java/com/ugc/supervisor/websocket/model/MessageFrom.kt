package com.ugc.supervisor.websocket.model

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MessageFrom(val emitter: MessageEmitter, var message: String, var date : Long) {

    fun getFormatedDate() : String{

        return SimpleDateFormat("HH:mm:ss")
            .format(
                Date(Timestamp(date).getTime())
            )
    }
}