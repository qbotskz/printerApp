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
    private final CartItemService cartItemService;
    private final EmployeeService employeeService;

    private final OrderItemRepository orderItemRepository;

    private final DeskRepo deskRepo;

    private final FoodService foodService;

    private final ChequeRepo chequeRepo;
    private final PropertiesRepo propertiesRepo;

    private final GuestRepo guestRepo;

    private final PaymentRepo paymentRepo;

    private final PaymentTypeRepo paymentTypeRepo;

    private final WaiterService waiterService;

    private final WebSocketService webSocketService;

    private final GeneralShiftService generalShiftService;

    private final PrintPrecheckRepo printPrecheckRepo;

    private final OrderItemDeleteEntityRepo orderItemDeleteEntityRepo;


    @Autowired
    public OrderService(OrderRepository orderRepo, CartItemService cartItemService, EmployeeService employeeService,
                        OrderItemRepository orderItemRepository, DeskRepo deskRepo, FoodService foodService,
                        ChequeRepo chequeRepo, PropertiesRepo propertiesRepo, GuestRepo guestRepo, PaymentRepo paymentRepo,
                        PaymentTypeRepo paymentTypeRepo, WaiterService waiterService,WebSocketService webSocketService,
                        GeneralShiftService generalShiftService,PrintPrecheckRepo printPrecheckRepo,
                        OrderItemDeleteEntityRepo orderItemDeleteEntityRepo) {
        this.orderRepo = orderRepo;
        this.cartItemService = cartItemService;
        this.employeeService = employeeService;
        this.orderItemRepository = orderItemRepository;
        this.deskRepo = deskRepo;
        this.foodService = foodService;
        this.chequeRepo = chequeRepo;
        this.propertiesRepo = propertiesRepo;
        this.guestRepo = guestRepo;
        this.paymentRepo = paymentRepo;
        this.paymentTypeRepo = paymentTypeRepo;
        this.waiterService = waiterService;
        this.webSocketService = webSocketService;
        this.generalShiftService = generalShiftService;
        this.printPrecheckRepo = printPrecheckRepo;
        this.orderItemDeleteEntityRepo = orderItemDeleteEntityRepo;
    }

    public ReportDailyWithTaxDTO getReportDailyWithTaxDTO(long code) {
        ReportDailyWithTaxDTO reportDailyWithTaxDTO = new ReportDailyWithTaxDTO();
        GeneralShift generalShift = generalShiftService.getOpenedShift();
        reportDailyWithTaxDTO.setGeneralShift(generalShift.getDTO());
        reportDailyWithTaxDTO.setCurrentEmployee(employeeService.findByCode(code).getDTO());
        reportDailyWithTaxDTO.setTotal(orderRepo.getTotalBetween(generalShift.getOpeningTime(), new Date()));
//        List<PaymentTypeReport> objects = orderRepo.getTotalForPaymentTypesJPQL(generalShift.getOpeningTime(), new Date());
//        reportDailyWithTaxDTO.setPaymentTypeReports(getDTO(orderRepo.getTotalForPaymentTypes(generalShift.getOpeningTime(), new Date())));
        reportDailyWithTaxDTO.setPaymentTypeReports(getDTO(orderRepo.getTotalForPaymentTypesJPQL(generalShift.getOpeningTime(), new Date())));
        return reportDailyWithTaxDTO;
    }
    private List<PaymentTypeReportDTO> getDTO(List<PaymentTypeReport> totalForPaymentTypesJPQL) {

        List<PaymentTypeReportDTO> dtos = new ArrayList<>();
        for (PaymentTypeReport paymentTypeReport : totalForPaymentTypesJPQL){
            dtos.add(paymentTypeReport.getDTO());
        }

        return dtos;
    }

    public FoodOrder findById(long id) {
        return orderRepo.findOrderById(id);
    }

    @Transactional
    public FoodOrder save(FoodOrder foodOrder) {
        return orderRepo.save(foodOrder);
    }

//    public List<FoodOrder> findActiveOrders() {
////        return orderRepo.findAllByDoneFalseAndTicketReceivedTrueOrderByCreatedDateDesc();
////        return orderRepo.findAllByDoneFalseAndPaidTrueOrderByCreatedDateDesc();
//    }

    public List<FoodOrder> findActiveOrdersOrderByDate(){
        return orderRepo.findAllByOrderStatusAndWaiterIsNullOrderByCreatedDate(OrderStatus.DONE);
//        return orderRepo.findAllByDoneFalseAndWaiterIsNullOrderByCreatedDate();
    }
//    public List<FoodOrder> findNonPaidOrders() {
////        return orderRepo.findAllByDoneFalseAndTicketReceivedTrueOrderByCreatedDateDesc();
//        return orderRepo.findAllByPaidIsNullOrderByCreatedDateDesc();
//    }

    @Transactional
    public FoodOrder fillOrderWithCardItemsAndSave(FoodOrder foodOrder, long chatId) {


        List<CartItem> cartItems = cartItemService.findAllCartItemsOfUser(chatId);


        foodOrder = orderRepo.save(foodOrder);
        //todo get guest by chatId
        User user = foodOrder.getClientByChatId(chatId);
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem: cartItems) {
            Food food = cartItem.getFood();
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(food);
            orderItem.setPrice(food.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
//            orderItem.setGuest(foodOrder);

            orderItem = orderItemRepository.save(orderItem);
            orderItems.add(orderItem);

//            foodOrder.getCheque().addTotal(orderItem.getTotal());
        }


//        foodOrder.setOrderItems(orderItems);
//        int totalPrice = orderItems.stream().map(orderItem -> orderItem.getQuantity()*orderItem.getPrice()).reduce(0, Integer::sum);
//        totalPrice = totalPrice + (foodOrder.getDeliverNeed() ? foodOrder.getDeliveryPrice():0);


        if (foodOrder.getCheque().getUseCashback()) {
            double cashBackUsed = user.getCashback();
            if (cashBackUsed > foodOrder.getCheque().getCalculatedTotal()){
                cashBackUsed = foodOrder.getCheque().getCalculatedTotal();
            }

            foodOrder.getCheque().setUsedCashback(cashBackUsed);

            user.setCashback(user.getCashback() - cashBackUsed);
            //todo write correctly save
//            userService.save(user);

        }
//        else {
//            foodOrder.setUsedCashback(0);
//        }



//        foodOrder.setTotal(totalPrice);

        foodOrder.setCreatedDate(new Date());
//        order.setTicketReceived(false);
        orderRepo.save(foodOrder);
//        cartItemService.clearUserCart(chatId);

        return foodOrder;




    }


//    @Transactional
//    public FoodOrder fillOrderWithCardItemsAndSaveFromWaiter(FoodOrder foodOrder, long deskId) {
//
//
//        List<CartItem> cartItems = cartItemService.findAllCartItemsOfTable(deskId);
//
//
//        foodOrder = orderRepo.save(foodOrder);
//
//        List<OrderItem> orderItems = new ArrayList<>();
//
//        for (CartItem cartItem: cartItems) {
//            Food food = cartItem.getFood();
//            OrderItem orderItem = new OrderItem();
//            orderItem.setFood(food);
//            orderItem.setPrice(food.getPrice());
//            orderItem.setQuantity(cartItem.getQuantity());
//            orderItem.setFoodOrder(foodOrder);
//
//            orderItem = orderItemRepository.save(orderItem);
//            orderItems.add(orderItem);
//        }
//
//
//        foodOrder.setOrderItems(orderItems);
//        int totalPrice = orderItems.stream().map(orderItem -> orderItem.getQuantity()*orderItem.getPrice()).reduce(0, Integer::sum);
//        totalPrice = totalPrice + (foodOrder.getDeliverNeed() ? foodOrder.getDeliveryPrice():0);
//
//        foodOrder.setUsedCashback(0);
//
//
//        foodOrder.setTotal(totalPrice);
//
//        foodOrder.setCreatedDate(new Date());
//        orderRepo.save(foodOrder);
//
//        return foodOrder;
//
//    }
    @Transactional // method for client //todo переделать
    public FoodOrder reOrder(FoodOrder foodOrder, long chatId) {

        List<CartItem> cartItems = cartItemService.findAllCartItemsOfUser(chatId);


        foodOrder = orderRepo.save(foodOrder);
//        User user = foodOrder.getClient();
        Set<OrderItem> orderItems = new HashSet<>();

        for (CartItem cartItem: cartItems) {
            Food food = cartItem.getFood();
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(food);
            orderItem.setPrice(food.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
//            orderItem.setFoodOrder(foodOrder);

            orderItem = orderItemRepository.save(orderItem);
            orderItems.add(orderItem);

//            foodOrder.getCheque().addTotal(orderItem.getTotal());
        }


//        foodOrder.addOrderItems(orderItems);

//        int totalPrice = orderItems.stream().map(orderItem -> orderItem.getQuantity()*orderItem.getPrice()).reduce(0, Integer::sum);
//        totalPrice = totalPrice + (foodOrder.getDeliverNeed() ? foodOrder.getDeliveryPrice():0);






//        foodOrder.addTotal(totalPrice);

//        orders.setCreatedDate(new Date());
//        order.setTicketReceived(false);
        orderRepo.save(foodOrder);
//        cartItemService.clearUserCart(chatId);

        return foodOrder;




    }

//    @Transactional
//    public FoodOrder reOrderFromWaiter(FoodOrder order, long deskId) {
//
//        List<CartItem> cartItems = cartItemService.findAllCartItemsOfTable(deskId);
//
//
//        order = orderRepo.save(order);
//        Set<OrderItem> orderItems = new HashSet<>();
//
//        for (CartItem cartItem: cartItems) {
//            Food food = cartItem.getFood();
//            OrderItem orderItem = new OrderItem();
//            orderItem.setFood(food);
//            orderItem.setPrice(food.getPrice());
//            orderItem.setQuantity(cartItem.getQuantity());
//            orderItem.setFoodOrder(order);
//
//            orderItem = orderItemRepository.save(orderItem);
//            orderItems.add(orderItem);
//        }
//
//
//        order.addOrderItems(orderItems);
//        int totalPrice = orderItems.stream().map(orderItem -> orderItem.getQuantity()*orderItem.getPrice()).reduce(0, Integer::sum);
//        totalPrice = totalPrice + (order.getDeliverNeed() ? order.getDeliveryPrice():0);
//
//
//
//        order.addTotal(totalPrice);
//
//        orderRepo.save(order);
//
//        return order;
//
//
//
//
//    }



//    @Transactional
//    public Orders reOrderFromWaiter(Orders orders, long deskId) {
//
//        List<CartItem> cartItems = cartItemService.findAllCartItemsOfTable(deskId);
//
//
//        orders = orderRepo.save(orders);
//        Set<OrderItem> orderItems = new HashSet<>();
//
//        for (CartItem cartItem: cartItems) {
//            Food food = cartItem.getFood();
//            if(!orders.hasFood(food)) {
//                OrderItem orderItem = new OrderItem();
//                orderItem.setFood(food);
//                orderItem.setPrice(food.getPrice());
//                orderItem.setQuantity(cartItem.getQuantity());
//                orderItem.setOrders(orders);
//
//                orderItem = orderItemRepository.save(orderItem);
//                orderItems.add(orderItem);
//            }
//            else {
//                orders.addQuantiry(1);
//            }
//        }
//
//        orders.addOrderItems(orderItems);
//        int totalPrice = orderItems.stream().map(orderItem -> orderItem.getQuantity()*orderItem.getPrice()).reduce(0, Integer::sum);
//        totalPrice = totalPrice + (orders.getDeliverNeed() ? orders.getDeliveryPrice():0);
//
//
//        orders.addTotal(totalPrice);
//
//        orderRepo.save(orders);
//
//        return orders;
//
//
//
//
//    }



//    public List<Order> findAllOrdersOfUser(String chatId) {
//
//        return orderRepo.getActuallyOrders(chatId, PaymTech.orderTimeout);
////        return orderRepo.findAllByUser_ChatId(chatId);
//    }

//    @Transactional
//    public void orderPaid(FoodOrder foodOrder) {
//        User user = foodOrder.getClient();
////        user.setCashback(user.getCashback() - foodOrder.getUsedCashback());
//        userService.save(user);
////        foodOrder.setPaid(true);
//        save(foodOrder);
//    }

//    @Transactional
//    public void cancelOrder(FoodOrder foodOrder) {
////        foodOrder.setPaid(false);
//        this.save(foodOrder);
//    }

    public FoodOrder getActiveOrderInRestaurant(long chatId) {
        return orderRepo.getLastClientOrder(chatId, OrderType.in_the_restaurant);
//        return null;
    }

    public List<FoodOrder> getActiveOrdersOfWaiter(long chatId) {
//        return orderRepo.findAllByWaiterChatIdAndDoneIsFalse(chatId);
        return orderRepo.findAllByWaiterChatIdAndOrderStatusNot(chatId, OrderStatus.DONE);
    }

    public List<FoodOrder> getActiveOrdersOfWaiterByCode(long code) {
        return orderRepo.findAllByWaiterCodeAndOrderStatusNot(code, OrderStatus.DONE);
    }

    public List<FoodOrder> getDoneOrdersOfWaiter(long chatId) {
        return orderRepo.getDoneOrdersOfWaiterByChatId(chatId);
    }

    public FoodOrder getActiveOrderByDesk(long deskId) {
//        return orderRepo.findLastByDeskIdAndOrderTypeAndDoneIsFalse(deskId, OrderType.in_the_restaurant);
        return orderRepo.findLastByDeskIdAndOrderTypeAndOrderStatusNot(deskId, OrderType.in_the_restaurant, OrderStatus.DONE);
    }

    @Transactional
    public FoodOrder closeOrderOfTable(FoodOrder foodOrder) {
//        foodOrder.setDone(true);
        orderRepo.save(foodOrder);
        return foodOrder;
    }

    @Transactional // method for waiter
    public AnswerAddToCartDTO addToCart(long foodId, long guestId, Language language) {

        Guest guest = guestRepo.getOne(guestId);
        OrderItem orderItem = orderItemRepository.findByFoodIdAndGuestIdAndOrderItemStatus(foodId, guestId ,OrderItemStatus.IN_CART);

        if (orderItem != null){
            orderItem.addQuantity();
            orderItemRepository.save(orderItem);
        }
        else {
            Food food = foodService.findById(foodId);

            orderItem = new OrderItem();
            orderItem.setOrderItemStatus(OrderItemStatus.IN_CART);
            orderItem.setCreatedDate(new Date());
            orderItem.setQuantity(1);
            orderItem.setPrice(food.getPrice());
            orderItem.setGuest(guest);
            orderItem.setFood(food);
            orderItem = orderItemRepository.save(orderItem);
        }

//        chequeRepo.addTotal(orderItem.getId());
//        Cheque cheque1e = orderItem.getGuest().getFoodOrder().getCheque();
//        cheque1e.addTotal(orderItem.getPrice());
//        chequeRepo.save(cheque1e);

        AnswerAddToCartDTO answerAddToCartDTO = new AnswerAddToCartDTO();
        answerAddToCartDTO.setOrderItem(orderItem.getOrderItemDTO(language));
        Cheque cheque = chequeRepo.getByGuest(guestId);
        answerAddToCartDTO.setCheque(cheque.getChequeDTO());

        return answerAddToCartDTO;
    }


    @Transactional // method for waiter
    public OrderItem addToCartMobile(long foodId, long guestId) {

        Guest guest = guestRepo.getOne(guestId);

        OrderItem orderItem = orderItemRepository.findByFoodIdAndGuestIdAndOrderItemStatus(foodId, guestId ,OrderItemStatus.IN_CART);
        if (orderItem != null){
            orderItem.addQuantity();
        }
        else {
            Food food = foodService.findById(foodId);

            orderItem = new OrderItem();
            orderItem.setOrderItemStatus(OrderItemStatus.IN_CART);
            orderItem.setCreatedDate(new Date());
            orderItem.setQuantity(1);
            orderItem.setPrice(food.getPrice());
            orderItem.setGuest(guest);
            orderItem.setFood(food);
        }
        orderItem = orderItemRepository.save(orderItem);

//        Cheque cheque1e = orderItem.getGuest().getFoodOrder().getCheque();
//        cheque1e.addTotal(orderItem.getPrice());
//        chequeRepo.save(cheque1e);

        return orderItem;
    }


    @Transactional
    public FoodOrder createEmptyOrder(long deskId, long code) {


        Desk desk = deskRepo.findById(deskId);

        FoodOrder foodOrder = desk.getCurrentOrder();
        if (foodOrder == null){
            foodOrder = new FoodOrder();
            foodOrder.setCreatedDate(new Date());
            foodOrder.setWaiter(employeeService.findByCode(code));
            foodOrder.setDesk(desk);
            foodOrder.setOrderType(OrderType.in_the_restaurant);
            foodOrder.setOrderStatus(OrderStatus.ACTIVE);

            Cheque cheque = new Cheque();
            cheque.setService(Double.parseDouble(propertiesRepo.findFirstById(4).getValue1()));
            cheque = chequeRepo.save(cheque);

            foodOrder.setCheque(cheque);
        }



//        foodOrder.addGuest(guest);


        foodOrder = orderRepo.save(foodOrder);

        desk.setCurrentOrder(foodOrder);
        deskRepo.save(desk);

        Guest guest = new Guest();
        guest.setFoodOrder(foodOrder);
        guest.setCreatedDate(new Date());
        guestRepo.save(guest);

        foodOrder.addGuest(guest);
        return foodOrder;
    }

    @Transactional
    public FoodOrder createEmptyOrderByChatId(long deskId, long chatId) {


        Desk desk = deskRepo.findById(deskId);
        FoodOrder foodOrder = desk.getCurrentOrder();

//        FoodOrder foodOrder = getActiveOrderByDesk(deskId);

        if (foodOrder == null){
            foodOrder = new FoodOrder();
            foodOrder.setCreatedDate(new Date());
            foodOrder.setWaiter(employeeService.findByChatId(chatId));
            foodOrder.setDesk(desk);
            foodOrder.setOrderType(OrderType.in_the_restaurant);
            foodOrder.setOrderStatus(OrderStatus.ACTIVE);

            Cheque cheque = new Cheque();
            cheque.setService(Double.parseDouble(propertiesRepo.findFirstById(4).getValue1()));
            cheque = chequeRepo.save(cheque);

            foodOrder.setCheque(cheque);
        }



//        foodOrder.addGuest(guest);


        foodOrder = orderRepo.save(foodOrder);

        desk.setCurrentOrder(foodOrder);
        deskRepo.save(desk);

        Guest guest = new Guest();
        guest.setFoodOrder(foodOrder);
        guest.setCreatedDate(new Date());
        guestRepo.save(guest);

        foodOrder.addGuest(guest);
        return foodOrder;
    }

    @Transactional
    public Guest createGuest(long orderId) {

        FoodOrder foodOrder = findById(orderId);


        Guest guest = new Guest();
        guest.setFoodOrder(foodOrder);
        guest.setCreatedDate(new Date());
        guestRepo.save(guest);
        return guest;
    }

    public List<FoodOrder> findActiveOrdersOfDesk(long deskId) {
//        return orderRepo.findAllByDeskIdAndDoneIsFalse(deskId);
        return orderRepo.findAllByDeskIdAndOrderStatus(deskId, OrderStatus.DONE);
    }


    //TODO check this method
    public FoodOrder createOrderTakeOut(long chatId, boolean useCashback, boolean deliverNeed, String address, String fullName, String phoneNumber) {

        Employee user = employeeService.findByChatId(chatId);
        user.setPhone(phoneNumber);
        user.setFullName(fullName);
        employeeService.save(user);

        FoodOrder foodOrder = new FoodOrder();
//        foodOrder.setClient(user);
        foodOrder.setDeliverNeed(deliverNeed);
        foodOrder.setOrderType(deliverNeed?OrderType.courier:OrderType.takeout);
        foodOrder.setAddress(address);


        Cheque cheque = new Cheque();
        if (deliverNeed) {
            cheque.setDeliveryPrice(Integer.parseInt(propertiesRepo.findFirstById(3).getValue1()));
        }

        cheque.setUseCashback(useCashback);

        foodOrder = fillOrderWithCardItemsAndSave(foodOrder, chatId);

        return foodOrder;

    }


    @Transactional
    public Cheque editOrderItemCount(OrderItem orderItem, int count, String comment) {
        if (count >= 0 ) {

            Cheque cheque = orderItem.getGuest().getFoodOrder().getCheque();
            int price =  orderItem.getPrice();
            int quan =  orderItem.getQuantity();
            int forMinus = price * (count - quan);

//            cheque.addTotal(forMinus);
//            chequeRepo.save(cheque);

            if (count == 0) {
                orderItemRepository.setDelete(orderItem);
            }
            else {
                if (comment != null) {
                    orderItem.setComment(comment);
                }
                orderItem.setQuantity(count);
                orderItemRepository.save(orderItem);
            }

            return cheque;
        }
        return null;
    }

    @Transactional
    public void deleteOrderItem(OrderItem orderItem) {

//        FoodOrder foodOrder = orderItem.getGuest().getFoodOrder();
//        Cheque cheque = foodOrder.getCheque();
//        cheque.addTotal(-(orderItem.getTotal()));
//        chequeRepo.save(cheque);


        orderItemRepository.setDelete(orderItem);
    }


    @Transactional
    public OrderItem cut(long orderItemId, int quantity, long toGuestId) {
        OrderItem orderItem = orderItemRepository.getOne(orderItemId);
        if (orderItem.getQuantity() >= quantity){


            FoodOrder oldOrder = orderItem.getGuest().getFoodOrder();
            oldOrder.minusItem(orderItem, quantity);

            Guest guest = guestRepo.getOne(toGuestId);

            OrderItem orderItem1 = new OrderItem();
            orderItem1.setPrice(orderItem.getPrice());
            orderItem1.setFood(orderItem.getFood());
            orderItem1.setQuantity(quantity);
            orderItem1.setOrderItemStatus(orderItem.getOrderItemStatus());
            orderItem1.setCreatedDate(new Date());
            orderItem1.setGuest(guest);
            orderItemRepository.save(orderItem1);

            return orderItem1;
        }
        return null;
    }

    @Transactional
    public DeskDTO cut(long orderItemId, int quantity, long toGuestId, long chatId) {
        OrderItem orderItem = orderItemRepository.getOne(orderItemId);
        if (orderItem.getQuantity() >= quantity){


            FoodOrder oldOrder = orderItem.getGuest().getFoodOrder();
            oldOrder.minusItem(orderItem, quantity);

            Guest guest = guestRepo.getOne(toGuestId);

            OrderItem orderItem1 = new OrderItem();
            orderItem1.setPrice(orderItem.getPrice());
            orderItem1.setFood(orderItem.getFood());
            orderItem1.setQuantity(quantity);
            orderItem1.setOrderItemStatus(orderItem.getOrderItemStatus());
            orderItem1.setCreatedDate(new Date());
            orderItem1.setGuest(guest);
            orderItemRepository.save(orderItem1);

//            Cheque cheque = guest.getFoodOrder().getCheque();
//            cheque.addTotal(orderItem.getTotal());
//            chequeRepo.save(cheque);

            return oldOrder.getDesk().getDeskDTOFull(Language.ru, chatId);

        }
        else return null;
    }

    public boolean hasNotDoneOfWaiter(long code) {
//        return orderRepo.existsByWaiterChatIdAndDoneIsFalse(chatId);
        return orderRepo.countActiveOrders(code) == 0;
    }
    public boolean hasNotDoneOfWaiterByChatId(long chatId) {
//        return orderRepo.existsByWaiterChatIdAndDoneIsFalse(chatId);
        return orderRepo.countActiveOrdersByChatId(chatId) == 0;
    }

    public boolean hasNotDoneOfAll() {
//        return orderRepo.existsByWaiterChatIdAndDoneIsFalse(chatId);
        return orderRepo.existsByOrderStatusNot(OrderStatus.DONE);
    }

    @Transactional
    public FoodOrder precheck(long orderId) {
        if (!orderRepo.isEmpty(orderId)) {

            FoodOrder foodOrder = this.findById(orderId);
            foodOrder.setOrderStatus(OrderStatus.PRECHECK);

            Double excess = orderItemRepository.getTotalInCartsOfOrder(orderId);
            if (excess != null) {
                Cheque cheque = foodOrder.getCheque();
//                cheque.minusTotal(excess);
//                cheque = chequeRepo.save(cheque);
                foodOrder.setCheque(cheque);
            }


            orderItemRepository.deleteInCartsOfOrder(orderId);
            webSocketService.printPrecheck(orderId);
            this.save(foodOrder);
            return foodOrder;
        }
        return null;
    }

    @Transactional
    public FoodOrder doNotPrecheck(long orderId) {

        printPrecheckRepo.setCancelled(orderId, new Date());

        FoodOrder foodOrder = this.findById(orderId);
        foodOrder.setOrderStatus(OrderStatus.ACTIVE);
        this.save(foodOrder);
        return foodOrder;
    }

    @Transactional
    public void delete(FoodOrder currentOrder) {
        orderRepo.delete(currentOrder.getId());
    }


    @Transactional
    public Cheque deletePaymnet(PaymentDTO paymentDTO) {

        Payment payment = paymentRepo.findById(paymentDTO.getId());
        Cheque cheque = payment.getCheque();
        paymentRepo.delete(payment);

        cheque.calculate();
        chequeRepo.save(cheque);

        return cheque;

    }

    @Transactional
    public Cheque addPaymnetToCheque(long chequeId, long paymentTypeId, double amount) {
        Cheque cheque = chequeRepo.findById(chequeId);
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setCheque(cheque);
        payment.setPaymentType(paymentTypeRepo.findById(paymentTypeId));

        paymentRepo.save(payment);
        cheque.calculate();
        chequeRepo.save(cheque);

        return cheque;
    }

    @Transactional
    public boolean pay(ChequeDTO chequeDTO) {
        Cheque cheque = chequeRepo.findById(chequeDTO.getId());
//        cheque.setCalculatedTotal();
//        chequeRepo.save(cheque);
        if (cheque.getForPayment() == 0) {
            FoodOrder foodOrder = cheque.getOrder();
            foodOrder.setCompletionDate(new Date());
            foodOrder.setOrderStatus(OrderStatus.DONE);
            deskRepo.setCurrentOrderNull(cheque.getOrder().getDesk().getId());
            this.save(foodOrder);
            webSocketService.printPayment(foodOrder.getId());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean cookOrder(long orderId)
    {
        Boolean isAv = orderItemRepository.isAvailableRemains(orderId);
        if (isAv == null || isAv) {
            webSocketService.sendToPrinter(orderId);
            orderItemRepository.editQuantity(orderId);
            orderItemRepository.cookItems(OrderItemStatus.COOK.getId(), orderId, OrderItemStatus.IN_CART.getId());
//            chequeRepo.setTotal(orderId);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteGuest(GuestDTO guestDTO) {
        Guest guest = guestRepo.getOne(guestDTO.getId());
        Desk currentDesk = guest.getFoodOrder().getDesk();
        FoodOrder currentOrder = guest.getFoodOrder();
        if (guestRepo.getOrderItemsSize(guestDTO.getId()) == 0){
            guestRepo.delete(guest.getId());
            if (currentOrder.getGuestsSize() == 0){
                currentDesk.setCurrentOrder(null);
                deskRepo.save(currentDesk);
                delete(currentOrder);
            }
            return  true;
        }
        else return false;
    }

    @Transactional
    public Cheque editDiscount(long chequeId, double discount) throws Exception {
        if (discount >=0 && discount <= 100) {
            Cheque cheque = chequeRepo.findById(chequeId);
            cheque.setDiscount(discount);
            return chequeRepo.save(cheque);
        }
        else throw new Exception();

    }
    @Transactional
    public Cheque editService(long chequeId, double service) throws Exception {
        if (service >=0 && service <= 100) {
            Cheque cheque = chequeRepo.findById(chequeId);
            cheque.setService(service);
            return chequeRepo.save(cheque);
        }
        throw new Exception();
    }
    @Transactional
    public Cheque editPrepayment(long chequeId, double prepayment){
        Cheque cheque = chequeRepo.findById(chequeId);
//        cheque.setPrepayment(prepayment);
        return chequeRepo.save(cheque);
    }

    public OrderStatus getOrderStatus(long orderId) {
        return orderRepo.getStatusOfOrderByOrderId(orderId);
    }

    @Transactional
    public boolean changeDesk(long orderId, long toDeskId) {
        //проверить что стол пустой done
        //проверить что стол не тот который уже стоит
        //проверить что стол существуетdone
        //проверить что ордер сущесвует
        if(deskRepo.existsAndFree(toDeskId)){
            deskRepo.setCurrentOrderNull(orderRepo.getDeskIdOfOrder(orderId));
            deskRepo.setCurrentOrder(orderId ,toDeskId);
            orderRepo.changeDesk(orderId, toDeskId);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changeWaiter(long orderId, long toWaiterId) {
        //проверить что у официанта открыта смена
        if (waiterService.isOpenShiftById(toWaiterId)){
            orderRepo.changeWaiter(orderId, toWaiterId);
            return true;
        }
        return false;
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

    public boolean cancelOrderItem(OrderItemDeleteDTO item) {
        OrderItem orderItem1 = orderItemRepository.getOne(item.getOrderItem().getId());
        if(!orderItem1.getOrderItemStatus().equals(OrderItemStatus.DELETED)) {
            if (orderItem1.getOrderItemStatus().equals(OrderItemStatus.IN_CART)) {
                this.deleteOrderItem(orderItem1);
                return true;
            } else if (item.getReason() != null) {
                //todo create dangerous zone
                webSocketService.printCancelOrderItem(item, orderItem1);
                deleteOrderItem(orderItem1);
                return true;

            }
        }
        return false;

    }

    public ReportDailyDTO getReportDaily(long code) {
        ReportDailyDTO reportDailyDTO = new ReportDailyDTO();
        GeneralShift generalShift = generalShiftService.getOpenedShift();
        reportDailyDTO.setGeneralShift(generalShift.getDTO());
        reportDailyDTO.setCurrentEmployee(employeeService.findByCode(code).getDTO());
        reportDailyDTO.setOrderTotal(orderRepo.getTotalBetween(generalShift.getOpeningTime(), new Date()));
        reportDailyDTO.setOrderQuantity(orderRepo.getQuantityBetween(generalShift.getOpeningTime(), new Date()));
        return reportDailyDTO;
    }


//    public DangerousOperationsDTO getDangerousOperations() {
//        GeneralShift generalShift = generalShiftService.getOpenedShift();
//
//        DangerousOperationsDTO dangerousOperationsDTO = new DangerousOperationsDTO();
//        dangerousOperationsDTO.setPrechecks(getPrechecksDTO(printPrecheckRepo.findAllByCancelPrecheckDateBetween(generalShift.getOpeningTime(), new Date())));
//        dangerousOperationsDTO.setOrderItems(getItemsDTOS(orderItemDeleteEntityRepo.findAllByDateBetweenOrderById(generalShift.getOpeningTime(), new Date())));
//
//        return dangerousOperationsDTO;
//    }

    private List<OrderItemDeleteDTO> getItemsDTOS(List<OrderItemDeleteEntity> orderItemDeleteEntities) {
        List<OrderItemDeleteDTO> dtos = new ArrayList<>();
        for (OrderItemDeleteEntity entity : orderItemDeleteEntities){
            dtos.add(entity.getDTO());
        }
        return dtos;
    }

    private List<PrintPrecheckDTO> getPrechecksDTO(List<PrintPrecheck> prechecks) {
        List<PrintPrecheckDTO> dtos = new ArrayList<>();
        for (PrintPrecheck printPrecheck :prechecks ){
            dtos.add(printPrecheck.getDTO());
        }
        return dtos;
    }
}
