package com.akimatBot.web.websocets;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

public class CustomWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {

    private static final int OUTPUT_BUFFER_SIZE = 1024000 ; // Установите желаемый размер буфера вывода

    @Override
    public WebSocketHandler decorate(WebSocketHandler handler) {
        return new WebSocketHandlerDecorator(handler) {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                // Установить размер буфера вывода для сеанса WebSocket
                session.setTextMessageSizeLimit(OUTPUT_BUFFER_SIZE);
                super.afterConnectionEstablished(session);
            }
        };
    }
}
