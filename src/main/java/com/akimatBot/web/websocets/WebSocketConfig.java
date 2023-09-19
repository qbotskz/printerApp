package com.akimatBot.web.websocets;

import com.akimatBot.web.websocets.handlers.KitchenPrint;
import com.akimatBot.web.websocets.handlers.PaymentPrint;
import com.akimatBot.web.websocets.handlers.PrecheckPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;


@Configuration
@EnableWebSocket
@CrossOrigin(origins = "*")
public class WebSocketConfig implements WebSocketConfigurer {

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
//        webSocketHandlerRegistry.addHandler(pongWebSocket(), "/pong");
//    }

    @Autowired
    KitchenPrint kitchenPrint;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(kitchenPrint, "/api/websocket/pong").setAllowedOrigins("*");
        registry.addHandler(precheckPrint(), "/api/websocket/precheckPrint").setAllowedOrigins("*");
        registry.addHandler(paymentPrint(), "/api/websocket/paymentPrint").setAllowedOrigins("*");
//        registry.addHandler(cancelOrderItemPrint(), "/api/websocket/cancelOrderItemPrint").setAllowedOrigins("*");
    }

    //    @Bean
//    public WebSocketHandler kitchenPrint() {
//        return new KitchenPrint();
//    }
    @Bean
    public WebSocketHandler precheckPrint() {
        return new PrecheckPrint();
    }

    @Bean
    public WebSocketHandler paymentPrint() {
        return new PaymentPrint();
    }
//    @Bean
//    public CancelOrderItemPrint cancelOrderItemPrint() {
//        return new CancelOrderItemPrint();
//    }

    public WebSocketHandlerDecoratorFactory webSocketHandlerDecoratorFactory() {
        return new CustomWebSocketHandlerDecoratorFactory();
    }
}