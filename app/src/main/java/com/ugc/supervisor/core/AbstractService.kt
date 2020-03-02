package com.ugc.supervisor.core

import android.content.Context
import android.util.Log
import com.ugc.supervisor.R
import com.ugc.supervisor.model.ErrorType
import com.ugc.supervisor.model.UgcError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call

abstract class AbstractService<T>(var context : Context) {

    val apiEndPoint: ApiEndPoint

    init {
        apiEndPoint = NetworkUtil.getRetrofit().create(ApiEndPoint::class.java)
    }


    protected suspend fun <Result> execute(call: Call<Result>, callback: RequestCallBack<Result> ) {
        try {
            if(!NetworkUtil.verifyAvailableNetwork(context)){
                callback.onError(
                    UgcError(ErrorType.NETWORK_ERROR, context.getString(R.string.netword_unavailable_generic_error)))
                return
            }
            val response= call.execute()

            CoroutineScope(Dispatchers.Main).launch {

                if (response.isSuccessful) {
                    response.body()?.let { callback.onSuccess(it) }
                } else {
                    callback.onError(
                        UgcError(ErrorType.BACKEND_ERROR, response.errorBody()!!.string())
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(AbstractService::class.java.simpleName, e.toString())
            callback.onError(
                UgcError(ErrorType.UNEXCEPTED_ERROR, context.getString(R.string.unexcepted_generic_error)))
        }
    }
}

