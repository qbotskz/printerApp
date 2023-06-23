//package com.akimatBot.command.impl;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.DeliveryMethod;
//import com.akimatBot.entity.enums.OrderType;
//import com.akimatBot.entity.enums.PaymentMethod;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import lombok.SneakyThrows;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//public class id092_order_from_website extends Command {
//    private Order order;
//    private String rawOrder;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        if(isButton(231)){
//
//            User user = userRepository.findByChatId(chatId);
//            Map<String, Integer> foodMap = new HashMap<String, Integer>();
//            ArrayList<Food> foodListToAdd = new ArrayList<>();
//
//            rawOrder = update.getCallbackQuery().getMessage().getText();
//            String[] splitOrder = rawOrder.split("===================================");
//            ArrayList<String> splitOrderList = new ArrayList<>(Arrays.asList(splitOrder));
//
//
//
//
//            // Making list of food (String)
//            ArrayList<String> foods = new ArrayList<>(Arrays.asList(splitOrderList.get(1)
//                    .split("\\d\\."))
//                    .stream().filter(x-> !x.equals("\n") )
//                    .map(x->x=x.replace("\n", ""))
//                    .map(x->x=x.trim())
//                    .collect(Collectors.toList()));
//
//
//            // Making foodMap and foodList
//            for(String f:foods){
//                int foodNum = 0;
//                Pattern pattern = Pattern.compile("\\d+шт");
//                Matcher matcher = pattern.matcher(f);
//                if (matcher.find())
//                {
//                    String foodNumString = matcher.group(0);
//                    foodNum = Integer.parseInt(foodNumString.replaceAll("[^0-9.]", ""));
//                }
//                pattern = Pattern.compile("\\d");
//                matcher = pattern.matcher(f);
//                int index = 0;
//                if(matcher.find()){
//
//                    index = matcher.start();
//                }
//                f = f.substring(0,index).trim();
//                Food food = foodRepository.findFoodByNameRu(f);
//                for(int i =0;i<foodNum;i++){
//                    foodListToAdd.add(food);
//                }
//
//                foodMap.put(f, foodNum);
//
//            }
//
//            order = new Order();
//            order.setFoods(foodListToAdd);
//            order.setUser(user);
//            order.setOperator(user);
//            order.setFinished(false);
//            order.setOrderType(OrderType.WEBSITE);
//            order.setOrderedDate(new Date());
//            order.setDeliveryMethod(DeliveryMethod.BY_COURIER);
//            order.setPaymentMethod(PaymentMethod.DEFAULT);
//
//            // Order details mapping to map except food
//            String[] pairs = splitOrderList.get(2).split("\n");
//            Map<String, String> orderDetailsMap = new HashMap<String, String>();
//            for (int i=1;i<pairs.length;i++) {
//                String pair = pairs[i];
//                String[] keyValue = pair.split(":");
//                orderDetailsMap.put(keyValue[0], keyValue[1]);
//                if(keyValue[0].equals("Адрес")){
//                    order.setLocation(keyValue[1]);
//                }
//                else if(keyValue[0].equals("Общая сумма заказа")){
//                    order.setSum(Integer.parseInt(keyValue[1].replaceAll("[^0-9.]", "")));
//                }
//                else if(keyValue[0].equals("Контакт")){
//                    order.setContact(keyValue[1]);
//                }
//            }
//
//            order = orderRepository.save(order);
//
//            System.out.println(order);
//
//
//
//            String from = "vyigrok@gmail.com";
//
//            // Sender's email ID needs to be mentioned
//            String to = "orkenfreeman@gmail.com";
//
//            // Assuming you are sending email from through gmails smtp
//            String host = "smtp.gmail.com";
//
//            // Get system properties
//            Properties properties = System.getProperties();
//
//            // Setup mail server
//            properties.put("mail.smtp.host", host);
//            properties.put("mail.smtp.port", "465");
//            properties.put("mail.smtp.ssl.enable", "true");
//            properties.put("mail.smtp.auth", "true");
//
//            // Get the Session object.// and pass username and password
//            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//
//                protected PasswordAuthentication getPasswordAuthentication() {
//
//
//                    return new PasswordAuthentication("vyigrok@gmail.com", "Tric5tric2");
//
//                }
//
//            });
//
//            session.setDebug(false);
//
//            try {
//                MimeMessage message = new MimeMessage(session);
//                message.setFrom(new InternetAddress(from));
//                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//                message.setSubject("This is the Subject Line!");
//                message.setText("ПОСТУПИЛ ЗАКАЗ №"+order.getId()+rawOrder.substring(rawOrder.indexOf('\n')+1));
//                editMessage("Оформляем заказ...", updateMessageId);
//                System.out.println("sending...");
//                Transport.send(message);
//                System.out.println("Sent message successfully....");
//            } catch (MessagingException mex) {
//                mex.printStackTrace();
//            }
//            editMessageWithKeyboard("ЗАКАЗ №"+order.getId()+rawOrder.substring(rawOrder.indexOf('\n')+1), updateMessageId, 64);
//            sendOrderToCourier();
//
//
//
//        }
//        else{
//            deleteMessage(updateMessageId);
//            System.out.println("ORDER DELETED");
//        }
//        return false;
//    }
//
//    public void sendOrderToCourier(){
//        List<User> users = userRepository.findAllByRolesContains(new Role(3));
//        String finalMessageToCourier = "ВАМ ПОСТУПИЛ ЗАКАЗ №"+order.getId()+rawOrder.substring(rawOrder.indexOf('\n')+1);
//
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
//                        sendMessageWithKeyboardWithChatId(finalMessageToCourier, 39, u.getChatId());
//                        messageCount++;
//
//                    }
//                    catch(Exception e){
//                        System.out.println(u.getChatId()+" - CHAT NOT FOUND");
//                    }
//
//                }
//
//            }
//        };
//        sendingThread.start();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                order = orderRepository.findOrderById(order.getId());
//
//                if(order.getCourier()==null || order.isFinished()){
//                    Thread sendingThread = new Thread() {
//                        int messageCount = 0;
//
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
//
//                                try{
//                                    sendMessageWithKeyboardWithChatId(finalMessageToCourier, 39, u.getChatId());
//                                    messageCount++;
//
//                                }
//                                catch (Exception e){
//                                    System.out.println(u.getChatId()+" - CHAT NOT FOUND");
//                                }
//                            }
//
//                        }
//                    };
//                    sendingThread.start();
//                }
//                else{
//                    Thread.currentThread().stop();
//                }
//            }
//        };
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(timerTask,1000*60*5,1000*60*5);
//    }
//}
