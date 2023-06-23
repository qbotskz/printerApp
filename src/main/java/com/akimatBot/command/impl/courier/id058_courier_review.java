//package com.akimatBot.command.impl.courier;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Courier;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.custom.Review;
//import com.akimatBot.entity.enums.StarGrade;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.utils.ButtonsLeaf;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class id058_courier_review extends Command {
//    private ButtonsLeaf buttonsLeaf;
//    private Order order;
//    private Review review;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType) {
//            case START:
//                if (isButton(180)) {
//                    deleteMessage(updateMessageId);
//                    sendMessageWithKeyboard("Главное меню", 2);
//                    return EXIT;
//                }
//                parseOrder();
//                review = new Review();
//                review.setUser(userRepository.findByChatId(chatId));
//                getGrade();
//                waitingType = WaitingType.GRADING;
//                return COMEBACK;
//            case GRADING:
//                if (hasCallbackQuery()) {
//
//                    switch (getCallbackQuery().getData()) {
//                        case "0":
//                            order.setReviewGrade(StarGrade.ONE_STAR);
//                            review.setReviewGrade(StarGrade.ONE_STAR);
//                            break;
//                        case "1":
//                            order.setReviewGrade(StarGrade.TWO_STAR);
//                            review.setReviewGrade(StarGrade.TWO_STAR);
//                            break;
//                        case "2":
//                            order.setReviewGrade(StarGrade.THREE_STAR);
//                            review.setReviewGrade(StarGrade.THREE_STAR);
//                            break;
//                        case "3":
//                            order.setReviewGrade(StarGrade.FOUR_STAR);
//                            review.setReviewGrade(StarGrade.FOUR_STAR);
//                            break;
//                        case "4":
//                            order.setReviewGrade(StarGrade.FIVE_STAR);
//                            review.setReviewGrade(StarGrade.FIVE_STAR);
//                            break;
//                    }
//                    getReviewText();
//                    waitingType = WaitingType.SET_TEXT;
//                    return COMEBACK;
//                }
//            case SET_TEXT:
//                if (hasCallbackQuery()) {
//                    order.setReviewText(null);
//                    review.setReviewText(null);
//                }
//                if (update.hasMessage() && update.getMessage().hasText()) {
//                    order.setReviewText(update.getMessage().getText());
//                    review.setReviewText(update.getMessage().getText());
//                    deleteMessage(updateMessageId);
//                    sendMessage(getText(83));
//                    orderRepository.save(order);
//                    review.setUploadedDate(new Date());
//                    reviewRepository.save(review);
//
//                    return EXIT;
//                }
//
//                orderRepository.save(order);
//                review.setUploadedDate(new Date());
//                reviewRepository.save(review);
//                editMessage(getText(83), updateMessageId);
//                return EXIT;
//        }
//        return false;
//    }
//
//    //    private int getReviewText() throws TelegramApiException{
////        return sendMessageWithKeyboard(getText(82), 23);
////    }
//    private void getReviewText() throws TelegramApiException {
//         editMessageWithKeyboard(getText(82), updateMessageId,23);
//    }
//
//    public void parseOrder() {
//        String[] myString;
//        try {
//            myString = getCallbackQuery().getMessage().getText().split("\n");
//        } catch (Exception e) {
//            myString = getCallbackQuery().getMessage().getCaption().split("\n");
//        }
//        Pattern pattern = Pattern.compile("(?<=№|#).[0-9]+");
//
//        Matcher matcher = pattern.matcher(myString[0]);
//        int orderNum = -1;
//        if (matcher.find()) {
//            orderNum = Integer.parseInt(matcher.group(0).replaceAll("\\s+", ""));
//            System.out.println(orderNum);
//        }
//        order = orderRepository.findOrderById(orderNum);
//    }
//
////    private void getGrade() throws TelegramApiException {
////        List<String> list = new ArrayList<>();
////        List<String> list2 = new ArrayList<>();
////        for (int i = 0; i < 5; i++) {
////            list.add("⭐");
////            list2.add(String.valueOf(i));
////        }
////        buttonsLeaf = new ButtonsLeaf(list, list2, 5, 5);
////        sendMessageWithKeyboard(getText(81), buttonsLeaf.getListButtonInline());
////    }
//private void getGrade() throws TelegramApiException {
//    List<String> list = new ArrayList<>();
//    List<String> list2 = new ArrayList<>();
//    for (int i = 0; i < 5; i++) {
//        list.add("⭐");
//        list2.add(String.valueOf(i));
//    }
//    buttonsLeaf = new ButtonsLeaf(list, list2, 5, 5);
//    Courier courier = courierRepository.findCourierByUser(order.getCourier());
//    if(courier.isPhotoAllowed()){
//        deleteMessage(updateMessageId);
//        sendMessageWithKeyboard(getText(81), buttonsLeaf.getListButtonInline());
//    }
//    else{
//        editMessageWithKeyboard(getText(81),updateMessageId, buttonsLeaf.getListButtonInline());
//    }
//}
//}
