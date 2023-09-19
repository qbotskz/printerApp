package com.akimatBot.web.controllers.mobile;

import com.akimatBot.entity.custom.*;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.enums.OrderItemStatus;
import com.akimatBot.entity.standart.Employee;
import com.akimatBot.repository.repos.*;
import com.akimatBot.services.*;
import com.akimatBot.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController()
@RequestMapping("/api/waiter")
@PreAuthorize("@permissionEvaluator.isWaiterByChatId()")
public class WaiterController {

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
    DeskRepo deskRepo;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ChequeRepo chequeRepo;

    @Autowired
    GuestRepo guestRepo;

    @Autowired
    WaiterShiftService waiterShiftService;

    @Autowired
    WaiterService waiterService;

    @Autowired
    PaymentMethodRepo paymentMethodRepo;

    @Autowired
    PasswordEncoder encoder;


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId) && " +
            "@permissionEvaluator.isActiveOrderByOrderId(#orderId)")
    @PostMapping("/cookOrder")
    public ResponseEntity<Object> createOrderRest(
            @RequestParam("orderId") long orderId,
            @RequestHeader(value = "chatId") long chatId
    ) {
        try {
            if (orderService.cookOrder(orderId)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>("Check remains!!!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/eatOrderItem")
    public Boolean eatOrderItem(@RequestParam("orderItemId") long orderItemId,
                                @RequestHeader(value = "chatId") long chatId
    ) {

        try {


            OrderItem orderItem = orderItemRepository.getOne(orderItemId);
            if (orderItem.getOrderItemStatus().equals(OrderItemStatus.COOK)) {
                orderItem.setOrderItemStatus(OrderItemStatus.EAT);
            } else if (orderItem.getOrderItemStatus().equals(OrderItemStatus.EAT)) {
                orderItem.setOrderItemStatus(OrderItemStatus.COOK);

            }
            orderItemRepository.save(orderItem);

            return orderItem.getOrderItemStatus().equals(OrderItemStatus.EAT);
        } catch (Exception e) {
            return null;
        }

    }


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/editDiscount")
    public ResponseEntity<Object> editDiscount(@RequestParam("chequeId") long chequeId,
                                               @RequestParam("discount") double discount,
                                               @RequestHeader(value = "chatId") long chatId
    ) {

        try {
            return new ResponseEntity<>(orderService.editDiscount(chequeId, discount).getChequeDTO(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }

    }


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/editService")
    public ResponseEntity<Object> editService(@RequestParam("chequeId") long chequeId,
                                              @RequestParam("service") double service,
                                              @RequestHeader(value = "chatId") long chatId
    ) {
        try {
            return new ResponseEntity<>(orderService.editService(chequeId, service).getChequeDTO(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/orderItem/editQuantity") // returns desk full json
    public ResponseEntity<Object> editCountOfOrderItem(@RequestParam("orderItemId") long orderItemId,
                                                       @RequestParam("quantity") int quantity,
                                                       @RequestParam(value = "comment", required = false) String comment,
                                                       @RequestHeader(value = "chatId") long chatId
    ) {
        try {
            OrderItem orderItem = orderItemRepository.getOne(orderItemId);
            if (orderItem.getOrderItemStatus().equals(OrderItemStatus.IN_CART) && quantity < 100) {
                orderService.editOrderItemCount(orderItem, quantity, comment);
                return new ResponseEntity<>(orderItem.getGuest().getFoodOrder().getDesk().getDeskDTOFullByChatId(Language.ru, chatId),
                        HttpStatus.OK);
            }
            return new ResponseEntity<>("Order item is not in the cart", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//            return new AnswerDTO("Error!").getJson();
        }

    }


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId) " +
            "&& @permissionEvaluator.isActiveOrderByOrderItem(#item.orderItem.id)")
    @PostMapping("/orderItem/delete")
    public Object editCountOfOrderItem(
            @RequestBody OrderItemDeleteDTO item,
            @RequestHeader(value = "chatId") long chatId
    ) {

        try {

//            FoodOrder currentOrder = new FoodOrder();
//            OrderItem orderItem1 = orderItemRepository.getOne(item.getOrderItemDTO().getId());
//            currentOrder = orderItem1.getGuest().getFoodOrder();
//
//            if (orderItem1.getOrderItemStatus().equals(OrderItemStatus.IN_CART)){
//                orderService.deleteOrderItem(orderItem1);
//            }
//            else {
//                if (item.getReason() == null){
//                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//                }else {
//                    //todo create dangerous zone
//                    orderService.deleteOrderItem(orderItem1);
//
//                }
//            }

//            return currentOrder.getDesk().getDeskDTOFullByChatId(Language.ru, chatId);
//////////////////////////////////////////////////////////////////////////////////
            OrderItem orderItem2 = orderItemRepository.getOne(item.getOrderItem().getId());
            Desk desk = orderItem2.getGuest().getFoodOrder().getDesk();
            if (orderService.cancelOrderItem(item)) {
                return new ResponseEntity<>(desk.getDeskDTOFull(Language.ru, chatId), HttpStatus.OK);
            }
            return new ResponseEntity<>("Where is comment????!!!!\uD83D\uDE20\uD83D\uDE20\uD83D\uDE20", HttpStatus.FORBIDDEN);

//////////////////////////////////////////////////////////////////////////////////

        } catch (Exception e) {
            e.printStackTrace();
            return new AnswerDTO("Error!").getJson();
        }

    }


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/guest/delete")
    public Object deleteOrder(
            @RequestBody GuestDTO guestDTO,
            @RequestHeader(value = "chatId") long chatId
    ) {
        try {
            //todo сделать сервис и переместить туда
            Guest guest = guestRepo.getOne(guestDTO.getId());
            Desk currentDesk = guest.getFoodOrder().getDesk();
            FoodOrder currentOrder = guest.getFoodOrder();
            if (guestRepo.getOrderItemsSize(guestDTO.getId()) == 0) {
                guestRepo.delete(guest.getId());
                if (currentOrder.getGuestsSize() == 0) {
                    currentDesk.setCurrentOrder(null);
                    deskRepo.save(currentDesk);
                    orderService.delete(currentOrder);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/editPrepayment")
    public ResponseEntity<Object> editPrepayment(@RequestParam("chequeId") long chequeId,
                                                 @RequestParam("prepayment") double prepayment,
                                                 @RequestHeader(value = "chatId") long chatId
    ) {

        try {

            return new ResponseEntity<>(orderService.editPrepayment(chequeId, prepayment).getChequeDTO(),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }

    }


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)  " +
            "&& @permissionEvaluator.isActiveOrderGuestId(#guestId)")
    @PostMapping("/addToCart")
    public Object addToCart(@RequestParam("foodId") int foodId,
                            @RequestParam("guestId") long guestId,
                            @RequestHeader(value = "chatId") long chatId) {

        try {
            return orderService.addToCartMobile(foodId, guestId).getGuest().getFoodOrder().getDesk().getDeskDTOFullByChatId(Language.ru, chatId);
        } catch (Exception e) {
            e.printStackTrace();
            Map<Object, Object> ans = new TreeMap<>();
            ans.put("answer", "error");
            return ans;
        }
    }


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/createEmptyOrder")
    public Object createEmptyOrder(
            @RequestParam("deskId") long deskId,
            @RequestHeader(value = "chatId") long chatId) {
        try {
            FoodOrder foodOrder = orderService.createEmptyOrderByChatId(deskId, chatId);
            return foodOrder.getFoodOrderDTOByChatId(Language.ru, chatId);

        } catch (Exception e) {
            e.printStackTrace();
            Map<Object, Object> ans = new TreeMap<>();
            ans.put("answer", "error");
            return ans;
        }
    }


    @GetMapping("/getCart")
    public Map<Object, Object> getCart(@RequestParam("deskId") long tableId,
                                       @RequestParam("chatId") long chatId) {


        List<CartItem> cartItems = cartItemService.findAllCartItemsOfTable(tableId);
        Employee user = employeeService.getByChatId(chatId);
        Map<Object, Object> data = new TreeMap<>();


        List<Map<Object, Object>> books = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Map<Object, Object> ans = new TreeMap<>();
            ans.put("id", cartItem.getId());
            ans.put("quantity", cartItem.getQuantity());
            ans.put("item", cartItem.getFood().getJson(user.getLanguage()));
            books.add(ans);
        }


        data.put("cartItems", books);

        return data;
    }

    @GetMapping("/getName")
    public String getCart(@RequestParam("chatId") long chatId) {
        return employeeService.getNameByChatId(chatId);
    }

//    @GetMapping("/getOrder")
//    public  FoodOrderDTO getOrder(@RequestParam("orderId") long orderId){
//
//        return orderService.findById(orderId).getFoodOrderDTO(Language.ru, );
//
//    }


    @GetMapping("/getOrders/active")
    public Object getActiveOrder(@RequestParam("chatId") long chatId) {
        List<FoodOrder> orders = orderService.getActiveOrdersOfWaiter(chatId);
        if (orders.size() > 0) {
            List<FoodOrderDTO> orjsns = new ArrayList<>();
            for (FoodOrder fo : orders) {
                orjsns.add(fo.getFoodOrderDTOByChatId(Language.ru, chatId));
            }
            return orjsns;
        } else {
            return new AnswerDTO("order is empty").getJson();
        }
    }

    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @GetMapping("/getOrders/done")
    public Object getDoneOrders(@RequestHeader("chatId") long chatId) {
        List<FoodOrder> orders = orderService.getDoneOrdersOfWaiter(chatId);

        List<FoodOrderDTO> orjsns = new ArrayList<>();
        for (FoodOrder fo : orders) {
            orjsns.add(fo.getFoodOrderDTOByChatId(Language.ru, chatId));
        }
        return orjsns;


    }

//    @GetMapping("/activeOrders")
//    public List<Map<Object, Object>> getActiveOrders(){
//        List<Map<Object, Object>>  orders = new ArrayList<>();
//        for (FoodOrder order:orderService.findActiveOrdersOrderByDate()){
//            orders.add(order.getFoodOrderDTO(Language.ru, ch));
//        }
//
//        return orders;
//    }

    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId) " +
            "&& @permissionEvaluator.isActiveOrderByOrderItem(#orderItemId)")
    @PostMapping("/order/cut")
    public DeskDTO cutOrderItem(@RequestParam("orderItemId") long orderItemId,
                                @RequestParam("quantity") int quantity,
                                @RequestParam("toGuestId") long toGuestId,
                                @RequestHeader(value = "chatId", defaultValue = "1") long chatId) {

        return orderService.cut(orderItemId, quantity, toGuestId, chatId);

    }


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId) && " +
            "@permissionEvaluator.isNotDoneOrderByOrderId(#orderId)")
    @PostMapping("/order/precheck")
    public ResponseEntity<Object> cutOrderItem(@RequestParam("orderId") long orderId,
                                               @RequestHeader(value = "chatId", defaultValue = "1") long chatId) {
        FoodOrder foodOrder = orderService.precheck(orderId);
        if (foodOrder != null) {
            return new ResponseEntity<>(foodOrder.getDesk().getDeskDTOFullByChatId(Language.ru, chatId), HttpStatus.OK);
        }
        return new ResponseEntity<>("Order is empty!!!", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/order/precheck/cancel")
    public DeskDTO cancelPrecheck(@RequestParam("orderId") long orderId,
                                  @RequestHeader(value = "chatId", defaultValue = "1") long chatId) {

        return orderService.doNotPrecheck(orderId).getDesk().getDeskDTOFullByChatId(Language.ru, chatId);

    }

    @PostMapping("/shift/open")
    public ResponseEntity<Object> openShift(@RequestHeader(value = "chatId") long chatId) {

        if (waiterShiftService.openShiftByChatId(chatId))
            return new ResponseEntity<>(HttpStatus.CREATED);

        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
    }

    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/shift/close")
    public Object closeShift(@RequestHeader(value = "chatId") long chatId) {

        if (orderService.hasNotDoneOfWaiterByChatId(chatId) && waiterShiftService.closeShiftByChatId(chatId)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);

    }

    @GetMapping("/shift/isOpen")
    public Map<Object, Object> isOpenShiftByChatId(@RequestHeader(value = "chatId") long chatId) {
        WaiterShift waiterShift = waiterShiftService.getOpenedShiftByChatId(chatId);
        if (waiterShift != null) {
            return new WaiterShiftDTO(true, waiterShift.getOpeningTime()).getJson();
        }
        return new WaiterShiftDTO(false, null).getJson();
    }


    @GetMapping("/getAll")
    public List<WaiterDTO> getAllWaiters(@RequestHeader(value = "chatId") long chatId) {
        return waiterService.getAllActiveWaiters(chatId, Language.ru);
    }

    @GetMapping("/getOne")
    public WaiterDTO getOneWaiter(@RequestParam("chatId") long chatId) {


        return waiterService.getOne(chatId, Language.ru);
    }


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @GetMapping("/getPaymentTypes")
    public List<PaymentMethodDTO> getPaymentTypes(@RequestHeader("chatId") long chatId) {

        List<PaymentMethod> methods = paymentMethodRepo.findAllByActiveIsTrueOrderById();
        List<PaymentMethodDTO> dtos = new ArrayList<>();
        for (PaymentMethod method : methods) {
            dtos.add(method.getPaymentMethodDTO());
        }

        return dtos;
    }


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/order/addPayment")
    public ChequeDTO addPaymentToOrder(@RequestHeader("chatId") long chatId,
                                       @RequestParam("chequeId") long chequeId,
                                       @RequestParam("paymentTypeId") long paymentTypeId,
                                       @RequestParam("amount") double amount) {

        return orderService.addPaymnetToCheque(chequeId, paymentTypeId, amount).getChequeDTO();

    }

    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/order/deletePayment")
    public ChequeDTO addPaymentToOrder(@RequestHeader("chatId") long chatId,
                                       @RequestBody PaymentDTO paymentDTO) {

        return orderService.deletePaymnet(paymentDTO).getChequeDTO();

    }


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @PostMapping("/order/pay")
    public Object payOrder(@RequestHeader("chatId") long chatId,
                           @RequestBody ChequeDTO chequeDTO) {
        if (orderService.pay(chequeDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }


    @PreAuthorize("@permissionEvaluator.isOpenShiftByChatId(#chatId)")
    @GetMapping("/order/getOne")
    public FoodOrderDTO payOrder(@RequestHeader("chatId") long chatId,
                                 @RequestParam("foodOrderId") long foodOrderId) {

        return orderService.findById(foodOrderId).getFoodOrderDTOByChatId(Language.ru, chatId);
    }


//    @PostMapping("/login")
//    public ResponseEntity<?> login(
//            @RequestBody UserDTO userDTO) {
//        User user = userService.findByCode(userDTO.getCode());
//        if (encoder.matches(userDTO.getPassword(), user.getPassword())) {
//            return ResponseEntity.ok("true");
//        }
//        return ResponseEntity.ok("failed");
//    }


    @GetMapping("/getRoles")
    public List<RoleDTO> getRoles(@RequestHeader("chatId") long chatId) {
        return employeeService.getRolesDTOByChatId(chatId);
    }


}
