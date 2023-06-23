//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.utils.DateKeyboard;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//public class id067_courier_report_admin extends Command {
//    private User user;
//    private DateKeyboard dateKeyboard;
//    private Date start;
//    private Date end;
//    private CourierReportService courierReportService;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                deleteMessage(updateMessageId);
//
//                if(isButton(197)){
//                    Date date = new Date();
//
//                    user = userRepository.findByChatId(chatId);
//                    List<Order> orders = orderRepository.findOrdersByOrderedDateBetween(removeTime(date),addTime(date));
//                    courierReportService = new CourierReportService();
//                    courierReportService.sendCourierReport(chatId, bot , orders);
//                    return EXIT;
//                }
//                else if(isButton(198)){
//                    dateKeyboard = new DateKeyboard();
//                    sendStartDate();
//                    waitingType = WaitingType.START_DATE;
//                    return COMEBACK;
//                }
//            case START_DATE:
//                deleteMessage(updateMessageId);
//
//                if (hasCallbackQuery()) {
//                    if (dateKeyboard.isNext(updateMessageText)) {
//                        sendStartDate();
//                        return COMEBACK;
//                    }
//                    start = dateKeyboard.getDateDate(updateMessageText);
//                    sendEndDate();
//                    waitingType = WaitingType.END_DATE;
//
//                    return COMEBACK;
//                } else {
//                    sendStartDate();
//                }
//                return COMEBACK;
//            case END_DATE:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()) {
//                    if (dateKeyboard.isNext(updateMessageText)) {
//                        sendEndDate();
//                        return COMEBACK;
//                    }
//                    end = dateKeyboard.getDateDate(updateMessageText);
//                    List<Order> orders = orderRepository.findOrdersByOrderedDateBetween(removeTime(start),addTime(end));
//                    courierReportService = new CourierReportService();
//                    courierReportService.sendCourierReport(chatId, bot , orders);
//
//                    return EXIT;
//                } else {
//                   sendEndDate();
//                }
//                return COMEBACK;
//        }
//        return false;
//    }
//    public static Date removeTime(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.set(Calendar.HOUR, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        return cal.getTime();
//    }
//    public static Date addTime(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.set(Calendar.HOUR, 23);
//        cal.set(Calendar.MINUTE, 59);
//        cal.set(Calendar.SECOND, 59);
//        cal.set(Calendar.MILLISECOND, 599);
//        return cal.getTime();
//    }
//    private int sendStartDate() throws TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard("Период с:", dateKeyboard.getCalendarKeyboard()));
//    }
//
//    private void makeSmallReport(List<Order> orders) throws TelegramApiException {
//        int allOrders = orders.size();
//        int sumByNalom = 0;
//        int sumByCard = 0;
//        int refuses = 0;
//        int wage = 0;
//
//
//        for(Order o:orders){
//            if(o.getRefuseReason()!=null) refuses++;
//            if(o.getPaymentMethod().getId() == 1){
//                sumByCard+=o.getSum();
//            }
//            else{
//                sumByNalom +=o.getSum();
//            }
//
//        }
//        System.out.println(123);
//        sendMessage(String.format(getText(169), user.getFullName(), allOrders, sumByNalom+"₸", sumByCard+"₸", refuses, wage));
//    }
//    private int sendEndDate() throws TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard("До: ", dateKeyboard.getCalendarKeyboard()));
//    }
//}
