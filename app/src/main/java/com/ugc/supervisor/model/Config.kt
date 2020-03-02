package com.ugc.supervisor.model

import com.google.gson.annotations.SerializedName

data class Config(
    @SerializedName("status") public val status : String,
    @SerializedName("app_min_version") public val minVersion : Int
)