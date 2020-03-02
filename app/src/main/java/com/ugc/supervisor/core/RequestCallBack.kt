package com.ugc.supervisor.core

import com.ugc.supervisor.model.UgcError

interface RequestCallBack<Result> {

    fun onSuccess(result : Result)

    fun onError(error : UgcError)

}