//package com.akimatBot.command.impl.operator;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.standart.User;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class id095_select_manager extends Command {
//    private Order order;
//    private User user;
//    private String orderMessage;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        parseOrder();
//        long id = -1;
//        if(update.hasCallbackQuery()){
//
//            try{
//                id = Long.parseLong(update.getCallbackQuery().getData().split(",")[1]);
//            }
//            catch (Exception e){
//
//            }
//        }
//        orderMessage = update.getCallbackQuery().getMessage().getText();
//        user = userRepository.findById(id);
//        splitOrderMessage();
//        deleteMessage(updateMessageId);
//
//        sendMessageWithKeyboardWithChatId(String.format(getText(78)+orderMessage, order.getId()), 78, user.getChatId());
//
//        if(order.getUser()!=null){
//
//
//            sendMessage(String.format(getText(77), order.getId())+orderMessage, order.getUser().getChatId());
//        }
//        return EXIT;
//    }
//    public void splitOrderMessage(){
//        List<String> temp = new ArrayList<String>(Arrays.asList(orderMessage.split("\n")));
//        temp.remove(0);
//
//        orderMessage = String.join("\n", temp);
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
