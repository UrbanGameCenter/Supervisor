package com.ugc.supervisor.core;

import com.ugc.supervisor.model.UgcError;

public abstract class RequestCallBack<Result> {

    public abstract void onSuccess(Result result);

    public void onError(UgcError error) { }


}
