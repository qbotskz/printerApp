//package com.akimatBot.command.impl;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.OrderRepository;
//import com.akimatBot.repository.repos.UserRepository;
//import com.akimatBot.utils.ButtonsLeaf;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.*;
//
//public class id006_order_records extends Command {
//    private final OrderRepository orderRepository = TelegramBotRepositoryProvider.getOrderRepository();
//    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
//    private int lastMsgId;
//    private ButtonsLeaf buttonsLeaf;
//    private User user;
//    private Order order;
//    private List<Order> orders;
//    private List<String> finalList;
//    private List<String> finalList2;
//
//    // this command just shows order records of users by chatId
//    // Here users can reorder, cancel an order
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                deleteMessage(updateMessageId);
//                deleteMessage(lastMsgId);
//                user = userRepository.findByChatId(chatId);
//                orders = orderRepository.getOrdersByUserOrderByIdAsc(user);
//                if (orders == null || orders.size() == 0) {
//                    sendMessage(Const.NO_ORDERS);
//                    return EXIT;
//                }
//                showAllOrders();
//                waitingType = WaitingType.CHOOSE_ORDER;
//                return COMEBACK;
//            case CHOOSE_ORDER:
//
//                if(hasCallbackQuery()){
//                    if(buttonsLeaf.isNext(updateMessageText)){
//
//                        editMessageWithKeyboard(getText(24), updateMessageId, buttonsLeaf.getListButtonInline());
//
//                    }
//                    else{
//                        deleteMessage(updateMessageId);
//                        deleteMessage(lastMsgId);
//                        order = orders.stream()
//                                .filter(botEntity -> updateMessageText.equals(String.valueOf(botEntity.getId())))
//                                .findAny()
//                                .orElse(null);
//                        System.out.println(order.getId());
//                        showOrder();
//                        waitingType = WaitingType.GET_ORDER;
//                    }
//                }
//                else{
//                    lastMsgId = wrongData();
//                    showAllOrders();
//                }
//                return COMEBACK;
//
//            case GET_ORDER:
//                deleteMessage(updateMessageId);
//                deleteMessage(lastMsgId);
//                if(hasCallbackQuery()){
//                    if(isButton(67)){
//                        showAllOrders();
//                        waitingType = WaitingType.CHOOSE_ORDER;
//                    }
//                    else if(isButton(72)){
//                        int id = (int) order.getId();
//
////                        List<MessageToEdit> messageToEdits = messageToEditRepo.findMessageToEditsByOrder(order);
////                        for(MessageToEdit m:messageToEdits){
////
////                            messageToEditRepo.delete(m);
////                        }
//                        if(order.getCourier()!=null){
//                            sendMessage(String.format(getText(101), order.getUser().getFullName(), order.getId()),order.getCourier().getChatId());
//                        }
//                        if(order.getOperator()!=null){
//                            sendMessage(String.format(getText(101), order.getUser().getFullName(), order.getId()),order.getOperator().getChatId());
//                        }
//                        orderRepository.delete(order);
//                        sendMessage(String.format(getText(32), id));
//                        return EXIT;
//                    }
//                    else if(isButton(73)){
//                        String text = "";
//
//                        // If button with id 73 was clicked(In my case it is reorder)
//                        // It will reorder the order that customer choose
//                        user.setFoods(new ArrayList<Food>(order.getFoods()));
//                        user = userRepository.save(user);
//                        user = userRepository.findByChatId(chatId);
//                        Map<Food, Long> foodMap = countByClassicalLoop(user.getFoods());
//                        List<Food> keys = new ArrayList<>(foodMap.keySet());
//                        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//                        int sum = 0;
//                        int count = 1;
//                        while (itr.hasNext()) {
//                            Map.Entry<Food, Long> entry = itr.next();
//                            if(entry.getKey().getSpecialOfferSum()!=null){
//                                // getSpecialOfferSum()!=null means that current food has a special offer
//
//                                text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) + "₸ x " + entry.getValue() + "шт = " + (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue() + "₸\n";
//                                sum += (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue();
//
//                            }
//                            else{
//                                text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + entry.getKey().getFoodPrice(order.getUser().getCity()) + "₸ x " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue() + "₸\n";
//                                sum += entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue();
//
//                            }
//                            count++;
//                        }
//
//                        toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(134), text,sum),11));
//
//                    }
//                }
//                else{
//                    lastMsgId = wrongData();
//                    showOrder();
//                }
//                return COMEBACK;
//
//        }
//        return EXIT;
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//    public int showAllOrders() throws TelegramApiException {
//        List<String> list = new ArrayList<>();
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        orders.forEach((e) -> {
//            finalList.add(e.getId() + " | " + e.getOrderedDate());
//            finalList2.add(String.valueOf(e.getId()));
//        });
//        buttonsLeaf = new ButtonsLeaf(finalList,finalList2,10);
//        return toDeleteKeyboard(sendMessageWithKeyboard(Const.SHOW_ALL_ORDERS, buttonsLeaf.getListButtonInline()));
//    }
//
//    public void showOrder() throws TelegramApiException {
//
//        String text = "";
//        String message="";
//        if(order.getRefuseReason()==null || order.getRefuseReason().equals("null")){
//            if(order.isFinished()){
//                message+=getText(173)+"\n";
//            }
//            else{
//                message+=getText(174)+"\n";
//            }
//        }
//        else{
//            message+=getText(175)+"\n";
//        }
//        Map<Food, Long> foodMap = countByClassicalLoop(order.getFoods());
//
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        int sum = 0;
//        int count = 1;
//        while (itr.hasNext()) {
//            Map.Entry<Food, Long> entry = itr.next();
//            if(entry.getKey().getSpecialOfferSum()!=null){
//                text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) + "₸ x " + entry.getValue() + "шт = " + (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue() + "₸\n";
//                sum += (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue();
//
//            }
//            else{
//                text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + entry.getKey().getFoodPrice(order.getUser().getCity()) + "₸ x " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue() + "₸\n";
//                sum += entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue();
//
//            }
//            count++;
//        }
//
//        if (order.getPaymentNalom() != null) {
//            message += String.format(getText(85), order.getId()) + "\n" + String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getComment(), order.getDeliveryType(),order.getSum() ,order.getPaymentNalom());
//        } else {
//            message += String.format(getText(85), order.getId()) + "\n" + String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getComment(), order.getDeliveryType(),order.getSum());
//        }
//        Date date = new Date();
//
//        if((date.before(addTime(order.getOrderedDate(), 0 , 15)) && !order.isFinished()) || (date.after(addTime(order.getOrderedDate(), 1, 30)) && date.before(addTime(order.getOrderedDate(), 4, 30)) && !order.isFinished()) ){
//            sendMessageWithKeyboard(message, 25);
//        }
//        else if(!order.isFinished()){
//            sendMessageWithKeyboard(message, 35);
//        }
//        else{
//            sendMessageWithKeyboard(message, 22);
//        }
//    }
//
//    public static Date addTime(Date date, int hours, int minute) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.HOUR, hours);
//        cal.add(Calendar.MINUTE, minute);
//        cal.set(Calendar.SECOND, 00);
//        cal.set(Calendar.MILLISECOND, 0000);
//        return cal.getTime();
//    }
//}
