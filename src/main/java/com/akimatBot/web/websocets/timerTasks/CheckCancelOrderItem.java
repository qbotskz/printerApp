package com.akimatBot.web.websocets.timerTasks;

import com.akimatBot.entity.custom.OrderItem;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.web.dto.OrderItemDTO;
import com.akimatBot.web.dto.OrderItemDeleteDTO;
import com.akimatBot.web.dto.PrintKitchenDTO;
import com.akimatBot.web.websocets.WebSocketSessionManager;
import com.akimatBot.web.websocets.entities.KitchenPrintEntityRepo;
import com.akimatBot.web.websocets.entities.OrderItemDeleteEntity;
import com.akimatBot.web.websocets.entities.OrderItemDeleteEntityRepo;
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
public class CheckCancelOrderItem extends TimerTask {

    @Autowired
    OrderItemDeleteEntityRepo orderItemDeleteEntityRepo;

    @Override
    public void run() {
//        List<OrderItemDeleteEntity> orderItemDeleteEntities = orderItemDeleteEntityRepo.findAllByOrderById();
//        for (OrderItemDeleteEntity orderItemDeleteEntity : orderItemDeleteEntities){
//
//            WebSocketSession socketSession = WebSocketSessionManager.getSession(CancelOrderItemPrint.handlerId);
//
//            if (socketSession != null && socketSession.isOpen()) {
//                try {
//                    socketSession.sendMessage(new TextMessage(new Gson().toJson(orderItemDeleteEntity.getDTO())));
//                    orderItemDeleteEntityRepo.delete(orderItemDeleteEntity);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
    }


}
