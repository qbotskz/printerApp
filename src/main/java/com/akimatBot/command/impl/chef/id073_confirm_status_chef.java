//package com.akimatBot.command.impl.chef;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Cashback;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.DeliveryMethod;
//import com.akimatBot.entity.enums.OrderStatus;
//import com.akimatBot.utils.ButtonsLeaf;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class id073_confirm_status_chef extends Command {
//    private int sum;
//    private Order order;
//    private Cashback cashback = cashbackRepository.findTopByOrderByIdDesc();
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        parseOrder();
//        if(!order.getChef().getChatId().equals(chatId)){
//            return EXIT;
//        }
//
//        if(order.isFinished()){
//            editMessage(String.format(getText(157), order.getId()), updateMessageId);
//            return EXIT;
//        }
//        int statusId = Integer.parseInt(updateMessageText.split(",")[1]);
//
//        String stat = OrderStatus.getById(statusId).getName(getLanguageId());
////        order.setDeliveryStatus(OrderStatus.getById(statusId));
//
//        if(statusId == OrderStatus.COOKED.getId()){
//            order.setFinished(true);
//            order = orderRepository.save(order);
//            editMessage(String.format(getText(184), order.getId())+"\n"+formOrderMessage(),updateMessageId);
//            sendMessage(String.format(getText(183), order.getId())+"\n"+formOrderMessage(), order.getOperator().getChatId());
//            if(order.getCourier()!=null){
//                sendMessage(String.format(getText(183), order.getId(), getFoodsFromOrder(),
//                        sum, order.getContact(),
//                        order.getLocation(), order.getPaymentMethod().getName(1)), order.getCourier().getChatId());
//            }
////            int cashbackAmount=0;
////            if(cashback!=null){
////                cashbackAmount = (order.getSum()/100)*cashback.getCashbackPercentage();
////                User customer = order.getUser();
////                if(customer.getCashback()!=null) {
////                    customer.setCashback(customer.getCashback()+cashbackAmount);
////                }
////                else{
////                    customer.setCashback(cashbackAmount);
////                }
////                userRepository.save(customer);
////                if(cashbackAmount!=0) {
////                    sendMessage(String.format(getText(205), cashbackAmount), order.getUser().getChatId() );
////                }
////            }
//            return EXIT;
//        }
//
//        return EXIT;
//    }
//    private void setButtons(int id) throws TelegramApiException{
//        String confirmData = "73," + id;
//        List<String> list = new ArrayList<>(Arrays.asList(OrderStatus.getById(id+1).getName(getLanguageId())));
//        List<String> ids = new ArrayList<>(Arrays.asList("28,"+String.valueOf(id+1)+",-"));
//
//        ButtonsLeaf stat = new ButtonsLeaf(list,ids, 5, 71,chatId);
//
//        editMessageWithKeyboard(String.format(getText(71), order.getId(), getFoodsFromOrder(), sum,OrderStatus.getById(id).getName(getLanguageId())), updateMessageId, stat.getListButtonInline());
//
//
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
//            orderMessage = String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId())+order.getDeliveryType(), order.getComment(),order.getSum());
//        } else {
//            orderMessage = String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getComment(),order.getDeliveryType(),order.getSum());
//        }
//
//        return orderMessage;
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
