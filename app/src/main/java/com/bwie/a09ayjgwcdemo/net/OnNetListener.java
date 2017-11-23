package com.bwie.a09ayjgwcdemo.net;


public interface OnNetListener<T> {
    public void OnSuccess(T t);
    public void OnFailure(Exception e);
}
