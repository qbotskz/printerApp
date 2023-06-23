//package com.akimatBot.command.impl.operator;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class id084_custom_reason_for_rejecting_order extends Command {
//    private Order order;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                parseOrder();
//                deleteMessage(updateMessageId);
//                sendMessage(String.format(getText(200), order.getId()));
//                waitingType = WaitingType.SET_TEXT;
//                return COMEBACK;
//            case SET_TEXT:
//                deleteMessage(updateMessageId);
//                if(update.hasMessage()&&update.getMessage().hasText()){
//                    order.setRefuseReason(update.getMessage().getText());
//                    order.setFinished(true);
//                    order = orderRepository.save(order);
//                    sendMessage(String.format(getText(148, order.getUser().getChatId()), order.getUser().getFullName(), order.getId(), updateMessageText), order.getUser().getChatId());
//                    return EXIT;
//
//                }
//        }
//        return false;
//    }
//    public void parseOrder() {
//        String[] myString = getCallbackQuery().getMessage().getText().split("\n");
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
//}
