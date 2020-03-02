package com.ugc.supervisor.core

import com.ugc.supervisor.model.Config
import com.ugc.supervisor.model.DefaultResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiEndPoint {

    companion object {
        const val API = "/api"
        const val V1 = "/v1"

        const val TECHNICAL = "/technical"

    }

    @GET(API + V1 + TECHNICAL + "/config")
    fun getConfig() : Call<Config>

}