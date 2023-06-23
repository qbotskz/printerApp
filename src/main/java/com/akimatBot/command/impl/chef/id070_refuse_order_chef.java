//package com.akimatBot.command.impl.chef;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class id070_refuse_order_chef extends Command {
//    private Order order;
//    private int sum;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        parseOrder();
//        deleteMessage(updateMessageId);
//
//        if(order.getCourier()!= null){
//            //todo
//            // send it to admin
//            sendMessageWithKeyboardWithChatId(String.format(getText(181), order.getChef().getFullName(), order.getId(),
//                    updateMessageText,getFoodsFromOrder(), sum, order.getPaymentMethod().getName(1),
//                    order.getContact(), order.getLocation(),
//                    order.getCourier().getFullName()+", "+order.getCourier().getPhone() ), 79,372462586 );
//
//        }
//        else{
//            sendMessageWithKeyboardWithChatId(String.format(getText(181), order.getChef().getFullName(), order.getId(),
//                    updateMessageText,getFoodsFromOrder(), sum, order.getPaymentMethod().getName(1),
//                    order.getContact(), order.getLocation(),
//                    "Нет" ), 79,372462586);
//        }
//        order.setRefuseReason(updateMessageText);
//        order = orderRepository.save(order);
////        orderRepository.delete(order);
//        return EXIT;
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
