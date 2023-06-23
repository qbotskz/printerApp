//package com.akimatBot.command.impl.operator;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.DeliveryMethod;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.OrderRepository;
//import com.akimatBot.repository.repos.UserRepository;
//import com.akimatBot.utils.ButtonsLeaf;
//import lombok.SneakyThrows;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class id024_test_operator extends Command {
//    private final OrderRepository orderRepository = TelegramBotRepositoryProvider.getOrderRepository();
//    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
//    private List<User> operators;
//    private Order order;
//    private ArrayList<String> finalList;
//    private ArrayList<String> finalList2;
//    private ButtonsLeaf buttonsLeaf;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        parseOrder();
//        if(order.isFinished()){
//            editMessage(String.format(getText(144), order.getId()),updateMessageId);
//            return EXIT;
//        }
//        if(hasCallbackQuery()){
//
//
//            if(isButton(99) ){
//                try{
//                    if(order.getOperator().getChatId().equals(chatId)){
//                        //
//                        editMessage(getText(145), updateMessageId);
//                        return EXIT;
//                    }
//                }
//                catch (Exception e){
//
//                }
//
//                if(order.getDeliveryMethod()==DeliveryMethod.BY_COURIER){
//                    editMessageWithKeyboard(update
//                                    .getCallbackQuery()
//                                    .getMessage().getText(),
//                            updateMessageId, 64);
//                }
//                else{
//                    editMessage(update
//                            .getCallbackQuery()
//                            .getMessage().getText(), updateMessageId);
//                }
//                if(order.getOperator()!=null&& !order.getOperator().getChatId().equals(chatId)){
//                    // другой уже взял
//                    editMessage(String.format(getText(143), order.getOperator().getFullName(), order.getId()), updateMessageId);
//
//                    return EXIT;
//                }
//
////                editMessageWithKeyboard(update
////                                .getCallbackQuery()
////                                .getMessage().getText(),
////                        updateMessageId, 98);
//
//                operators = userRepository.findAllByRolesContains(new Role(4));
//                order.setOperator(userRepository.findByChatId(chatId));
//
//                Thread sendingThread = new Thread(){
//                    int messageCount = 0;
//                    @SneakyThrows
//                    @Override
//                    public void run(){
//                        for(User u:operators){
//
//                            if(messageCount >= 10){
//                                try{
//                                    Thread.sleep(1500);
//                                }
//                                catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                                messageCount = 0;
//                            }
//                            if(order.getOperator()==null){
//                                order.setOperator(u);
//                            }
//                            if(u.getId() == order.getOperator().getId()){
//                                continue;
//                            }
//                            try{
//                                sendMessage(String.format(getText(143), order.getOperator().getFullName(), order.getId()), u.getChatId());
//                                messageCount++;
//                            }
//                            catch (Exception e){
//                                System.out.println(u.getChatId()+" - CHAT NOT FOUND");
//                            }
//
//                        }
//
//                    }
//                };
//                sendingThread.start();
//
//                order = orderRepository.save(order);
//
//                finishOrder();
//
//            }
//            else if(isButton(100)){
//                //todo
//                selectRejectionReason();
//            }
//
//            else if(isButton(157)){
//                List<User> users = userRepository.findAllByRolesContains(new Role(3));
//                String finalMessageToCourier = update.getCallbackQuery().getMessage().getText();
//                Thread sendingThread = new Thread() {
//                    int messageCount = 0;
//
//                    @SneakyThrows
//                    @Override
//                    public void run() {
//                        for (User u : users) {
//
//                            if (messageCount >= 10) {
//                                try {
//                                    Thread.sleep(1500);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                messageCount = 0;
//                            }
//
//                            try{
//                                sendMessageWithKeyboardWithChatId(finalMessageToCourier, 39, u.getChatId());
//                                messageCount++;
//                            }
//                            catch (Exception e){
//                                System.out.println(u.getChatId()+" - CHAT NOT FOUND");
//                            }
//                        }
//
//                    }
//                };
//                sendingThread.start();
//            }
//
//            else if(isButton(240)){
//                showAllManagers();
//            }
//
//        }
//
//        return false;
//    }
//    private void selectRejectionReason() throws TelegramApiException {
//        List<String> list = new ArrayList<>(Arrays.asList(getButtonText(158),getButtonText(159),getButtonText(160)));
//        List<String> ids = new ArrayList<>(Arrays.asList("158", "159", "160"));
//
//        ButtonsLeaf stat = new ButtonsLeaf(list,list,5,85,chatId);
//        String[] str = update.getCallbackQuery().getMessage().getText().split("\n");
//        StringBuilder stringBuilder = new StringBuilder();
//        int i =0;
//        for(String s:str){
//            if(i==0){
//                stringBuilder.append(String.format(getText(147), order.getId())).append("\n");
//                i++;
//                continue;
//            }
//            if(i==str.length-1){
//                break;
//            }
//            stringBuilder.append(s).append("\n");
//            i++;
//
//        }
//        editMessageWithKeyboard(update.getCallbackQuery().getMessage().getText(), updateMessageId, stat.getListButtonInline());
//
//    }
//    public void showAllManagers() throws TelegramApiException {
//        List<String> list = new ArrayList<>();
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        List<User> managers = userRepository.findAllByRolesContains(new Role(6));
//        managers.forEach((e) -> {
//            finalList.add(e.getFullName());
//            finalList2.add(String.valueOf("95,"+e.getId()));
//        });
//
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2);
//        editMessageWithKeyboard(String.format(getText(226), order.getId()) +"\n"+ formOrderMessage(),updateMessageId, buttonsLeaf.getListButtonInline());
//        /// return toDeleteKeyboard(sendMessageWithKeyboard(getText(11), buttonsLeaf.getListButtonInline()));
//    }
//    private String formOrderMessage() throws TelegramApiException {
//
//        order = orderRepository.save(order);
////        String text = "ЗАКАЗ №"+order.getId();
//        String text = "";
//
//        Map<Food, Long> foodMap = countByClassicalLoop(order.getFoods());
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        int sum = 0;
//        int count = 1;
//        while (itr.hasNext()) {
//            Map.Entry<Food, Long> entry = itr.next();
//            text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + entry.getKey().getFoodPrice(order.getUser().getCity()) + "₸ x " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue() + "₸\n";
//            sum += entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue();
//
//
//            count++;
//
//        }
////        String deliveryType = "Менеджер позвонит вам по поводу цены доставки";
//        String orderMessage;
//        if (order.getDeliveryMethod()==DeliveryMethod.SELF_PICKUP) {
//            orderMessage = String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId())+" "+order.getDeliveryType() , order.getComment(),order.getSum());
//        } else {
//            orderMessage = String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getComment(),order.getDeliveryType(),order.getSum());
//        }
//
//        return orderMessage;
//    }
//    public void parseOrder(){
//        String[] myString = getCallbackQuery().getMessage().getText().split("\n");
//        Pattern pattern = Pattern.compile("(?<=№|#).[0-9]+");
//
//        Matcher matcher = pattern.matcher(myString[0]);
//        int orderNum = -1;
//        if (matcher.find())
//        {
//            orderNum = Integer.parseInt(matcher.group(0).replaceAll("\\s+",""));
//            System.out.println(orderNum);
//        }
//        order = orderRepository.findOrderById(orderNum);
//    }
//
//
//    private void finishOrder() throws TelegramApiException {
//
//
//        String text = "";
//        Map<Food, Long> foodMap = countByClassicalLoop(order.getFoods());
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
//        String message;
//        String messageToCourier;
//        sum = order.getSum();
//        if (order.getDeliveryMethod()==DeliveryMethod.SELF_PICKUP) {
//            message = String.format(getText(77), order.getId()) + "\n" + String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getComment(), order.getSum(),order.getPaymentNalom());
//            messageToCourier = String.format(getText(78), order.getId()) + "\n" + String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getComment(),order.getSum(),order.getPaymentNalom());
//        } else {
//            messageToCourier = String.format(getText(78), order.getId()) + "\n" + String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getComment(),order.getDeliveryType(),order.getSum());
//
//            message = String.format(getText(77), order.getId()) + "\n" + String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getComment(),order.getDeliveryType(),order.getSum());
//        }
//        if(order.getUser()!=null){
//            sendMessageWithKeyboardWithChatId(message, 2, order.getUser().getChatId());
//        }
//        if(order.getDeliveryMethod().equals(DeliveryMethod.BY_COURIER)){
//            List<User> users = userRepository.findAllByRolesContains(new Role(3));
//            String finalMessageToCourier = messageToCourier;
//            Thread sendingThread = new Thread() {
//                int messageCount = 0;
//
//                @SneakyThrows
//                @Override
//                public void run() {
//                    for (User u : users) {
//
//                        if (messageCount >= 10) {
//                            try {
//                                Thread.sleep(1500);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            messageCount = 0;
//                        }
//
//                        try{
//                            sendMessageWithKeyboardWithChatId(finalMessageToCourier, 39, u.getChatId());
//                            messageCount++;
//
//                        }
//                        catch(Exception e){
//                            System.out.println(u.getChatId()+" - CHAT NOT FOUND");
//                        }
//
//                    }
//
//                }
//            };
//
//            sendingThread.start();
//            TimerTask timerTask = new TimerTask() {
//                @Override
//                public void run() {
//                    order = orderRepository.findOrderById(order.getId());
//
//                    if(order.getCourier()==null){
//                        Thread sendingThread = new Thread() {
//                            int messageCount = 0;
//
//                            @SneakyThrows
//                            @Override
//                            public void run() {
//                                for (User u : users) {
//
//                                    if (messageCount >= 10) {
//                                        try {
//                                            Thread.sleep(1500);
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                        messageCount = 0;
//                                    }
//
//                                    try{
//                                        sendMessageWithKeyboardWithChatId(finalMessageToCourier, 39, u.getChatId());
//                                        messageCount++;
//
//                                    }
//                                    catch (Exception e){
//                                        System.out.println(u.getChatId()+" - CHAT NOT FOUND");
//                                    }
//                                }
//
//                            }
//                        };
//                        sendingThread.start();
//                    }
//                    else{
//                        Thread.currentThread().stop();
//                    }
//                }
//            };
//            Timer timer = new Timer();
//            timer.scheduleAtFixedRate(timerTask,1000*60*5,1000*60*5);
//        }
//        List<User> users = userRepository.findAllByRolesContains(new Role(6));
//        String finalMessageToCourier = messageToCourier;
//        Thread sendingThreadToChefs = new Thread() {
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
//                        sendMessageWithKeyboardWithChatId(finalMessageToCourier, 78, u.getChatId());
//                        messageCount++;
//                    }
//                    catch (Exception e){
//                        System.out.println(u.getChatId()+" - CHAT NOT FOUND");
//                    }
//                }
//
//            }
//        };
//        sendingThreadToChefs.start();
//
//    }
//
//
//
//
//
//}
