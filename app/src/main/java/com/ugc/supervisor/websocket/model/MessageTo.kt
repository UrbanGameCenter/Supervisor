package com.ugc.supervisor.websocket.model

import com.google.gson.annotations.SerializedName

class MessageTo(
    @SerializedName("message") val message : String,
    @SerializedName("room")val room : String) {
}