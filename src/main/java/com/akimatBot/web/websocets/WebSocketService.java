package com.akimatBot.web.websocets;

import com.akimatBot.entity.custom.FoodOrder;
import com.akimatBot.entity.custom.OrderItem;
import com.akimatBot.entity.custom.PrintPrecheck;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.repository.repos.OrderItemRepository;
import com.akimatBot.repository.repos.OrderRepository;
import com.akimatBot.repository.repos.PrintPrecheckRepo;
import com.akimatBot.repository.repos.PropertiesRepo;
import com.akimatBot.utils.DateUtil;
import com.akimatBot.web.dto.OrderItemDeleteDTO;
import com.akimatBot.web.dto.PrintPrecheckDTO;
import com.akimatBot.web.websocets.entities.KitchenPrintEntityRepo;
import com.akimatBot.web.websocets.entities.OrderItemDeleteEntityRepo;
import com.akimatBot.web.websocets.entities.PrintKitchenEntity;
import com.akimatBot.web.websocets.handlers.PaymentPrint;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WebSocketService {

    @Value("${printerApiToken}")
    static String apiToken;

    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    KitchenPrintEntityRepo kitchenPrintEntityRepo;

    @Autowired
    OrderItemDeleteEntityRepo orderItemDeleteEntityRepo;

    @Autowired
    PropertiesRepo propertiesRepo;

    @Autowired
    PrintPrecheckRepo printPrecheckRepo;

    public void sendToPrinter(long orderId) {
        try {
            List<OrderItem> orderItems = orderItemRepository.getAllByOrderInCart(orderId);
            if (orderItems.size() > 0) {


                PrintKitchenEntity printKitchenEntity = new PrintKitchenEntity();
                printKitchenEntity.setItems(orderItems);
                printKitchenEntity.setOrderId(orderId);
                printKitchenEntity.setCreatedDate(orderRepository.getCreatedDateByOrderId(orderId));
                printKitchenEntity.setDeskNumber(orderRepository.getDeskNumberById(orderId));
                printKitchenEntity.setWaiterName(orderRepository.getWaiterNameById(orderId));
                printKitchenEntity.setHallName(orderRepository.getHallNameById(orderId));
                printKitchenEntity = kitchenPrintEntityRepo.save(printKitchenEntity);

//                WebSocketSession socketSession = WebSocketSessionManager.getSession(KitchenPrint.handlerId);
//                if (socketSession!= null && socketSession.isOpen()) {
//                    socketSession.sendMessage(new TextMessage(new Gson().toJson(printKitchenEntity.getDTO())));
//                }

            }


//            Map<String, WebSocketSession> sessionMap = WebSocketSessionManager.sessions;
//            for (Map.Entry<String, WebSocketSession> entry : sessionMap.entrySet()) {
//                entry.getValue().sendMessage(new TextMessage());
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public void printPrecheck(long orderId) {
        try {
            FoodOrder foodOrder = orderRepository.findOrderById(orderId);

            PrintPrecheck printPrecheck = new PrintPrecheck();
            printPrecheck.setPrecheckDate(DateUtil.getDbMmYyyyHhMmSs(new Date()));
            printPrecheck.setFoodOrder(foodOrder);
            printPrecheck.setPrinterName(propertiesRepo.findFirstById(5).getValue1());
            printPrecheck.setWaiterName(foodOrder.getWaiter().getFullName());
            printPrecheck.setHallName(foodOrder.getDesk().getHall().getName());
            printPrecheck.setDeskNumber(foodOrder.getDesk().getNumber());
            printPrecheckRepo.save(printPrecheck);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void printPayment(long orderId) {
        try {
            FoodOrder foodOrder = orderRepository.findOrderById(orderId);
            WebSocketSession socketSession = WebSocketSessionManager.getSession(PaymentPrint.handlerId);

            PrintPrecheckDTO printPrecheckDTO = new PrintPrecheckDTO();
            printPrecheckDTO.setPrecheckDate(DateUtil.getDbMmYyyyHhMmSs(new Date()));
            printPrecheckDTO.setFoodOrder(foodOrder.getFoodOrderDTO(Language.ru));
            printPrecheckDTO.setPrinterName(propertiesRepo.findFirstById(5).getValue1());
            printPrecheckDTO.setWaiterName(foodOrder.getWaiter().getFullName());
            printPrecheckDTO.setHallName(foodOrder.getDesk().getHall().getName());
            printPrecheckDTO.setDeskNumber(foodOrder.getDesk().getNumber());

            if (socketSession != null && socketSession.isOpen()) {
                socketSession.sendMessage(new TextMessage(new Gson().toJson(printPrecheckDTO)));
            } else {
                log.error("Session is closed!!!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printCancelOrderItem(OrderItemDeleteDTO item, OrderItem orderItem) {
        try {
//            WebSocketSession socketSession = WebSocketSessionManager.getSession(CancelOrderItemPrint.handlerId);
//
//            OrderItemDeleteEntity orderItemDeleteEntity = new OrderItemDeleteEntity();
//            orderItemDeleteEntity.setReason(item.getReason());
//            orderItemDeleteEntity.setOrderItem(orderItem);
//            orderItemDeleteEntity.setOrderId(orderItem.getGuest().getFoodOrder().getId());
//            orderItemDeleteEntity.setWaiterName(orderItem.getGuest().getFoodOrder().getWaiter().getFullName());
//            orderItemDeleteEntity.setHallName(orderItem.getGuest().getFoodOrder().getDesk().getHall().getName());
//            orderItemDeleteEntity.setDeskNumber(orderItem.getGuest().getFoodOrder().getDesk().getNumber());
//            orderItemDeleteEntity = orderItemDeleteEntityRepo.save(orderItemDeleteEntity);
//
//
//            //todo переписать из опен
//            if (socketSession != null && socketSession.isOpen()) {
//                socketSession.sendMessage(new TextMessage(new Gson().toJson(orderItemDeleteEntity.getDTO())));
//            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}