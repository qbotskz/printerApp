package com.akimatBot.web.websocets.timerTasks;

import com.akimatBot.web.websocets.entities.OrderItemDeleteEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
