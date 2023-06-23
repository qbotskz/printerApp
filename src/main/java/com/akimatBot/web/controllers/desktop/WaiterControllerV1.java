//package com.akimatBot.web.controllers.desktop;
//
//import com.akimatBot.entity.custom.*;
//import com.akimatBot.entity.enums.Language;
//import com.akimatBot.entity.enums.OrderItemStatus;
//import com.akimatBot.repository.repos.*;
//import com.akimatBot.services.*;
//import com.akimatBot.utils.reports.OrderReportDaily;
//import com.akimatBot.web.dto.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//@RestController()
//@RequestMapping("/api/desktop/waiter")
////@Component("waiterController")
//public class WaiterControllerV1 {
//
//    @Autowired
//    OrderRepository orderRepo;
//
//    @Autowired
//    MessageRepository messageRepo;
//    @Autowired
//    CartItemService cartItemService;
//    @Autowired
//    EmployeeService employeeService;
//
//    @Autowired
//    PropertiesRepo propertiesRepo;
//
//    @Autowired
//    OrderService orderService;
//
//    @Autowired
//    DeskRepo deskRepo;
//
//    @Autowired
//    OrderItemRepository orderItemRepository;
//
//    @Autowired
//    ChequeRepo chequeRepo;
//
//    @Autowired
//    GuestRepo guestRepo;
//
//    @Autowired
//    WaiterShiftService waiterShiftService;
//
//    @Autowired
//    WaiterService waiterService;
//
//    @Autowired
//    PaymentMethodRepo paymentMethodRepo;
//
//    @Autowired
//    FoodService foodService;
//
//    Language language = Language.ru;
//
//    @PostMapping("/login")
//    public ResponseEntity<HttpStatus>  loginByCode(@RequestBody WaiterDTO waiterDTO
//    ){
//            if (waiterService.existByCode(waiterDTO.getCode())) {
//                return new ResponseEntity<>(HttpStatus.OK);
//            }
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping(value = "/{id}")
//    public ResponseEntity<WaiterDTO> getUserById(@PathVariable(name = "id") Long id){
//
//
//        WaiterDTO waiterDTO = waiterService.getByCode(id);
//
//        if(waiterDTO == null){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return new ResponseEntity<>(waiterDTO, HttpStatus.OK);
//    }
//
//    @PostMapping("/shift/open")
//    public ResponseEntity<Object> openShift(@RequestHeader(value="code") long code){
//
//        if (waiterShiftService.openShift(code))
//            return new ResponseEntity<>(HttpStatus.CREATED);
//
//        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
//    }
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift()")
//    @PostMapping("/shift/close")
//    public Object closeShift(@RequestHeader(value="code") long code) {
//
//        if (orderService.hasNotDoneOfWaiter(code) && waiterShiftService.closeShift(code)){
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
//
//        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
//
//    }
//
//    @GetMapping("/shift/isOpen")
//    public  Map<Object, Object> isOpenShift(@RequestHeader(value="code") long code){
//
//        WaiterShift waiterShift = waiterShiftService.getOpenedShift(code);
//        if (waiterShift != null){
//            return new WaiterShiftDTO(true, waiterShift.getOpeningTime()).getJson();
//        }
//        return new WaiterShiftDTO(false, null).getJson();
//    }
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift()")
//    @GetMapping("/stopList/all")
//    public  List<FoodDTO> getAllStopList(@RequestHeader(value="code") long code){
//
//        List<Food> foods = foodService.getAllStopList();
//        return foodService.getFoodsDTO(foods, language);
//
//    }
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() "+
//            "&& @permissionEvaluator.hasRole('STOP_LIST')")
//    @PostMapping("/stopList/add")
//    public ResponseEntity<Object> addToStopList(@RequestHeader(value="code") long code,
//                                                @RequestBody FoodDTO foodDTO){
//
//        FoodDTO dto = foodService.addToStopList( foodDTO).getFoodDTO(language);
//        if (dto != null){
//            return new ResponseEntity<>(dto, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() " +
//            "&& @permissionEvaluator.hasRole('STOP_LIST')")
//    @PostMapping("/stopList/remove")
//    public ResponseEntity<Object> removeFromStopList(@RequestHeader(value="code") long code,
//                                                @RequestBody FoodDTO foodDTO){
//
//        if (foodService.removeFromStopList( foodDTO)){
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift()  " +
//            "&& @permissionEvaluator.isActiveOrderGuestId(#guestId) " +
//            "&& @permissionEvaluator.isMyOrderOrCanEditOtherByGuestId(#guestId)")
//    @PostMapping("/addToCart")
//    public Object addToCart(@RequestParam("foodId") int foodId,
//                            @RequestParam("guestId") long guestId){
//
//
//        try {
//            return orderService.addToCart(foodId, guestId,language);
//        }catch (Exception e){
//            e.printStackTrace();
//            Map<Object, Object> ans = new TreeMap<>();
//            ans.put("answer" , "error");
//            return ans;
//        }
//    }
//
//
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift()")
//    @PostMapping("/createEmptyOrder")
//    public Object createEmptyOrder(
//            @RequestParam("deskId") long deskId,
//            @RequestHeader(value="code") long code){
//        try {
//            return orderService.createEmptyOrder(deskId, code).getDesk().getDeskDTOFull(Language.ru, code);
//        }catch (Exception e){
//            e.printStackTrace();
//            Map<Object, Object> ans = new TreeMap<>();
//            ans.put("answer" , "error");
//            return ans;
//        }
//    }
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() " +
//            "&& @permissionEvaluator.isActiveOrderByOrderId(#foodOrderDTO.id) " +
//            "&& @permissionEvaluator.isMyOrderOrCanEditOther(#foodOrderDTO.id)")
//    @PostMapping("/createQuest")
//    public ResponseEntity<Object> createQuest(
//            @RequestBody FoodOrderDTO foodOrderDTO,
//            @RequestHeader(value="code") long code){
//        try {
//            return new ResponseEntity<>(orderService.createGuest(foodOrderDTO.getId()).getGuestDTO(Language.ru), HttpStatus.OK) ;
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST) ;
//        }
//    }
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() " +
//            "&& @permissionEvaluator.isActiveOrderByOrderId(#foodOrderDTO.id) && " +
//            "@permissionEvaluator.isMyOrderOrCanEditOther(#foodOrderDTO.id)")
//    @PostMapping("/cookOrder")
//    public ResponseEntity<Object> createOrderRest(
//            @RequestBody FoodOrderDTO foodOrderDTO,
//            @RequestHeader(value="code") long code
//    ){
//        try {
//            if (orderService.cookOrder(foodOrderDTO.getId())){
//                return new ResponseEntity<>(chequeRepo.findByOrderId(foodOrderDTO.getId()).getChequeDTO(), HttpStatus.OK);
//            }
//            return new ResponseEntity<>("Check remains!!!", HttpStatus.BAD_REQUEST);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() " +
//            "&& @permissionEvaluator.isActiveOrderByOrderItem(#orderItemDTO.id)")
//    @PostMapping("/eatOrderItem")
//    public Boolean eatOrderItem(@RequestBody OrderItemDTO orderItemDTO,
//                                @RequestHeader(value="code") long code
//    ){
//
//        try {
//            OrderItem orderItem = orderItemRepository.getOne(orderItemDTO.getId());
//            if (orderItem.getOrderItemStatus().equals(OrderItemStatus.COOK)) {
//                orderItem.setOrderItemStatus(OrderItemStatus.EAT);
//            }
//            else if (orderItem.getOrderItemStatus().equals(OrderItemStatus.EAT)) {
//                orderItem.setOrderItemStatus(OrderItemStatus.COOK);
//
//            }
//            orderItemRepository.save(orderItem);
//
//            return orderItem.getOrderItemStatus().equals(OrderItemStatus.EAT);
//        }catch (Exception e){
//            return null;
//        }
//
//    }
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() " +
//            "&& @permissionEvaluator.isActiveOrderByOrderItem(#item.orderItemDTO.id) " +
//            "&& @permissionEvaluator.isMyOrderOrCanEditOtherByOrderItemId(#item.orderItemDTO.id)")
//    @PostMapping("/orderItem/delete")
//    public ResponseEntity<Object> editCountOfOrderItem(
//            @RequestBody OrderItemDeleteDTO item,
//            @RequestHeader(value="code") long code
//    ){
//
//        try {
//
//            OrderItem orderItem1 = orderItemRepository.getOne(item.getOrderItemDTO().getId());
//            Cheque cheque = orderItem1.getGuest().getFoodOrder().getCheque();
//            if (orderService.cancelOrderItem(item)){
//                return new ResponseEntity<>(cheque.getChequeDTO(), HttpStatus.OK);
//            }
//            return new ResponseEntity<>("Where is comment????!!!!\uD83D\uDE20\uD83D\uDE20\uD83D\uDE20",HttpStatus.FORBIDDEN);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() "+
//            "&& @permissionEvaluator.isActiveOrderByOrderItem(#orderItemDTO.id) " +
//            "&& @permissionEvaluator.isMyOrderOrCanEditOtherByOrderItemId(#orderItemDTO.id)")
//    @PostMapping("/orderItem/editQuantity") // returns desk full json
//    public ResponseEntity<Object> editCountOfOrderItem(@RequestBody OrderItemDTO orderItemDTO,
//                                       @RequestHeader(value="code") long code
//    ){
//        try {
//            OrderItem orderItem = orderItemRepository.findById(orderItemDTO.getId());
//            if (orderItem.getOrderItemStatus().equals(OrderItemStatus.IN_CART)) {
//                Cheque cheque = orderService.editOrderItemCount(orderItem, orderItemDTO.getQuantity(), orderItemDTO.getComment());
//
//                return new ResponseEntity<>(cheque.getChequeDTO(),
//                        HttpStatus.OK);
//            }
//            else
//                return new ResponseEntity<>("Order item is not in the cart", HttpStatus.BAD_REQUEST);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() "+
//            "&& @permissionEvaluator.isActiveOrderGuestId(#guestDTO.id)")
//    @PostMapping("/guest/delete")
//    public Object deleteOrder(
//            @RequestBody GuestDTO guestDTO,
//            @RequestHeader(value="code") long code
//    ){
//        try {
//             if (orderService.deleteGuest(guestDTO)){
//                 return  new ResponseEntity<>(HttpStatus.OK);
//             }
//             else {
//                 return new ResponseEntity<>("Guest has order items!", HttpStatus.NOT_ACCEPTABLE);
//             }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//        }
//
//    }
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() && " +
//            "@permissionEvaluator.hasRole('EDIT_DISCOUNT') "+
//            "&& @permissionEvaluator.isActiveOrderByCheque(#chequeDTO.id)")
//    @PostMapping("/cheque/editDiscount")
//    public ResponseEntity<Object> editDiscount(
//                                @RequestBody ChequeDTO chequeDTO,
//                                @RequestHeader(value="code") long code
//    ){
//        try {
//            return new ResponseEntity<>(orderService.editDiscount(chequeDTO.getId() ,chequeDTO.getDiscount()).getChequeDTO(), HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() && "+
//            "@permissionEvaluator.hasRole('EDIT_SERVICE') "+
//            "&& @permissionEvaluator.isActiveOrderByCheque(#chequeDTO.id)")
//    @PostMapping("/cheque/editService")
//    public ResponseEntity<Object> editService( @RequestBody ChequeDTO chequeDTO,
//                                                @RequestHeader(value="code") long code
//    ){
//        try {
//            return new ResponseEntity<>(orderService.editService(chequeDTO.getId() ,chequeDTO.getService()).getChequeDTO(), HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() "+
//            "&& @permissionEvaluator.isActiveOrderByCheque(#chequeDTO.id) " +
//            "&& @permissionEvaluator.isMyOrderOrCanEditOtherByChequeId(#chequeDTO.id)")
//    @PostMapping("/cheque/editPrepayment")
//    public ResponseEntity<Object> editPrepayment(@RequestBody ChequeDTO chequeDTO,
//                                  @RequestHeader(value="code") long code
//    ){
//        try {
//            ChequeDTO chequeDTO1 = orderService.editPrepayment(chequeDTO.getId() ,chequeDTO.getPrepayment()).getChequeDTO();
//            return new ResponseEntity<>(chequeDTO1, HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift()")
//    @GetMapping("/searchFood")
//    public  List<FoodDTO> searchBook(@RequestParam("foodName") String foodName,
//                                                 @RequestParam("page") int page,
//                                                 @RequestHeader
//                                                 ("code") int code){
//
//        List<FoodDTO> maps = new ArrayList<>();
//        List<Food> foods = foodService.searchFood(foodName, page,language);
//        for (Food food : foods){
//            maps.add(food.getFoodDTO(language));
//        }
//        return maps ;
//    }
//
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() "+
//            "&& @permissionEvaluator.isNotDoneOrderByOrderId(#orderDTO.id) && " +
//            "@permissionEvaluator.isMyOrderOrCanEditOther(#orderDTO.id)")
//    @PostMapping("/order/precheck")
//    public ResponseEntity<Object> cutOrderItem(@RequestBody FoodOrderDTO orderDTO,
//                                                @RequestHeader(value = "code", defaultValue = "1") long code){
//        try {
//            FoodOrder foodOrder = orderService.precheck(orderDTO.getId());
//            if (foodOrder != null){
//                return new ResponseEntity<>(HttpStatus.OK);
//            }
//            return new ResponseEntity<>("Order is empty!!!",HttpStatus.BAD_REQUEST);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() "+
//            "&& @permissionEvaluator.isPrecheckOrderByOrder(#orderDTO.id) " +
//            "&& @permissionEvaluator.hasRole('PRECHECK_CANCEL')")
//    @PostMapping("/order/precheck/cancel")
//    public ResponseEntity<Object> cancelPrecheck(@RequestBody FoodOrderDTO orderDTO,
//                                  @RequestHeader(value = "code", defaultValue = "1") long code){
//        try {
//            orderService.doNotPrecheck(orderDTO.getId());
//            return new ResponseEntity<>(HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() " +
//            "&& @permissionEvaluator.isActiveOrderByOrderItem(#orderItemId) " +
//            "&& @permissionEvaluator.isMyOrderOrCanEditOtherByGuestId(#toGuestId)")
//    @PostMapping("/order/cut")
//    public ResponseEntity<Object> cutOrderItem(@RequestParam("orderItemId") long orderItemId,
//                                @RequestParam("quantity") int quantity,
//                                @RequestParam("toGuestId") long toGuestId,
//                                @RequestHeader(value = "code", defaultValue = "1") long code){
//        try {
//            OrderItem orderItem = orderService.cut(orderItemId, quantity, toGuestId);
//           if (orderItem != null){
//               return new ResponseEntity<>(orderItem.getOrderItemDTO(language), HttpStatus.OK);
//           }
//            return new ResponseEntity<>("Error in quantity!",HttpStatus.BAD_REQUEST);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() "+
//            "&& @permissionEvaluator.isPrecheckOrderByCheque(#chequeId) " +
//            "&& @permissionEvaluator.isMyOrderOrCanEditOtherByChequeId(#chequeId)")
//    @PostMapping("/order/addPayment")
//    public ChequeDTO addPaymentToOrder(@RequestHeader("code") long code,
//                                       @RequestParam ("chequeId") long chequeId,
//                                       @RequestParam("paymentTypeId") long paymentTypeId,
//                                       @RequestParam("amount") double amount){
//
//        return orderService.addPaymnetToCheque(chequeId, paymentTypeId, amount).getChequeDTO();
//
//    }
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift()")
//    @PostMapping("/order/deletePayment")
//    public ChequeDTO addPaymentToOrder(@RequestHeader("code") long code,
//                                       @RequestBody PaymentDTO paymentDTO){
//
//        return orderService.deletePaymnet(paymentDTO).getChequeDTO();
//
//    }
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() " +
//            "&& @permissionEvaluator.isMyOrderOrCanEditOtherByChequeId(#chequeDTO.id)")
//    @PostMapping("/order/pay")
//    public Object payOrder(@RequestHeader("code") long code,
//                           @RequestBody ChequeDTO chequeDTO){
//        if (orderService.pay(chequeDTO) ){
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//    }
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift()")
//    @GetMapping("/getPaymentTypes")
//    public List<PaymentMethodDTO> getPaymentTypes(@RequestHeader("code") long code){
//
//        List<PaymentMethod> methods = paymentMethodRepo.findAllByActiveIsTrueOrderById();
//        List<PaymentMethodDTO> dtos = new ArrayList<>();
//        for (PaymentMethod method : methods){
//            dtos.add(method.getPaymentMethodDTO());
//        }
//
//        return dtos;
//    }
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() " +
//            "&& @permissionEvaluator.isMyOrderOrCanEditOther(#orderId)")
//    @PostMapping("/order/changeDesk")
//    public Object changeDesk(
//                                @RequestParam("orderId") long orderId,
//                                @RequestParam("toDeskId") long toDeskId
//    ){
//        if (orderService.changeDesk(orderId,toDeskId) ){
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//    }
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift() " +
//            "&& @permissionEvaluator.hasRole('CHANGE_WAITER_OF_DESK')")
//    @PostMapping("/order/changeWaiter")
//    public Object changeWaiter(
//                                @RequestParam("orderId") long orderId,
//                                @RequestParam("toWaiterId") long toWaiterId
//    ){
//        if (orderService.changeWaiter(orderId,toWaiterId) ){
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//    }
//
//
//    @GetMapping("/getRoles")
//    public List<RoleDTO> getRoles(@RequestHeader("code") long code){
//        return employeeService.getRolesDTOByCode(code);
//    }
//
//    @GetMapping("/getReportDaily")
//    public  ResponseEntity<Object> getReportDaily(@RequestHeader("code") long code){
//        ReportDailyDTO reportDaily = orderService.getReportDaily(code);
//        return new ResponseEntity<>(reportDaily, HttpStatus.OK);
//    }
//
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//
//
//
//
//
//
//
//
//
////    @GetMapping("/getCart")
////    public  Map<Object, Object> getCart(@RequestParam("deskId") long tableId,
////                                        @RequestParam("chatId") long chatId){
////
////
////        List<CartItem> cartItems = cartItemService.findAllCartItemsOfTable(tableId);
////        User user = userService.getByChatId(chatId);
////        Map<Object, Object> data = new TreeMap<>();
////
////
////
////        List<Map<Object, Object>> books = new ArrayList<>();
////        for (CartItem cartItem : cartItems){
////            Map<Object, Object> ans = new TreeMap<>();
////            ans.put("id" , cartItem.getId());
////            ans.put("quantity" , cartItem.getQuantity());
////            ans.put("item" , cartItem.getFood().getJson(user.getLanguage()));
////            books.add(ans);
////        }
////
////
////        data.put("cartItems", books);
////
////        return data ;
////    }
//
////    @GetMapping("/getOrder")
////    public  FoodOrderDTO getOrder(@RequestParam("orderId") long orderId){
////
////        return orderService.findById(orderId).getFoodOrderDTO(Language.ru, );
////
////    }
//
//
//
//
//
//
//
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift()")
//    @GetMapping("/getOrders/done")
//    public Object getDoneOrders(@RequestHeader("chatId") long chatId) {
//        List<FoodOrder> orders = orderService.getDoneOrdersOfWaiter(chatId);
//
//        List<FoodOrderDTO> orjsns = new ArrayList<>();
//        for (FoodOrder fo : orders) {
//            orjsns.add(fo.getFoodOrderDTO(Language.ru, chatId));
//        }
//        return orjsns;
//
//
//    }
//
////    @GetMapping("/activeOrders")
////    public List<Map<Object, Object>> getActiveOrders(){
////        List<Map<Object, Object>>  orders = new ArrayList<>();
////        for (FoodOrder order:orderService.findActiveOrdersOrderByDate()){
////            orders.add(order.getFoodOrderDTO(Language.ru, ch));
////        }
////
////        return orders;
////    }
//
//
//
//
//
//
//    @GetMapping("/getAll")
//    public  List<WaiterDTO> getAllWaiters(@RequestHeader(value="chatId") long chatId){
//        return waiterService.getAllActiveWaiters(chatId, Language.ru);
//    }
//
//    @GetMapping("/getOne")
//    public WaiterDTO getOneWaiter(@RequestParam("chatId") long chatId){
//
//
//        return waiterService.getOne(chatId, Language.ru);
//    }
//
//
//
//
//
//
//
//    @PreAuthorize("@permissionEvaluator.isOpenShift()")
//    @GetMapping("/order/getOne")
//    public FoodOrderDTO payOrder(@RequestHeader("chatId") long chatId,
//                           @RequestParam("foodOrderId") long foodOrderId){
//
//        return orderService.findById(foodOrderId).getFoodOrderDTO(Language.ru, chatId);
//    }
//
//
//
//}
