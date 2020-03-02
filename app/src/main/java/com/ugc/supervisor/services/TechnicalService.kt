package com.ugc.supervisor.services

import android.content.Context
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.ugc.supervisor.core.AbstractService
import com.ugc.supervisor.core.RequestCallBack
import com.ugc.supervisor.model.Config
import com.ugc.supervisor.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TechnicalService(context : Context) : AbstractService<Config>(context) {

    @WorkerThread
     fun getConfig(callback: RequestCallBack<Config>) {
        CoroutineScope(Dispatchers.IO).launch {
            execute(
                apiEndPoint.getConfig(),
                callback)
        }
    }
}