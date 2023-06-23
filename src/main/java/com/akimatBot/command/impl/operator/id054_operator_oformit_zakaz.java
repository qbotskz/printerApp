//package com.akimatBot.command.impl.operator;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.custom.RestaurantBranch;
//import com.akimatBot.entity.enums.DeliveryMethod;
//import com.akimatBot.entity.enums.OrderType;
//import com.akimatBot.entity.enums.PaymentMethod;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.utils.ButtonsLeaf;
//import com.akimatBot.utils.Const;
//import com.akimatBot.utils.JsonReader;
//import com.akimatBot.utils.MapUtil;
//import com.jayway.jsonpath.PathNotFoundException;
//import lombok.SneakyThrows;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//public class id054_operator_oformit_zakaz extends Command {
//
//
//    private User user;
//    private ButtonsLeaf buttonsLeaf;
//    private int lastMsgId;
//    private int msgId;
//    private Order order;
//    private DeliveryMethod deliveryMethod;
//    private DeliveryMethod deliveryMethodEnum;
//    private String location;
//    //    private String API = "AIzaSyARrdm2oRC22Bwd0-mmGwzlxVVmosgv7Uo";
//    private JsonReader jsonReader;
//    private String paymentNalom;
//    private PaymentMethod paymentMethod;
//    private String name;
//    private String phone;
//    private String comment;
//    private boolean inZone;
//    private int deliveryPrice;
//    private String deliveryType;
//    private List<RestaurantBranch> branches;
//    private RestaurantBranch restaurantBranch;
//    private double latitude;
//    private double longitude;
//
//
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        deleteMessage(lastMsgId);
//        switch (waitingType) {
//            case START:
//                if (isButton(40)) {
//                    sendMessageWithKeyboard("Главное меню", 2);
//                    return EXIT;
//                }
//                sendMessageWithKeyboard("----", 21);
//                deleteMessage(updateMessageId);
//                user = userRepository.findByChatId(chatId);
//                getName();
//                waitingType = WaitingType.CHANGE_NAME;
////                getDeliveryMethod();
////                waitingType = WaitingType.DELIVERY_METHOD;
//                return COMEBACK;
//            case CONFIRM_NAME:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()) {
//                    if (isButton(56)) {
//                        getDeliveryMethod();
//                        user = userRepository.save(user);
//                        waitingType = WaitingType.DELIVERY_METHOD;
//                    } else {
//                        getName();
//                        waitingType = WaitingType.CHANGE_NAME;
//                    }
//
//                } else {
//                    lastMsgId = wrongData();
//                    confirmName(name);
//                }
//                return COMEBACK;
//            case CHANGE_NAME:
//                deleteMessage(updateMessageId);
//                if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().length() <= 50) {
//
//                    //user.setFullName(update.getMessage().getText());
//                    name = update.getMessage().getText();
//                    confirmName(name);
//                    waitingType = WaitingType.CONFIRM_NAME;
//
//                } else {
//                    lastMsgId = wrongData();
//                    getName();
//                }
//                return COMEBACK;
//            case DELIVERY_METHOD:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()) {
//                    if (isButton(63)) {
//                        deliveryMethodEnum = DeliveryMethod.BY_COURIER;
////                        deliveryMethod = getButtonText(63);
//                        getLocation();
////                        confirmPhone(user.getPhone());
//                        waitingType = WaitingType.GET_LOCATION;
////                        waitingType = WaitingType.CONFIRM_PHONE;
//                    } else if (isButton(64)) {
//                        deliveryMethodEnum = DeliveryMethod.SELF_PICKUP;
////                        deliveryMethod = getButtonText(64);
//                        confirmPhone(user.getPhone());
//                        waitingType = WaitingType.CONFIRM_PHONE;
//                    }
//
//                } else {
//                    lastMsgId = wrongData();
//                    getDeliveryMethod();
//                }
//                return COMEBACK;
//            case GET_LOCATION:
//                deleteMessage(lastMsgId);
//                deleteMessage(updateMessageId);
//                deleteMessage(lastSendMessageID);
//                sendMessageWithKeyboard("----", 21);
//
//                if (update.hasCallbackQuery()) {
//                    // it is when button Back clicked
//                    // button id is 67
//                    getDeliveryMethod();
//                    waitingType = WaitingType.DELIVERY_METHOD;
//
//                    return COMEBACK;
//                }
//                if (update.getMessage().hasLocation()) {
//                    // here it uses google api to transfer coordinates to actual address
//                    // But it is paid, so be careful
//                    jsonReader = new JsonReader();
//                    latitude = update.getMessage().getLocation().getLatitude();
//                    longitude = update.getMessage().getLocation().getLongitude();
//
//                    MapUtil mapUtil = new MapUtil();
//                    inZone = mapUtil.isInDeliveryZone(longitude, latitude);
//
//
//                    jsonReader.setLatitude(String.valueOf(update.getMessage().getLocation().getLatitude()));
//                    jsonReader.setLongitude(String.valueOf(update.getMessage().getLocation().getLongitude()));
//
//                    try {
//                        location = jsonReader.getFormattedAddress();
//                    } catch (PathNotFoundException e) {
//                        location = "null";
//                    }
//                    getPhone();
//                    waitingType = WaitingType.CHANGE_PHONE;
//                    return COMEBACK;
////                    if(user.getPhone()==null||user.getPhone().equals("null")){
////                        getPhone();
////                        waitingType = WaitingType.CHANGE_PHONE;
////                        return COMEBACK;
////                    }
////                    else{
////                        confirmPhone(user.getPhone());
////                        waitingType = WaitingType.CONFIRM_PHONE;
////                    }
//                }
//                else if (update.getMessage().hasText() && update.hasMessage()) {
//                    location = update.getMessage().getText();
//                    getPhone();
//                    waitingType = WaitingType.CHANGE_PHONE;
//                    return COMEBACK;
////                    if(user.getPhone()==null||user.getPhone().equals("null")){
////                        getPhone();
////                        waitingType = WaitingType.CHANGE_PHONE;
////                        return COMEBACK;
////                    }
////                    else{
////                        confirmPhone(user.getPhone());
////                        waitingType = WaitingType.CONFIRM_PHONE;
////                    }
////                    waitingType = WaitingType.CONFIRM_PHONE;
//
//                }
//                else {
//                    lastMsgId = wrongData();
//                    getLocation();
//                }
//                return COMEBACK;
//            case CONFIRM_PHONE:
//                deleteMessage(lastMsgId);
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()) {
//                    if (isButton(56)) {
//
//                        choosePayment();
//                        waitingType = WaitingType.PAYMENT_METHOD;
//
//                    } else if (isButton(57)) {
//                        getPhone();
//                        waitingType = WaitingType.CHANGE_PHONE;
//                    } else {
//                        if(deliveryMethod.equals(getButtonText(64))){
//                            getDeliveryMethod();
//                            waitingType =WaitingType.DELIVERY_METHOD;
//                        }else{
//                            getLocation();
//                            waitingType = WaitingType.GET_LOCATION;
//                        }
//                        return COMEBACK;
//                    }
//                } else {
//                    lastMsgId = wrongData();
//                    confirmPhone(user.getPhone());
//                }
//                return COMEBACK;
//            case CHANGE_PHONE:
//
//                // This is where your typed or shared phone number will be saved in database
//
//                deleteMessage(lastSendMessageID);
//
//                deleteMessage(updateMessageId);
//                if (botUtils.hasContactOwner(update)) {
//                    phone = update.getMessage().getContact().getPhoneNumber();
//                    if (phone.charAt(0) == '7') {
//                        phone = phone.replaceFirst("7", "+7");
//                    }
//
//                    msgId = choosePayment();
//                    waitingType = WaitingType.PAYMENT_METHOD;
//                    //finishOrder();
//                    //waitingType = WaitingType.FINISH_ORDER;
//                }
//                else if(update.hasMessage() && update.getMessage().hasText()){
//                    phone = update.getMessage().getText();
//                    if(phone.length()==12) {
//                        if (!phone.startsWith("+")) {
//                            tryAgain();
//                            return COMEBACK;
//                        }
//                    }
//
//                    msgId = choosePayment();
//                    waitingType = WaitingType.PAYMENT_METHOD;
//
//                }
//                else {
//                    lastMsgId = wrongData();
//                    getPhone();
//                }
//                return COMEBACK;
//            case PAYMENT_METHOD:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()) {
//                    if (isButton(58)) {
////                        paymentMethod = getButtonText(58);
//                        paymentMethod = PaymentMethod.KASPI_TRANSFER;
//                        finishOrder(false);
//                        waitingType = WaitingType.FINISH_ORDER;
//                    } else if(isButton(59)) {
////                        paymentMethod = getButtonText(59);
//                        paymentMethod = PaymentMethod.BY_CASH;
//                        finishOrder(false);
//                        waitingType = WaitingType.FINISH_ORDER;
//                    }
//                    else{
//                        confirmPhone(user.getPhone());
//                        waitingType = WaitingType.CONFIRM_PHONE;
//                    }
//
//                } else {
//                    lastMsgId = wrongData();
//                    choosePayment();
//                }
//                return COMEBACK;
//            case GET_MONEY_SUM:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()) {
//                    if (isButton(60)) {
//                        paymentNalom = getButtonText(60);
//                        finishOrder(false);
//                        waitingType = WaitingType.FINISH_ORDER;
//                    } else if (isButton(61)) {
//                        exactSum();
//                        waitingType = WaitingType.EXACT_MONEY_SUM;
//                    } else if(isButton(62)){
//                        paymentNalom = getButtonText(62);
//                        finishOrder(false);
//                        waitingType = WaitingType.FINISH_ORDER;
//                    }
//                    else{
//                        choosePayment();
//                        waitingType = WaitingType.PAYMENT_METHOD;
//
//                    }
//                } else {
//                    lastMsgId = wrongData();
//                    getMoneySum();
//                }
//                return COMEBACK;
//            case EXACT_MONEY_SUM:
//                deleteMessage(updateMessageId);
//                if(hasCallbackQuery()){
//                    getMoneySum();
//                    waitingType = WaitingType.GET_MONEY_SUM;
//
//                }
//
//                else if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().length() <= 50 && isNumeric(update.getMessage().getText())) {
//                    paymentNalom = update.getMessage().getText();
//                    finishOrder(false);
//                    waitingType = WaitingType.FINISH_ORDER;
//                } else {
//                    lastMsgId = wrongData();
//                    exactSum();
//                }
//                return COMEBACK;
//            case FINISH_ORDER:
//                //finishOrder();
//                //createMessageToEdit();
//                return EXIT;
//        }
//        return EXIT;
//    }
//    private void tryAgain() throws TelegramApiException {
//        wrongData();
//        getPhone();
//    }
//
//    public static boolean isNumeric(String string) {
//        int intValue;
//
//        if (string == null || string.equals("")) {
//            System.out.println("String cannot be parsed, it is null or empty.");
//            return false;
//        }
//
//        try {
//            intValue = Integer.parseInt(string);
//            return true;
//        } catch (NumberFormatException e) {
//            System.out.println("Input String cannot be parsed to Integer.");
//        }
//        return false;
//    }
//
//    private int exactSum() throws TelegramApiException {
//        return sendMessageWithKeyboard(getText(Const.GET_EXACT_MONEY_SUM), 16);
//    }
//
//    private int getMoneySum() throws TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(Const.GET_PAYMENT_METHOD), 18));
//    }
//
//    private int confirmPhone(String phone) throws TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(Const.CONFIRM_PHONE), user.getPhone()), 62));
//    }
//
//    private int choosePayment() throws TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(Const.GET_PAYMENT_METHOD), 17));
//    }
//    private void finishOrder(boolean useCashback) throws TelegramApiException {
//        order = new Order();
//        order.setUser(user);
//        order.setFoods(user.getFoods());
//
//        order.setOrderType(OrderType.TELEGRAM);
//        order.setDeliveryMethod(deliveryMethodEnum);
//        order.setContact(name + ", " + phone);
//        order.setOrderedDate(new Date());
//        order.setOrderType(user.getOrderType());
//        order.setComment(comment);
//
//
//        order.setPaymentMethod(paymentMethod);
//        order.setFinished(false);
//
//
//        order = orderRepository.save(order);
//        String text = "";
//        Map<Food, Long> foodMap = countByClassicalLoop(user.getFoods());
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        int sum = 0;
//        int count = 1;
//        while (itr.hasNext()) {
//            Map.Entry<Food, Long> entry = itr.next();
//            if(entry.getKey().getSpecialOfferSum()!=null){
//                text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + (entry.getKey().getFoodPrice(user.getCity()) - entry.getKey().getSpecialOfferSum()) + "₸ x " + entry.getValue() + "шт = " + (entry.getKey().getFoodPrice(user.getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue() + "₸\n";
//                sum += (entry.getKey().getFoodPrice(user.getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue();
//
//            }
//            else{
//                text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + entry.getKey().getFoodPrice(user.getCity()) + "₸ x " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice(user.getCity()) * entry.getValue() + "₸\n";
//                sum += entry.getKey().getFoodPrice(user.getCity()) * entry.getValue();
//
//            }
//            count++;
//        }
//
//        if(inZone){
//            deliveryType = "В квадрате -";
//            if(sum>=5000)  {
//                deliveryPrice = 0;
//                deliveryType +=" бесплатно";
//            }
//            else {
//                deliveryPrice = 1000;
//                deliveryType += deliveryPrice;
//            }
//
//
//        }
//
//
//        else{
//            deliveryPrice = 0;
//            deliveryType = "Вне квадрата, цены от "+1200+"тг\n"+"Менеджер позвонит вам по поводу цены доставки";
//
//        }
//        order.setDeliveryPrice(deliveryPrice);
//        order.setDeliveryType(deliveryType);
//        if(order.getDeliveryMethod()==DeliveryMethod.BY_COURIER && longitude!=0 && latitude!=0){
//            String sp = "<a href='https://2gis.kz/almaty/geo/"+longitude+"%2C"+latitude+"'>"+location+"</a>";
//            order.setLocation(sp);
//        }
//        else if(order.getDeliveryMethod()==DeliveryMethod.BY_COURIER){
//            order.setLocation(location);
//        }
//        else{
//            order.setLocation(restaurantBranch.getBranchName());
//
//        }
//        sum+=deliveryPrice;
//        order.setDeliveryPrice(deliveryPrice);
//        String message = "";
//        String messageToOperator = "";
//        String messageToCourier = "";
//        if(useCashback){
//            if(sum- user.getCashback() < 0){
//                user.setCashback(user.getCashback()-sum);
//                order.setSum(0);
//
//            }
//            else{
//                order.setSum(sum-user.getCashback());
//                user.setCashback(0);
//            }
//            user = userRepository.save(user);
//        }
//        else{
//            order.setSum(sum);
//        }
//        if (order.getDeliveryMethod()==DeliveryMethod.SELF_PICKUP) {
//            // paymentNalom != null means that paymentMethod is BY_CASH
//            // I wrote this before i added paymentMethod field to Order entity
//            // so don't mention it
//            messageToOperator = String.format(getText(79), order.getId()) + "\n" + String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getComment(), order.getSum());
//            message = String.format(getText(84), order.getId()) + "\n" + String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getComment(),order.getSum());
//        } else {
//            // Otherwise paymentMethod is CARD
//            messageToOperator = String.format(getText(79), order.getId()) + "\n" + String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getComment(),order.getDeliveryType(), order.getSum());
//            message = String.format(getText(84), order.getId()) + "\n" + String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getComment(),order.getDeliveryType(),order.getSum());
//        }
////        if(order.getDeliveryMethod()==DeliveryMethod.SELF_PICKUP){
////            messageToCourier = messageToCourier.replaceAll("(?m)^<b>Доставка:</b>.*", "");
////            messageToOperator = messageToOperator.replaceAll("(?m)^<b>Доставка:</b>.*", "");
////            message = message.replaceAll("(?m)^<b>Доставка:</b>.*", "");
////        }
//        sendMessageWithKeyboard(message, 2);
//
//
//
//
//        orderRepository.save(order);
//
//        List<User> users;
//        if(restaurantBranch==null)users = userRepository.findAllByRolesContains(new Role(4));
//        else{
//            users = userRepository.findAllByRolesContainsAndRestaurantBranch(new Role(4), restaurantBranch);
//        }
//        String finalMessageToOperator = messageToOperator;
//        Thread sendingThread = new Thread() {
//            int messageCount = 0;
//
//            @SneakyThrows
//            @Override
//            public void run() {
//                for (User u : users) {
//
//                    if (messageCount >= 10) {
//                        try {
//                            Thread.sleep(1500);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        messageCount = 0;
//                    }
//
//                    try{
//                        sendMessageWithKeyboardWithChatId(finalMessageToOperator, 38, u.getChatId());
//                        messageCount++;
//                    }
//                    catch (Exception e){
//                        System.out.println(u.getChatId()+" - CHAT NOT FOUND");
//                    }
//
//                }
//
//            }
//        };
//        sendingThread.start();
//        // I sent messages using Thread because Telegram Api has a limitation of sending messages at once
//        // You can send only 30 message per second
//        // To overcome this problem thread will sleep for 1.5 sec after sending 15 messages
//
//    }
//    private void finishOrder1() throws TelegramApiException {
//        order = new Order();
//        order.setUser(userRepository.findByChatId(chatId));
//        order.setFoods(user.getFoods());
//        order.setOrderType(user.getOrderType());
//        order.setDeliveryMethod(deliveryMethodEnum);
//        order.setContact(user.getFullName() + ", " + user.getPhone());
//        order.setOrderedDate(new Date());
//        order.setLocation(location);
//        order.setPaymentMethod(paymentMethod);
//        order.setPaymentNalom(paymentNalom);
//        order.setFinished(false);
//        order = orderRepository.save(order);
//
//        String text = "";
//        Map<Food, Long> foodMap = countByClassicalLoop(user.getFoods());
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        int sum = 0;
//        int count = 1;
//        while (itr.hasNext()) {
//            Map.Entry<Food, Long> entry = itr.next();
//            if(entry.getKey().getSpecialOfferSum()!=null){
//                text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + (entry.getKey().getFoodPrice(user.getCity()) - entry.getKey().getSpecialOfferSum()) + "₸ x " + entry.getValue() + "шт = " + (entry.getKey().getFoodPrice(user.getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue() + "₸\n";
//                sum += (entry.getKey().getFoodPrice(user.getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue();
//
//            }
//            else{
//                text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + entry.getKey().getFoodPrice(user.getCity()) + "₸ x " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice(user.getCity()) * entry.getValue() + "₸\n";
//                sum += entry.getKey().getFoodPrice(user.getCity()) * entry.getValue();
//
//            }
//            count++;
//        }
//        String message = "";
//        String messageToOperator = "";
//        String messageToCourier = "";
//        if (order.getDeliveryMethod()==DeliveryMethod.SELF_PICKUP) {
//            messageToCourier = String.format(getText(78), order.getId()) + "\n" + String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getComment(),order.getSum(), paymentNalom);
//            messageToOperator = String.format(getText(79), order.getId()) + "\n" + String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getComment(),order.getSum(), paymentNalom);
//            message = String.format(getText(84), order.getId()) + "\n" + String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getComment(),order.getSum(), paymentNalom);
//        } else {
//            messageToCourier = String.format(getText(78), order.getId()) + "\n" + String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getComment(),order.getDeliveryType(),order.getSum());
//
//            messageToOperator = String.format(getText(79), order.getId()) + "\n" + String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getComment(),order.getDeliveryType(),order.getSum());
//            message = String.format(getText(84), order.getId()) + "\n" + String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getComment(),order.getDeliveryType(),order.getSum());
//        }
//        sendMessageWithKeyboard(message, 2);
//        order.setSum(sum);
//        orderRepository.save(order);
////        List<User> users = userRepository.findAllByRolesContains(new Role(4));
////        for (User u : users) {
////            sendMessageWithKeyboardWithChatId(messageToOperator, 38, u.getChatId());
////        }
//        List<User> users = userRepository.findAllByRolesContains(new Role(4));
//        String finalMessageToOperator = messageToOperator;
//        String finalMessageToCourier = messageToCourier;
//        Thread sendingThread = new Thread() {
//            int messageCount = 0;
//
//            @SneakyThrows
//            @Override
//            public void run() {
//                for (User u : users) {
//
//                    if (messageCount >= 10) {
//                        try {
//                            Thread.sleep(1500);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        messageCount = 0;
//                    }
//
//                    sendMessageWithKeyboardWithChatId(finalMessageToOperator, 38, u.getChatId());
//                    messageCount++;
//                }
//
//            }
//        };
//        sendingThread.start();
//
//    }
//
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//
//    private int getLocation() throws TelegramApiException {
//        return sendMessageWithKeyboard(getText(164), 35);
//    }
//
//    private int confirmName(String name) throws TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(167), name), 16));
//    }
//
//    private int getDeliveryMethod() throws TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(Const.GET_DELIVERY_METHOD), 19));
//    }
//
//    private int getName() throws TelegramApiException {
//        return botUtils.sendMessage(165, chatId);
//    }
//
//    private int getPhone() throws TelegramApiException {
//        return sendMessageWithKeyboard(getText(Const.GET_PHONE), 12);
//    }
//
//
//}
