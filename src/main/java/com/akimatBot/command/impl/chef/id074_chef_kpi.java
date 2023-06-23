//package com.akimatBot.command.impl.chef;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Courier;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.services.CourierReportService;
//import com.akimatBot.utils.DateKeyboard;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//public class id074_chef_kpi extends Command {
//    private User user;
//    private DateKeyboard dateKeyboard;
//    private Date start;
//    private Date end;
//    private CourierReportService courierReportService;
//    private List<User> users;
//    private List<Courier> couriers;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                dateKeyboard = new DateKeyboard();
//                sendStartDate();
//                waitingType = WaitingType.START_DATE;
//                return COMEBACK;
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
//
////                    users = userRepository.findAllByRolesContains(new Role(3));
//                    couriers = courierRepository.findAll();
//
//                    List<Order> orders = orderRepository.findOrdersByOrderedDateBetween(removeTime(start),addTime(end));
//                    courierReportService = new CourierReportService();
//                    courierReportService.sendChefReport(chatId, bot , orders);
//                    return EXIT;
//                } else {
//                    sendEndDate();
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
//        cal.set(Calendar.SECOND, 00);
//        cal.set(Calendar.MILLISECOND, 0000);
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
//    private int sendEndDate() throws TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard("До: ", dateKeyboard.getCalendarKeyboard()));
//    }
//}
