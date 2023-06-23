//package com.akimatBot.command.impl.chef;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.DeliveryMethod;
//import com.akimatBot.entity.enums.OrderStatus;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
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
//public class id069_confirm_order_chef extends Command {
//    private Order order;
//    private List<User> chefs;
//    private int sum;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        parseOrder();
//        System.out.println("Hello from id069");
//        if(order.isFinished()){
//            editMessage(String.format(getText(144), order.getId()),updateMessageId);
//            return EXIT;
//        }
//        if(isButton(199)){
//            try{
//                if(order.getChef().getChatId().equals(chatId)){
//                    editMessage(getText(145), updateMessageId);
//                    return EXIT;
//                }
//            }
//            catch (Exception e){
//
//            }
//            if(order.getChef()!=null && !order.getChef().getChatId().equals(chatId)){
//                // другой уже взял
//                editMessage(String.format(getText(179), order.getChef().getFullName(), order.getId()), updateMessageId);
//                return EXIT;
//            }
//            chefs = userRepository.findAllByRolesContains(new Role(6));
//            order.setChef(userRepository.findByChatId(chatId));
//            Thread sendingThread = new Thread(){
//                int messageCount = 0;
//                @SneakyThrows
//                @Override
//                public void run(){
//                    for(User u:chefs){
//
//                        if(messageCount >= 10){
//                            try{
//                                Thread.sleep(1500);
//                            }
//                            catch (Exception e){
//                                e.printStackTrace();
//                            }
//                            messageCount = 0;
//                        }
//                        if(order.getChef()==null){
//                            order.setChef(u);
//                        }
//                        if(u.getId() == order.getChef().getId()){
//                            continue;
//                        }
//                        sendMessage(String.format(getText(179), order.getChef().getFullName(), order.getId()), u.getChatId());
//                        messageCount++;
//                    }
//
//                }
//            };
//            sendingThread.start();
//            sendMessage(String.format(getText(180), order.getChef().getFullName(), order.getId())+"\n"+formOrderMessage(), order.getOperator().getChatId());
//            order = orderRepository.save(order);
//            setButtons();
//        }
//        else if(isButton(200)){
//            //todo
//            order.setChef(userRepository.findByChatId(chatId));
//            order = orderRepository.save(order);
//            selectRejectionReason();
//        }
//        return EXIT;
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
//            count++;
//        }
//        String orderMessage;
//        if (order.getDeliveryMethod()== DeliveryMethod.SELF_PICKUP) {
//            orderMessage = String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()) + order.getDeliveryType(), order.getComment(),order.getSum());
//        } else {
//            orderMessage = String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getComment(),order.getDeliveryType(),order.getSum());
//        }
//
//        return orderMessage;
//    }
//    private void setButtons() throws TelegramApiException{
//        List<String> list = new ArrayList<>(Arrays.asList(OrderStatus.COOKED.getName(getLanguageId())));
//        List<String> ids = new ArrayList<>(Collections.singletonList("72,"+String.valueOf(OrderStatus.COOKED.getId())+",-"));
//
//        ButtonsLeaf stat = new ButtonsLeaf(list,ids, 5, 80,chatId);
//
//
//        if(order.getDeliveryStatus()!=null){
//            editMessageWithKeyboard(String.format(getText(71), order.getId())+"\n"+formOrderMessage(), updateMessageId, stat.getListButtonInline());
//        }
//        else{
//            editMessageWithKeyboard(String.format(getText(71), order.getId())+"\n"+formOrderMessage(), updateMessageId, stat.getListButtonInline());
//        }
//
//    }
//    private void selectRejectionReason() throws TelegramApiException {
//        List<String> list = new ArrayList<>(Arrays.asList(getButtonText(201),getButtonText(202),getButtonText(203), getButtonText(216)));
//        List<String> ids = new ArrayList<>(Arrays.asList("201", "202", "203", "216"));
//
//        ButtonsLeaf stat = new ButtonsLeaf(list,list);
////        String[] str = update.getCallbackQuery().getMessage().getText().split("\n");
//
//        editMessageWithKeyboard(String.format(getText(71), order.getId(), getFoodsFromOrder(), sum,order.getLocation(),"todo"),updateMessageId,stat.getListButtonInline());
//
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
