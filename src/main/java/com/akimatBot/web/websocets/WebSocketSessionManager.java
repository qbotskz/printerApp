package com.akimatBot.web.websocets;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {
    public static final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public static void addSession(WebSocketSession session, Long sessionKey) {
        sessions.put(sessionKey, session);
    }

    public static void removeSession(Long sessionKey) {
        sessions.remove(sessionKey);
    }

    public static WebSocketSession getSession(Long sessionId) {
        for (Map.Entry<Long, WebSocketSession> session : sessions.entrySet()) {
            System.out.println("Key = " + session.getKey() + " Value = " + session.getKey());
        }
        return sessions.get(sessionId);
    }
}