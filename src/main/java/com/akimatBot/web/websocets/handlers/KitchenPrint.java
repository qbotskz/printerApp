package com.akimatBot.web.websocets.handlers;

import com.akimatBot.web.dto.OrderItemDeleteDTO;
import com.akimatBot.web.dto.PrintKitchenDTO;
import com.akimatBot.web.websocets.WebSocketSessionManager;
import com.akimatBot.web.websocets.entities.KitchenPrintEntityRepo;
import com.akimatBot.web.websocets.entities.PrintKitchenEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;

@Slf4j
@Service
public class KitchenPrint implements WebSocketHandler {

    @Value("${printerApiToken}")
    String apiToken;

    private static final int OUTPUT_BUFFER_SIZE = 1024000; // Установите желаемый размер буфера вывода
    public static final long handlerId = 1;

    @Autowired
    KitchenPrintEntityRepo kitchenPrintEntityRepo;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session.getAttributes());
        System.out.println("Connection established");
        session.setTextMessageSizeLimit(OUTPUT_BUFFER_SIZE);
        session.setBinaryMessageSizeLimit(OUTPUT_BUFFER_SIZE);
//        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println(message.getPayload());
        if (message.getPayload().equals(apiToken)
//                && (WebSocketSessionManager.getSession(handlerId) == null || !WebSocketSessionManager.getSession(handlerId).isOpen())
        ){
            WebSocketSessionManager.removeSession(handlerId);
            WebSocketSessionManager.addSession(session, handlerId);
        }
        else {
            String msg = message.getPayload().toString();
            PrintKitchenDTO printKitchenDTO = new Gson().fromJson(msg, new TypeToken<PrintKitchenDTO>(){}.getType());
            kitchenPrintEntityRepo.deleteByOrderItem(printKitchenDTO.getId());

        }
    }



    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws IOException {
        WebSocketSessionManager.getSession(handlerId).close();
        WebSocketSessionManager.removeSession(handlerId);
        log.error("Transport error: " + exception.getMessage()+ " Handler ID = " + handlerId);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws IOException {
//        WebSocketSessionManager.getSession(handlerId).close();
        WebSocketSessionManager.removeSession(handlerId);
        log.error("Connection closed: " + closeStatus.getReason() + " Handler ID = " + handlerId);
        session.close();
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}