package com.akimatBot.web.controllers.client;

import com.akimatBot.entity.custom.FoodOrder;
import com.akimatBot.repository.repos.ChequeRepo;
import com.akimatBot.repository.repos.MessageRepository;
import com.akimatBot.repository.repos.OrderRepository;
import com.akimatBot.repository.repos.PropertiesRepo;
import com.akimatBot.services.CartItemService;
import com.akimatBot.services.EmployeeService;
import com.akimatBot.services.OrderService;
import com.akimatBot.web.dto.AnswerDTO;
import com.akimatBot.web.dto.FoodOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    MessageRepository messageRepo;


    @Autowired
    CartItemService cartItemService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PropertiesRepo propertiesRepo;

    @Autowired
    OrderService orderService;

    @Autowired
    ChequeRepo chequeRepo;


    //method for client // todo overwrite
    @PostMapping("/createOrder/inRestaurant")
    public FoodOrderDTO createOrderRest(@RequestParam("chatId") long chatId
    ) {

//        User user = userService.findByChatId(chatId);
////        user.setPhone(phoneNumber);
////        user.setFullName(fullName);
//        userService.save(user);
//
//        FoodOrder foodOrder = orderService.getActiveOrderInRestaurant(chatId);
//        if (foodOrder == null) {
//            foodOrder = new FoodOrder();
////            foodOrder.setClient(user);
//            foodOrder.setOrderType(OrderType.in_the_restaurant);
//
//            //todo add service, discount etc
//            Cheque cheque = new Cheque();
//            cheque = chequeRepo.save(cheque);
//
//            foodOrder.setCheque(cheque);
//
//            foodOrder = orderService.fillOrderWithCardItemsAndSave(foodOrder, chatId);
//            cartItemService.clearUserCart(chatId);
//        }
//        else {
//            foodOrder = orderService.reOrder(foodOrder, chatId);
//
//            cartItemService.clearUserCart(chatId);
//        }
//        return foodOrder.getFoodOrderDTO(user.getLanguage(), null);
        return null;
    }

    @GetMapping("/getOrder/active")
    public ResponseEntity<Object> getActiveOrder(@RequestParam("chatId") long chatId) {
        FoodOrder order = orderService.getActiveOrderInRestaurant(chatId);
        if (order != null) {
            return new ResponseEntity<>(order.getFoodOrderDTO(employeeService.getLanguageByChatId(chatId), null),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new AnswerDTO("order is empty").getJson(),
                    HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/activeOrders")
//    public List<FoodOrderDTO> getActiveOrders(){
//        List<Map<Object, Object>>  orders = new ArrayList<>();
//        for (FoodOrder order:orderService.findActiveOrdersOrderByDate()){
//            orders.add(order.getFoodOrderDTO(userService.getLanguageByChatId(createOrder())));
//        }
//
//        return orders;
//    }


    @PostMapping("/createOrder/inRestaurant/reOrder")
    public FoodOrderDTO reOrderRest(@RequestParam("chatId") long chatId) {

//        User user = userService.findByChatId(chatId);
//        userService.save(user);
//
//        FoodOrder foodOrder = orderService.getActiveOrderInRestaurant(chatId);
//
//        foodOrder = orderService.fillOrderWithCardItemsAndSave(foodOrder, chatId);
//        cartItemService.clearUserCart(chatId);
//
//        return foodOrder.getFoodOrderDTO(user.getLanguage(), null);
        return null;
    }


    //method for client // todo overwrite
    @PostMapping("/createOrder/takeout")
    public FoodOrderDTO createOrder(@RequestParam("chatId") long chatId,
                                    @RequestParam("useCashback") boolean useCashback,
                                    @RequestParam("deliverNeed") boolean deliverNeed,
                                    @RequestParam("address") String address,
                                    @RequestParam("fullName") String fullName,
                                    @RequestParam("phoneNumber") String phoneNumber) {

//        User user = userService.findByChatId(chatId);
//        user.setPhone(phoneNumber);
//        user.setFullName(fullName);
//        userService.save(user);
//
//        FoodOrder foodOrder = new FoodOrder();
////        foodOrder.setClient(user);
////        foodOrder.setUseCashback(useCashback);
//        foodOrder.setDeliverNeed(deliverNeed);
//        foodOrder.setOrderType(deliverNeed?OrderType.courier:OrderType.takeout);
////        order.setDeliveryPrice(deliverNeed?Integer.parseInt(propertiesRepo.findFirstById(3).getValue1()):0);
////        foodOrder.setDeliveryPrice(deliverNeed?1500:0);
//
//        foodOrder.setAddress(address);
//
//        Cheque cheque = new Cheque();
//        cheque.setUseCashback(useCashback);
//        cheque.setDeliveryPrice(deliverNeed?Integer.parseInt(propertiesRepo.findFirstById(3).getValue1()):0);
//
//        foodOrder = orderService.fillOrderWithCardItemsAndSave(foodOrder, chatId);
//        sendPayPage(foodOrder);
//        return foodOrder.getFoodOrderDTO(user.getLanguage(), null);

        return null;
    }


//    @PostMapping("/closeOrder")
//    public Map<Object, Object> closeOrder(@RequestParam("chatId") long chatId){
//        FoodOrder foodOrder = orderRepo.getLastClientOrder(chatId, OrderType.in_the_restaurant);
//        if(foodOrder != null) {
//            return orderService.closeOrderOfTable(foodOrder).getJson();
//        }
//        else {
//            return new AnswerDTO("order is not found").getJson();
//        }
//    }

    protected void sendPayPage(FoodOrder foodOrder) {
//        PaymTech paymTech = new PaymTech();
//        Long pOrderId;
//        if (order.getPaymTechOrderId() == null)
//            pOrderId = paymTech.createOrder(order.getTotal(), order.getId(), order.getUser().getChatId());
//        else pOrderId = order.getPaymTechOrderId();
//
//        if (pOrderId != null){
//            order.setPaymTechOrderId(pOrderId);
//            orderService.save(order);
//        }
    }
}
