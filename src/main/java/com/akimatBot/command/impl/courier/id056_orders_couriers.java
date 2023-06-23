//package com.akimatBot.command.impl.courier;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.OrderStatus;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.utils.ButtonsLeaf;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.*;
//
//public class id056_orders_couriers extends Command {
//    private Order order;
//    private List<Order> orders;
//    private List<String> finalList;
//    private List<String> finalList2;
//    private ButtonsLeaf buttonsLeaf;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                if(isButton(171)){
//                    orders = orderRepository.findByFinishedFalseAndCourierIsNullAndOperatorIsNotNullOrderByIdAsc();
//
//                }
//                else if(isButton(172)){
//                    orders = orderRepository.findByFinishedFalseAndCourierOrderByIdAsc(userRepository.findByChatId(chatId));
//                }
//                if (orders == null || orders.size() == 0) {
//                    sendMessage(getText(151));
//                    return EXIT;
//                }
//                showAllOrders();
//                waitingType = WaitingType.CHOOSE_ORDER;
//                return COMEBACK;
//            case CHOOSE_ORDER:
//                if(hasCallbackQuery()){
//                    if(buttonsLeaf.isNext(updateMessageText)){
//                        editMessageWithKeyboard(getText(Const.SHOW_ALL_ORDERS), updateMessageId, buttonsLeaf.getListButtonInline());
//                    }
//                    else{
//                        deleteMessage(updateMessageId);
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
//                    showAllOrders();
//                }
//                return COMEBACK;
//            case GET_ORDER:
//                deleteMessage(updateMessageId);
//                if(hasCallbackQuery()){
//                    if(isButton(67)){
//                        showAllOrders();
//                        waitingType = WaitingType.CHOOSE_ORDER;
//                    }
//                }
//                else{
//                    showOrder();
//                }
//                return COMEBACK;
//        }
//        return false;
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
//        Map<Food, Long> foodMap = countByClassicalLoop(order.getFoods());
//
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        int sum = 0;
//        int count = 1;
//        while (itr.hasNext()) {
//            Map.Entry<Food, Long> entry = itr.next();
//            text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + entry.getKey().getFoodPrice(order.getUser().getCity()) + "₸ x " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue() + "₸\n";
//            sum += entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue();
//            count++;
//        }
//        String message="";
//        if (order.getPaymentNalom() != null) {
//
//            try{
//                message = String.format(getText(85), order.getId()) + "\n" + String.format(getText(153), text, sum, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getPaymentNalom(), order.getCourier().getFullName(), order.getCourier().getPhone());
//            }
//            catch (NullPointerException e){
//                message = String.format(getText(85), order.getId()) + "\n" + String.format(getText(153), text, sum, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getPaymentNalom(), null, null);
//
//            }
//        } else {
//            try{
//                message = String.format(getText(85), order.getId()) + "\n" + String.format(getText(152), text, sum, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getCourier().getFullName(), order.getCourier().getPhone());
//            }
//            catch (NullPointerException e){
//                message = String.format(getText(85), order.getId()) + "\n" + String.format(getText(152), text, sum, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), null, null);
//
//            }
//        }
//
//        List<String> list;
//        List<String> ids;
//        if(order.getDeliveryStatus()==null){
//            list = new ArrayList<>(Arrays.asList(OrderStatus.IN_THE_WAY.getName(getLanguageId())));
//            ids = new ArrayList<>(Collections.singletonList("28,"+String.valueOf(OrderStatus.IN_THE_WAY.getId())+",-"));
//
//        }
//
//        else{
//            list = new ArrayList<>(Arrays.asList(OrderStatus.getById(order.getDeliveryStatus().getId()+1).getName(1)));
//            ids = new ArrayList<>(Collections.singletonList("28,"+String.valueOf((order.getDeliveryStatus().getId()+1)+",-")));
//        }
//
//        ButtonsLeaf stat = new ButtonsLeaf(list,ids, 5, 71,chatId);
//        if(order.getCourier()==null){
//            sendMessageWithKeyboard(message, 39);
//        }
//        else if(order.getCourier().getChatId().equals(chatId)) sendMessageWithKeyboard(message, stat.getListButtonInline());
//        else{
//            sendMessageWithKeyboard(message, 39);
//        }
//    }
//}
