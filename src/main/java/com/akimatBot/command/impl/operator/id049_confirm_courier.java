//package com.akimatBot.command.impl.operator;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
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
//public class id049_confirm_courier extends Command {
//    private Order order;
//    private User user;
//    private int sum = 0;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        parseOrder();
//        if(order.isFinished()){
//            editMessage(String.format(getText(144), order.getId()),updateMessageId);
//            return EXIT;
//        }
//        long id = -1;
//        if(update.hasCallbackQuery()){
//
//            try{
//                id = Long.parseLong(update.getCallbackQuery().getData().split(",")[1]);
//            }
//            catch (Exception e){
//
//            }
//        }
//        user = userRepository.findById(id);
//        order.setCourier(user);
//        order.setDeliveryStatus(OrderStatus.getById(1));
//        order = orderRepository.save(order);
//        List<User> couriers = userRepository.findAllByRolesContains(new Role(3));
//        Thread sendingThread = new Thread(){
//            int messageCount = 0;
//            @SneakyThrows
//            @Override
//            public void run(){
//                for(User u:couriers){
//
//                    if(messageCount >= 10){
//                        try{
//                            Thread.sleep(1500);
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        messageCount = 0;
//                    }
//                    if(order.getCourier()==null){
//                        order.setCourier(u);
//                    }
//                    if(u.getId() == order.getCourier().getId()){
//                        continue;
//                    }
//                    sendMessage(String.format(getText(102), order.getCourier().getFullName(), order.getId()), u.getChatId());
//                    messageCount++;
//                }
//
//            }
//        };
//        sendingThread.start();
//        sendOrderToCourier();
//        editMessageWithKeyboard(update.getCallbackQuery().getMessage().getText(),updateMessageId, 65);
//        return false;
//    }
//
//    public void sendOrderToCourier() throws TelegramApiException {
////        List<String> list = new ArrayList<>(Arrays.asList(getButtonText(111),getButtonText(112),getButtonText(113),getButtonText(114) ,getButtonText(115)));
////        List<String> ids = new ArrayList<>(Arrays.asList("111", "112", "113", "114", "115"));
////
////        ButtonsLeaf stat = new ButtonsLeaf(list,list);
//        List<String> list = new ArrayList<>(Arrays.asList(OrderStatus.IN_THE_WAY.getName(getLanguageId())));
//        List<String> ids = new ArrayList<>(Collections.singletonList("28,"+String.valueOf(OrderStatus.IN_THE_WAY.getId())+",-"));
//
//        ButtonsLeaf stat = new ButtonsLeaf(list,ids, 5, 71,chatId);
////        String[] str = update.getCallbackQuery().getMessage().getText().split("\n");
////        StringBuilder stringBuilder = new StringBuilder();
////        int i =0;
////        for(String s:str){
////            if(i==0){
////                stringBuilder.append(String.format(getText(146), order.getId())).append("\n");
////                i++;
////                continue;
////            }
////            if(i==str.length-1){
////                break;
////            }
////            stringBuilder.append(s).append("\n");
////            i++;
////        }
//        if(!order.isFinished()) {
//            sendMessageWithKeyboardWithChatId(String.format(getText(172), order.getId(), getFoodList(), sum, order.getDeliveryMethod(1),
//                    order.getContact(), order.getLocation(), order.getPaymentMethod().getName(1), order.getCourier().getFullName()+", "+order.getCourier().getPhone()),
//                    stat.getListButtonInline(), user.getChatId());
//        }
//        else{
//            editMessage(String.format(getText(176), order.getId()), updateMessageId);
//        }
//
//    }
//    private String getFoodList(){
//        String text = "";
//        if(order.getFoods()==null||order.getFoods().isEmpty()){
//            return text;
//        }
//
//        Map<Food, Long> foodMap = countByClassicalLoop(order.getFoods());
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        sum = 0;
//        int count = 1;
//
//        while (itr.hasNext()) {
//            Map.Entry<Food, Long> entry = itr.next();
//            if(entry.getKey().getSpecialOfferSum()!=null){
//                text += count + ". " + entry.getKey().getNameRu() + " " + (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) + "₸ x " + entry.getValue() + "шт = " + (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue() + "₸\n";
//                sum += (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue();
//
//            }
//            else{
//                text += count + ". " + entry.getKey().getNameRu() + " " + entry.getKey().getFoodPrice(order.getUser().getCity()) + "₸ x " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue() + "₸\n";
//                sum += entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue();
//
//            }
//            count++;
//        }
//        return text;
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
//}
