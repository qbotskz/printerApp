//package com.akimatBot.command.impl.courier;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Cashback;
//import com.akimatBot.entity.custom.Courier;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.OrderStatus;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.utils.ButtonsLeaf;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class id029_ConfirmByCourier extends Command {
//    private Order order;
//    private int sum;
//    private Cashback cashback = cashbackRepository.findTopByOrderByIdDesc();
//
//    @Override
//    public boolean execute() throws TelegramApiException {
//
//
//        parseOrder();
//        if(!order.getCourier().getChatId().equals(chatId)){
//            return EXIT;
//        }
//
//        if(order.isFinished()){
//            editMessage(String.format(getText(157), order.getId()), updateMessageId);
//            return EXIT;
//        }
//        int statusId = Integer.parseInt(updateMessageText.split(",")[1])-1;
////        String stat = getButtonText(Integer.parseInt(updateMessageText.split(",")[1]));
//        String stat = OrderStatus.getById(statusId).getName(getLanguageId());
//        order.setDeliveryStatus(OrderStatus.getById(statusId));
//        order = orderRepository.save(order);
//        if(statusId == OrderStatus.FINISHED_DELIVERY.getId()){
//            order.setFinished(true);
//            order.setDeliveredDate(new Date());
//            order = orderRepository.save(order);
//            editMessage(String.format(getText(69), order.getId(), order.getCourier().getFullName(), getFoodsFromOrder(), sum, order.getPaymentMethod().getName(1),
//                    order.getLocation(), order.getContact()), updateMessageId);
//            //sendMessage(String.format(getText(69), order.getId(), order.getCourier().getFullName(), getFoodsFromOrder()),order.getOperator().getChatId());
//            Courier courier = courierRepository.findCourierByUser(order.getCourier());
//            if(courier==null){
//                courier = new Courier();
//                courier.setUser(order.getCourier());
//                courierRepository.save(courier);
//            }
//
//
//            int cashbackAmount=0;
//
//            if(courier.isPhotoAllowed()){
//                sendMessageWithPhotoAndKeyboard(String.format(getText(80), order.getId(), order.getCourier().getFullName()), 72, courier.getPhotoUrl(), order.getUser().getChatId() );
//
//            }
//            else{
//                sendMessageWithKeyboardWithChatId(String.format(getText(80), order.getId(), order.getCourier().getFullName()),72 ,order.getUser().getChatId());
//            }
//            if(cashback!=null){
//                cashbackAmount = (order.getSum()/100)*cashback.getCashbackPercentage();
//                User customer = order.getUser();
//                if(customer.getCashback()!=null) {
//                    customer.setCashback(customer.getCashback()+cashbackAmount);
//                }
//                else{
//                    customer.setCashback(cashbackAmount);
//                }
//                userRepository.save(customer);
//                if(cashbackAmount!=0) {
//                    sendMessage(String.format(getText(205), cashbackAmount), order.getUser().getChatId() );
//                }
//            }
//            sendMessage(String.format(getText(69), order.getId(), order.getCourier().getFullName(), getFoodsFromOrder(), sum, order.getPaymentMethod().getName(1),
//                    order.getLocation(), order.getContact()), order.getOperator().getChatId());
//            return EXIT;
//        }
//        else{
//            sendMessage(String.format(getText(70), order.getId(),order.getDeliveryStatus().getName(getLanguage(order.getUser().getChatId()).getId()),getFoodsFromOrder(),sum , order.getCourier().getFullName()+", "+order.getCourier().getPhone()), order.getUser().getChatId());
//            //editMessage(String.format(getText(70), order.getId(), getFoodsFromOrder(),order.getDeliveryStatus(), order.getCourier().getFullName()),messageToEdit.getOrder().getOperator().getChatId(),(int) messageToEdit.getMsgId());
//
//        }
//        setButtons(statusId);
//
//
//
//
//        return EXIT;
//    }
//    private void setButtons(int id) throws TelegramApiException{
//        String confirmData = "29," + id;
//        List<String> list = new ArrayList<>(Arrays.asList(OrderStatus.getById(id+1).getName(getLanguageId())));
//        List<String> ids = new ArrayList<>(Arrays.asList("28,"+String.valueOf(id+1)+",-"));
//
//        ButtonsLeaf stat = new ButtonsLeaf(list,ids, 5, 71,chatId);
//
//        editMessageWithKeyboard(String.format(getText(71), order.getId(), getFoodsFromOrder(), sum,order.getLocation(),OrderStatus.getById(id).getName(getLanguageId())), updateMessageId, stat.getListButtonInline());
//
//
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
//
//}
