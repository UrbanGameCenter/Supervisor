package com.ugc.supervisor.core

import androidx.annotation.MainThread
import com.ugc.supervisor.model.UgcError

interface RequestCallBack<Result> {

    @MainThread
    fun onSuccess(result : Result)

    @MainThread
    fun onError(error : UgcError)

}