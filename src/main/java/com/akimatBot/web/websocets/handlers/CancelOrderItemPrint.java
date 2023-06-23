//package com.akimatBot.web.websocets.handlers;
//
//import com.akimatBot.web.dto.OrderItemDeleteDTO;
//import com.akimatBot.web.websocets.WebSocketSessionManager;
//import com.akimatBot.web.websocets.entities.KitchenPrintEntityRepo;
//import com.akimatBot.web.websocets.entities.OrderItemDeleteEntityRepo;
//import com.akimatBot.web.websocets.entities.PrintKitchenEntity;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//@Slf4j
//public class CancelOrderItemPrint implements WebSocketHandler {
//
//    @Value("${printerApiToken}")
//    String apiToken;
//
//    public static final long handlerId = 4;
//
////    @Autowired
////    KitchenPrintEntityRepo kitchenPrintEntityRepo;
//
//    @Autowired
//    OrderItemDeleteEntityRepo orderItemDeleteEntityRepo;
//
//    private static final int OUTPUT_BUFFER_SIZE = 1024000; // Установите желаемый размер буфера вывода
//
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        System.out.println(session.getAttributes());
//        System.out.println("Connection established");
//        session.setTextMessageSizeLimit(OUTPUT_BUFFER_SIZE);
//        session.setBinaryMessageSizeLimit(OUTPUT_BUFFER_SIZE);
////        super.afterConnectionEstablished(session);
//    }
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        System.out.println(message.getPayload());
//        if (message.getPayload().equals(apiToken)){
//            WebSocketSessionManager.removeSession(handlerId);
//            WebSocketSessionManager.addSession(session, handlerId);
//        }
//        else {
//            String msg = message.getPayload().toString();
//            OrderItemDeleteDTO orderItemDeleteDTO = new Gson().fromJson(msg, new TypeToken<OrderItemDeleteDTO>() {
//            }.getType());
//
//            orderItemDeleteEntityRepo.deleteByOrderItem(orderItemDeleteDTO.getId());
//
//        }
//    }
//
//
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        log.error("Transport error: " + exception.getMessage()+ " Handler ID = " + handlerId);
//        WebSocketSessionManager.removeSession(handlerId);
//        session.close();
//    }
//
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
////        WebSocketSessionManager.getSession(handlerId).close();
//        WebSocketSessionManager.removeSession(handlerId);
//        log.error("Connection closed: " + closeStatus.getReason() + " Handler ID = " + handlerId);
//        session.close();
//    }
//
//    @Override
//    public boolean supportsPartialMessages() {
//        return false;
//    }
//}