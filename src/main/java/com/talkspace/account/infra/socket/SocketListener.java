package com.talkspace.account.infra.socket;

public interface SocketListener<T> {
    void onMessage(T data);
}
