package com.talkspace.account.infra.socket;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SocketIOAdapter implements SocketServer {
    private final SocketIOServer server;

    Map<String, String> onlineAccounts = new HashMap<>();

    @Value("${api.security.token.secret}")
    private String secretKey;

    public SocketIOAdapter() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        this.server = new SocketIOServer(config);
    }

    @Override
    public void start() {
        this.server.start();
        this.onConnect();
        this.onDisconnect();
    }

    @Override
    public void stop() {
        this.server.stop();
    }

    @Override
    public void onConnect() {
        this.server.addConnectListener(client -> {
            String token = client.getHandshakeData().getSingleUrlParam("token");

            if (token != null) {
                String accountId = validateToken(token);
                String socketId = client.getSessionId().toString();
                onlineAccounts.put(accountId, socketId);
            } else {
                this.onDisconnect();
            }
        });
    }

    @Override
    public void onDisconnect() {
        this.server.addDisconnectListener(client -> {
            String socketId = client.getSessionId().toString();
            onlineAccounts.entrySet().removeIf(entry -> entry.getValue().equals(socketId));
        });
    }

    @Override
    public <T> void send(String event, T data) {
        this.server.getBroadcastOperations().sendEvent(event, data);
    }

    @Override
    public <T> void listener(String event, Class<T> type, SocketListener<T> listener) {
        this.server.addEventListener(event, type, (client, data, ack) -> {
            listener.onMessage(data);
        });
    }

    private String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.require(algorithm)
                .withIssuer("talkspace-api")
                .build()
                .verify(token)
                .getSubject();
    }
}
