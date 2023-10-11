package com.akimatBot.services;

import com.akimatBot.entity.custom.*;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.enums.OrderItemStatus;
import com.akimatBot.entity.enums.OrderStatus;
import com.akimatBot.entity.enums.OrderType;
import com.akimatBot.entity.standart.Employee;
import com.akimatBot.entity.standart.User;
import com.akimatBot.repository.repos.*;
import com.akimatBot.web.dto.*;
import com.akimatBot.web.websocets.WebSocketService;
import com.akimatBot.web.websocets.entities.OrderItemDeleteEntity;
import com.akimatBot.web.websocets.entities.OrderItemDeleteEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepo;


    @Autowired
    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    public FoodOrder findById(long id) {
        return orderRepo.findOrderById(id);
    }

    @Transactional
    public FoodOrder save(FoodOrder foodOrder) {
        return orderRepo.save(foodOrder);
    }

    @Transactional
    public void delete(FoodOrder currentOrder) {
        orderRepo.delete(currentOrder.getId());
    }

    public OrderStatus getOrderStatus(long orderId) {
        return orderRepo.getStatusOfOrderByOrderId(orderId);
    }


    public boolean hisOrder(Long code, long orderId) {
        return orderRepo.hisOrder(code, orderId);
    }

    public boolean hisOrderByChequeId(Long code, long chequeId) {
        return orderRepo.hisOrderByChequeId(code, chequeId);
    }

    public boolean hisOrderByGuestId(Long code, long guestId) {
        return orderRepo.hisOrderByGuestId(code, guestId);
    }

    public boolean hisOrderByOrderItemId(Long code, long orderItemId) {
        return orderRepo.hisOrderByOrderItemId(code, orderItemId);

    }
}
