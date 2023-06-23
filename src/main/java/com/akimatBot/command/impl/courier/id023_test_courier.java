//package com.akimatBot.command.impl.courier;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Courier;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.OrderStatus;
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
//public class id023_test_courier extends Command {
//    private final OrderRepository orderRepository = TelegramBotRepositoryProvider.getOrderRepository();
//    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
//
//    private List<User> couriers ;
//    private Courier courier;
//    private Order order;
//    private List<String> finalList;
//    private List<String> finalList2;
////    private ButtonsLeaf buttonsLeaf;
//    private String deliveryStatus;
//    private boolean isAccepted;
//    private int buttonId;
//    private int sum;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        parseOrder();
//        if(order.isFinished()){
//            editMessage(String.format(getText(144), order.getId()),updateMessageId);
//            return EXIT;
//        }
//        if(isButton(101)){
//
//            try{
//                if(order.getCourier().getChatId().equals(chatId)){
//                    editMessage(getText(145), updateMessageId);
//                    return EXIT;
//                }
//            }
//            catch (Exception e){
//
//            }
//            if(order.getCourier()!=null && !order.getCourier().getChatId().equals(chatId)){
//                // другой уже взял
//                editMessage(String.format(getText(102), order.getCourier().getFullName(), order.getId()), updateMessageId);
//                return EXIT;
//            }
//            couriers = userRepository.findAllByRolesContains(new Role(3));
//            order.setCourier(userRepository.findByChatId(chatId));
//            order.setDeliveryStatus(OrderStatus.ACCEPTED_ORDER);
//
//            Thread sendingThread = new Thread(){
//                int messageCount = 0;
//                @SneakyThrows
//                @Override
//                public void run(){
//                    for(User u:couriers){
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
//                        if(order.getCourier()==null){
//                            order.setCourier(u);
//                        }
//                        if(u.getId() == order.getCourier().getId()){
//                            continue;
//                        }
//                        try{
//                            sendMessage(String.format(getText(198), order.getCourier().getFullName(), order.getId()), u.getChatId());
//                        }
//                        catch (Exception e){
//                            System.out.println(chatId+" - CHAT NOT FOUND");
//                        }
//                        messageCount++;
//                    }
//
//                }
//            };
//            sendingThread.start();
//            sendMessage(String.format(getText(170), order.getCourier().getFullName(), order.getId(), getFoodsFromOrder(), sum, order.getPaymentMethod().getName(1), order.getLocation(), order.getContact()), order.getOperator().getChatId());
//            courier = courierRepository.findCourierByUser(userRepository.findByChatId(chatId));
//            if(courier==null){
//                courier = new Courier();
//                courier.setPhotoAllowed(false);
//                courier.setUser(userRepository.findByChatId(chatId));
//                courier = courierRepository.save(courier);
//            }
//            if(courier.getPhotoUrl()==null){
//                sendMessage(String.format(getText(171), order.getCourier().getFullName(), order.getId(), getFoodsFromOrder(), sum, order.getPaymentMethod().getName(1), order.getLocation(), order.getCourier().getFullName()+", "+order.getCourier().getPhone()), order.getUser().getChatId());
//
//            }
//            else{
//                sendMessageWithPhoto(String.format(getText(171), order.getCourier().getFullName(), order.getId(), getFoodsFromOrder(), sum, order.getPaymentMethod().getName(1), order.getLocation(), order.getCourier().getFullName()+", "+order.getCourier().getPhone()), order.getUser().getChatId(), courier.getPhotoUrl());
//            }
//            order = orderRepository.save(order);
//
//            setButtons();
////            selectStatus();
//        }
//        else if (isButton(102)){
//            deleteMessage(updateMessageId);
//            return EXIT;
//            // todo
//        }
//
//        return false;
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
//    private void setButtons() throws TelegramApiException{
//        List<String> list = new ArrayList<>(Arrays.asList(OrderStatus.IN_THE_WAY.getName(getLanguageId())));
//        List<String> ids = new ArrayList<>(Collections.singletonList("28,"+String.valueOf(OrderStatus.IN_THE_WAY.getId())+",-"));
//
//        ButtonsLeaf stat = new ButtonsLeaf(list,ids, 5, 71,chatId);
//
//        editMessageWithKeyboard(String.format(getText(71), order.getId(), getFoodsFromOrder(), sum, order.getLocation() ,order.getDeliveryStatus().getName(1)), updateMessageId, stat.getListButtonInline());
//
//
//    }
//    private void selectStatus() throws TelegramApiException {
//        List<String> list = new ArrayList<>(Arrays.asList(getButtonText(111),getButtonText(112),getButtonText(113),getButtonText(114) ,getButtonText(115)));
//        List<String> ids = new ArrayList<>(Arrays.asList("111", "112", "113", "114", "115"));
//
//        ButtonsLeaf stat = new ButtonsLeaf(list,list);
//
//       editMessageWithKeyboard(String.format(getText(71), order.getId(), getFoodsFromOrder(), sum, order.getLocation(),getText(97)), updateMessageId, stat.getListButtonInline());
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
//
//}
