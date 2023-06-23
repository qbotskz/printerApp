//package com.qbots.kitapal.paymTeachApi.web;
//
//import com.qbots.kitapal.MainApplication;
//import com.qbots.kitapal.model.custom.Book;
//import com.qbots.kitapal.model.custom.OrderItem;
//import com.qbots.kitapal.model.custom.OrderOfBooks;
//import com.qbots.kitapal.model.standard.Admin;
//import com.qbots.kitapal.paymTeachApi.OrderStatuses;
//import com.qbots.kitapal.paymTeachApi.PaymTech;
//import com.qbots.kitapal.repository.AdminRepo;
//import com.qbots.kitapal.repository.MessageRepo;
//import com.qbots.kitapal.service.KeyboardMarkUpService;
//import com.qbots.kitapal.service.OrderService;
//import com.qbots.kitapal.util.ButtonsLeaf;
//import com.qbots.kitapal.util.DateUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
//
//import java.util.*;
//
//@Component
//public class OrderCheckThread extends TimerTask {
//
//    @Autowired
//    OrderService orderService;
//
//    @Autowired
//    MessageRepo messageRepo;
//
//    @Autowired
//    AdminRepo adminRepo;
//
//
//    PaymTech paymTech;
//
//    int orderTimeout = 15;
//
//    @Override
//    public void run() {
//        List<OrderOfBooks> orders = orderService.findNonPaidOrders();
//        paymTech = new PaymTech();
//        System.out.println("Check timeout order start!!!! -> " + DateUtil.getDbMmYyyyHhMmSs(new Date()));
//        for (OrderOfBooks order : orders){
//            if (oldOrder(order)){
//                orderService.cancelOrder(order);
//                sendRejectOrder(order);
////                System.out.println(order.toString());
//            }
//            else if (paymTech.isPaid(order.getPaymTechOrderId())){
//                orderService.orderPaid(order);
//                sendSuccessToCustomer(order);
//            }
//
//        }
//    }
//
//    private void sendRejectOrder(OrderOfBooks order) {
//        sendMessage("Сіз 15 минут ішінде төлем жасамадыңыз, сондықтан тапсырыс қабылданбады!", order.getUser().getChatId());
//    }
//
//    private boolean oldOrder(OrderOfBooks order) {
//        Calendar createdDate = Calendar.getInstance();
//        createdDate.setTime(order.getCreatedDate());
//        createdDate.add(Calendar.MINUTE, orderTimeout);
//        return createdDate.getTime().before(new Date());
//    }
//
//    private void sendSuccessToCustomer(OrderOfBooks order) {
//        sendMessage(getText(22) + (order.getDeliverNeed() ? "" : getText(30)), order.getUser().getChatId());
//        new Thread(createRunnable( order)).start();
//    }
//
//    private String getText(int messId){
//        return messageRepo.findById(messId).getNameKz();
//    }
//
//    private Runnable createRunnable( OrderOfBooks orderForAdminMessage) {
//
//        return () -> {
//            try {
//
//                String text = getOrderInfo(orderForAdminMessage);
//                for (Admin a : adminRepo.findAllByDeletedIsFalse()) {
//                    sendMessageWithKeyboard(text,
//                            new ButtonsLeaf(Collections.singletonList("ҚАБЫЛДАУ"),
//                                    Collections.singletonList("id1001;0;" + orderForAdminMessage.getId())).getListButtonWithDataList(),
//                            a.getChatId());
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };
//
//    }
//
//    private String getOrderInfo(OrderOfBooks order)  {
//        StringBuilder orderInfo = new StringBuilder();
//        int k = 0;
//        for (OrderItem orderItem : order.getOrderItems()) {
//            k++;
//            if (k != 1) orderInfo.append("\n");
//            orderInfo.append(k).append(". ");
//
//            Book book = orderItem.getBook();
//            orderInfo.append(getBookDescription(book));
//            orderInfo.append("\n");
//
//            int itemPrice = orderItem.getPrice() * orderItem.getQuantity();
//            orderInfo.append(String.format(getText(54), orderItem.getPrice(), orderItem.getQuantity(), itemPrice));
//        }
//
//        String shippingText;
//        if (order.getDeliverNeed()) {
//            shippingText = String.format(getText(60), order.getAddress(), order.getDeliveryPrice());
//        } else shippingText = getText(59);
//
//        String cashBackText;
//        if (order.getUseCashback()) {
//            int cashBackUsed = order.getUsedCashback();
//
//            cashBackText = String.format(getText(72), cashBackUsed);
//        } else cashBackText = "";
//
//
//        String orderDescription = String.format(getText(61),
//                order.getId(),
//                order.getUser().getFullName(),
//                order.getUser().getPhone(),
//                orderInfo,
//                shippingText,
//                cashBackText,
//                order.getTotal());
//
//        return orderDescription;
//    }
//
//    public String getBookDescription(Book book) {
//        StringBuilder bookDescription = new StringBuilder();
//        bookDescription.append(book.getName()).append(" - ").append(book.getAuthorsLine());
//        return bookDescription.toString();
//    }
//
//    private int sendMessage(String text, String chatId){
//        try {
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(chatId);
//            sendMessage.setText(text);
//            sendMessage.setParseMode("html");
//            return MainApplication.bot.execute(sendMessage).getMessageId();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return 0;
//    }
//    private int sendMessageWithKeyboard(String text, long kid, String chatId){
//        KeyboardMarkUpService keyboardMarkUpService = new KeyboardMarkUpService();
//        return sendMessageWithKeyboard(text, keyboardMarkUpService.select(kid), chatId);
//    }
//    private int sendMessageWithKeyboard(String text, ReplyKeyboard replyKeyboard, String chatId){
//        try {
//
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(chatId);
//            sendMessage.setText(text);
//            sendMessage.setParseMode("html");
//            sendMessage.setReplyMarkup(replyKeyboard);
//            return MainApplication.bot.execute(sendMessage).getMessageId();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return 0;
//    }
//}
