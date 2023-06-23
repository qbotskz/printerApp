//package com.akimatBot.command.impl.chef;
//
//import com.akimatBot.command.Command;
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
//public class id072_select_status_chef extends Command {
//    private Order order;
//    private int sum;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        parseOrder();
//        if(!order.getChef().getChatId().equals(chatId)){
//            return EXIT;
//        }
//        if(order.isFinished()){
//            editMessage(String.format(getText(157), order.getId()), updateMessageId);
//            return EXIT;
//        }
////        Button button;
////         button = buttonRepository.findByNameAndLangId(updateMessageText, getLanguageId());
////
//        int id = -1;
//        if(update.hasCallbackQuery()){
//
//            try{
//                id = Integer.parseInt(update.getCallbackQuery().getData().split(",")[1]);
//            }
//            catch (Exception e){
//
//            }
//        }
//
//        setButtons(id);
//        return EXIT;
//    }
//    private void setButtons(int id) throws TelegramApiException{
//        String confirmData = "73," + (id);
//        List<String> list = new ArrayList<>(Arrays.asList(OrderStatus.getById(id).getName(getLanguageId())+" ✅", getButtonText(116)));
//
//
//        List<String> ids = new ArrayList<>(Arrays.asList("72,"+String.valueOf(OrderStatus.COOKED.getId())+",-", confirmData));
//
//        ButtonsLeaf stat = new ButtonsLeaf(list,ids, 5, 80,chatId);
//
//        editMessageWithKeyboard(String.format(getText(71),order.getId())+"\n"+formOrderMessage(), updateMessageId, stat.getListButtonInline());
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
//
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
