//package com.akimatBot.command.impl.courier;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.OrderStatus;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import lombok.SneakyThrows;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class id085_custom_reason_for_exiting extends Command {
//    private Order order;
//    private int sum;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                parseOrder();
//                deleteMessage(updateMessageId);
//                sendMessage(String.format(getText(201), order.getId()));
//                waitingType = WaitingType.SET_TEXT;
//                return COMEBACK;
//            case SET_TEXT:
//                deleteMessage(updateMessageId);
//                if(update.hasMessage()&&update.getMessage().hasText()){
//
//                    String name = order.getCourier().getFullName();
//                    order.setCourier(null);
//                    order.setDeliveryStatus(OrderStatus.ACCEPTED_ORDER);
//                    order = orderRepository.save(order);
//                    List<User> users = userRepository.findAllByRolesContains(new Role(4));
//                    Thread sendingThread = new Thread() {
//                        int messageCount = 0;
//                        @SneakyThrows
//                        @Override
//                        public void run() {
//                            for (User u : users) {
//
//                                if (messageCount >= 10) {
//                                    try {
//                                        Thread.sleep(1500);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                    messageCount = 0;
//                                }
//                                if(order.getPaymentNalom()==null){
//                                    sendMessageWithKeyboardWithChatId(String.format(getText(159),  name,order.getId(), updateMessageText) +"\n"
//                                                    + String.format(getText(162), getFoodsFromOrder(), sum, order.getUser().getPhone(), order.getLocation(),order.getPaymentMethod().getName(getLanguageId()), order.getDeliveryStatus().getName(getLanguageId()) ),
//                                            64,
//                                            u.getChatId());
//                                }
//                                else{
//                                    sendMessageWithKeyboardWithChatId(String.format(getText(159),  name,order.getId(), updateMessageText) +"\n"
//                                                    + String.format(getText(163), getFoodsFromOrder(), sum, order.getUser().getPhone(), order.getLocation(),order.getPaymentMethod().getName(getLanguageId()),order.getPaymentNalom() ,order.getDeliveryStatus().getName(getLanguageId()) ),
//                                            64,
//                                            u.getChatId());
//                                }
////                    sendMessageWithKeyboardWithChatId(String.format(getText(159), name, order.getId(), updateMessageText), 64, u.getChatId());
//                                messageCount++;
//                            }
//
//                        }
//                    };
//                    List<User> couriers = userRepository.findAllByRolesContains(new Role(3));
//                    Thread sendingThreadToCouriers = new Thread() {
//                        int messageCount = 0;
//                        @SneakyThrows
//                        @Override
//                        public void run() {
//                            for (User u : couriers) {
//
//                                if (messageCount >= 10) {
//                                    try {
//                                        Thread.sleep(1500);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                    messageCount = 0;
//                                }
//
//                                if(!u.getChatId().equals(chatId)){
//                                    if(order.getPaymentNalom()==null){
//                                        sendMessageWithKeyboardWithChatId(String.format(getText(161),  name,order.getId(), updateMessageText) +"\n"
//                                                        + String.format(getText(162), getFoodsFromOrder(), sum, order.getUser().getPhone(), order.getLocation(),order.getPaymentMethod().getName(getLanguageId()), order.getDeliveryStatus().getName(getLanguageId()) ),
//                                                39,
//                                                u.getChatId());
//                                    }
//                                    else{
//                                        sendMessageWithKeyboardWithChatId(String.format(getText(161),  name,order.getId(), updateMessageText) +"\n"
//                                                        + String.format(getText(163), getFoodsFromOrder(), sum, order.getUser().getPhone(), order.getLocation(),order.getPaymentMethod().getName(getLanguageId()),order.getPaymentNalom() ,order.getDeliveryStatus().getName(getLanguageId()) ),
//                                                39,
//                                                u.getChatId());
//                                    }
//                                    messageCount++;
//                                }
//
//                            }
//
//                        }
//                    };
//                    sendingThread.start();
//                    sendingThreadToCouriers.start();
//
//                    if(order.getUser()!=null) sendMessage(String.format(getText(160), order.getId(), name, updateMessageText), order.getUser().getChatId());
//                    return EXIT;
//                }
//        }
//        return false;
//    }
//    public void parseOrder() {
//        String[] myString = getCallbackQuery().getMessage().getText().split("\n");
//        Pattern pattern = Pattern.compile("(?<=№|#).[0-9]+");
//
//        Matcher matcher = pattern.matcher(myString[0]);
//        int orderNum = -1;
//        if (matcher.find()) {
//            orderNum = Integer.parseInt(matcher.group(0).replaceAll("\\s+", ""));
//            System.out.println(orderNum);
//        }
//        order = orderRepository.findOrderById(orderNum);
//    }
//    private String getFoodsFromOrder(){
//        String text = "";
//        Map<Food, Long> foodMap = countByClassicalLoop(order.getFoods());
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        sum = 0;
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
//        sum = order.getSum();
//        return text;
//    }
//}
