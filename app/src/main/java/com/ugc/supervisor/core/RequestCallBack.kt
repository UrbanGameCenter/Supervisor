package com.ugc.supervisor.core

import androidx.annotation.UiThread
import com.ugc.supervisor.model.UgcError

interface RequestCallBack<Result> {

    @UiThread
    fun onSuccess(result : Result)

    @UiThread
    fun onError(error : UgcError)

}