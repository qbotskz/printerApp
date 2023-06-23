//package com.akimatBot.command.impl.operator;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Order;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class id050_rejection_from_order extends Command {
//    private Order order;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        parseOrder();
//        deleteMessage(updateMessageId);
//        sendMessage(String.format(getText(148, order.getUser().getChatId()), order.getUser().getFullName(), order.getId(), updateMessageText), order.getUser().getChatId());
//        order.setRefuseReason(updateMessageText);
//        order.setFinished(true);
//        order = orderRepository.save(order);
////        orderRepository.delete(order);
//        return EXIT;
//    }
//
//    //    public void parseOrder(){
////        String myString = getCallbackQuery().getMessage().getText();
////
////        Scanner scanner = new Scanner(myString);
////        String line="";
////        while (scanner.hasNextLine()) {
////            line = scanner.nextLine();
////            break;
////        }
////        scanner.close();
////        int orderNum = Integer.parseInt(line.substring(line.lastIndexOf("№") + 1));
////        System.out.println(orderNum);
////
////        order = orderRepository.findOrderById(orderNum);
////    }
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
