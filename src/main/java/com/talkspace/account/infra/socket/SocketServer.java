package com.talkspace.account.infra.socket;

public interface SocketServer {
    void start();
    void stop();
    void onConnect();
    void onDisconnect();
    <T> void send(String event, T data);
    <T> void listener(String event, Class<T> type, SocketListener<T> listener);
}
