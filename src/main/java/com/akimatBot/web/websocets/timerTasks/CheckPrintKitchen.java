package com.akimatBot.web.websocets.timerTasks;

import com.akimatBot.entity.custom.OrderItem;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.web.dto.OrderItemDTO;
import com.akimatBot.web.dto.PrintKitchenDTO;
import com.akimatBot.web.websocets.WebSocketSessionManager;
import com.akimatBot.web.websocets.entities.KitchenPrintEntityRepo;
import com.akimatBot.web.websocets.entities.PrintKitchenEntity;
import com.akimatBot.web.websocets.handlers.KitchenPrint;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

@Service
public class CheckPrintKitchen extends TimerTask {

    @Autowired
    KitchenPrintEntityRepo kitchenPrintEntityRepo;

    @Override
    public void run() {
        List<PrintKitchenEntity> printKitchenEntities = kitchenPrintEntityRepo.findAllByOrderById();
        for (PrintKitchenEntity printKitchenEntity : printKitchenEntities){


            WebSocketSession socketSession = WebSocketSessionManager.getSession(KitchenPrint.handlerId);
            if (socketSession != null && socketSession.isOpen()) {
                try {
                    socketSession.sendMessage(new TextMessage(new Gson().toJson(printKitchenEntity.getDTO())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<OrderItemDTO> getItemsStr(List<OrderItem> orderItems){
        List<OrderItemDTO> dtos = new ArrayList<>();
        for (OrderItem orderItem : orderItems){
            dtos.add(orderItem.getOrderItemDTO(Language.ru));
        }
        return dtos;
    }
}
