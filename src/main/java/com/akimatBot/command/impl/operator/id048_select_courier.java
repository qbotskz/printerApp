//package com.akimatBot.command.impl.operator;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.utils.ButtonsLeaf;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class id048_select_courier extends Command {
//    private Order order;
//    private User user;
//    private List<String> finalList;
//    private List<String> finalList2;
//    private ButtonsLeaf buttonsLeaf;
//    private List<User> couriers;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        parseOrder();
//        if(order.isFinished()){
//            editMessage(String.format(getText(144), order.getId()),updateMessageId);
//            return EXIT;
//        }
//        showCouriers();
//        return false;
//    }
//    private void showCouriers() throws TelegramApiException {
//        List<String> list = new ArrayList<>();
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        couriers = userRepository.findAllByRolesContains(new Role(3));
//        couriers.forEach((e) -> {
//            finalList.add(e.getFullName());
//            finalList2.add(String.valueOf("49,"+e.getId()));
//        });
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10);
//        editMessageWithKeyboard(update.getCallbackQuery().getMessage().getText()+"\n"+String.format(getText(73), order.getId()), updateMessageId, buttonsLeaf.getListButtonInline());
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
