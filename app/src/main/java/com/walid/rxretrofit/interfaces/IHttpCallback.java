package com.walid.rxretrofit.interfaces;

public interface IHttpCallback<T> {
    void onError(int i11, String str);

    void onNext(T t11);

    void reportExceptionLog(Throwable th2);
}