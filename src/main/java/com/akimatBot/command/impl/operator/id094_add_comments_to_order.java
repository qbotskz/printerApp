//package com.akimatBot.command.impl.operator;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.KaspiAccount;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.DeliveryMethod;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.utils.ButtonsLeaf;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class id094_add_comments_to_order extends Command {
//    private int lastMsgId;
//    private String deliveryType;
//    private String orderMessage;
//    private Order order;
//    private ArrayList<String> finalList;
//    private ArrayList<String> finalList2;
//    private ButtonsLeaf buttonsLeaf;
//    private List<KaspiAccount> kaspiAccounts;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//
//        switch (waitingType){
//            case START:
//                parseOrder();
//                delete();
//                orderMessage = getCallbackQuery().getMessage().getText();
//                kaspiAccounts = kaspiAccountsRepository.findAll();
//                splitOrderMessage();
//                if(order.getDeliveryMethod()==DeliveryMethod.BY_COURIER){
//                    getMoneySum();
//                    waitingType = WaitingType.GET_MONEY_SUM;
//                }
//                else{
//                    getKaspi();
//                    waitingType = WaitingType.CHOOSE_OPTION;
//                }
//                return COMEBACK;
//            case GET_MONEY_SUM:
//                delete();
//                if(update.hasMessage() && update.getMessage().hasText() && isNumeric(update.getMessage().getText())){
//                    order.setDeliveryPrice(Integer.parseInt(update.getMessage().getText()));
//                    order.setSum(order.getSum()+order.getDeliveryPrice());
//                    getKaspi();
//                    waitingType = WaitingType.CHOOSE_OPTION;
//                    return COMEBACK;
//                }
//                else{
//                    lastMsgId = wrongData();
//                    getMoneySum();
//                }
//            case CHOOSE_OPTION:
//                delete();
//                if(hasCallbackQuery()){
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        sendMessageWithKeyboard(getText(48), buttonsLeaf.getListButtonInlineWithBack());
//                    }
//                    KaspiAccount kaspiAccount = kaspiAccounts.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//
//
//                    String kaspiInfo = kaspiAccount.getName()+"|"+kaspiAccount.getPhone();
//                    if(order.getDeliveryMethod()==DeliveryMethod.BY_COURIER){
//                        deliveryType = "Цена: "+order.getDeliveryPrice()+"\nОплату можете произвести по номеру "+kaspiInfo;
//                    }
//                    else{
//                        deliveryType = "\nОплату можете произвести по номеру "+kaspiInfo;
//                    }
//                    order.setDeliveryType(deliveryType);
//                    showAllManagers();
//                    waitingType = WaitingType.CHOOSE_EMPLOYEE;
//                    return COMEBACK;
//                }
//                else{
//                    lastMsgId = wrongData();
//                    getKaspi();
//                }
//            case CHOOSE_EMPLOYEE:
//
//        }
//        return false;
//    }
//    private String formOrderMessage() throws TelegramApiException {
//
//        order = orderRepository.save(order);
////        String text = "ЗАКАЗ №"+order.getId();
//        String text = "";
//
//        Map<Food, Long> foodMap = countByClassicalLoop(order.getFoods());
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        int sum = 0;
//        int count = 1;
//        while (itr.hasNext()) {
//            Map.Entry<Food, Long> entry = itr.next();
//            text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + entry.getKey().getFoodPrice(order.getUser().getCity()) + "₸ x " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue() + "₸\n";
//            sum += entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue();
//
//            count++;
//        }
//
//        if (order.getDeliveryMethod()==DeliveryMethod.SELF_PICKUP) {
//            orderMessage = String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()) + deliveryType, order.getComment(),order.getSum());
//        } else {
//            orderMessage = String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getComment(),order.getDeliveryType(),order.getSum());
//        }
//
//        return orderMessage;
//    }
//
//    public void splitOrderMessage(){
//        List<String> temp = new ArrayList<String>(Arrays.asList(orderMessage.split("\n")));
//        temp.remove(0);
//
//        orderMessage = String.join("\n", temp);
//    }
//    public void showAllManagers() throws TelegramApiException {
//        List<String> list = new ArrayList<>();
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        List<User> managers = userRepository.findAllByRolesContains(new Role(6));
//        managers.forEach((e) -> {
//            finalList.add(e.getFullName());
//            finalList2.add(String.valueOf("95,"+e.getId()));
//        });
//
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2);
//        editMessageWithKeyboard(String.format(getText(226), order.getId()) +"\n"+ formOrderMessage(),updateMessageId, buttonsLeaf.getListButtonInline());
//        /// return toDeleteKeyboard(sendMessageWithKeyboard(getText(11), buttonsLeaf.getListButtonInline()));
//    }
//    public void delete() throws TelegramApiException{
//        deleteMessage(updateMessageId);
//        deleteMessage(lastSendMessageID);
//        deleteMessage(lastMsgId);
//    }
//    private int getMoneySum() throws TelegramApiException{
//        return sendMessage(String.format(getText(224), order.getId()) +"\n"+ formOrderMessage());
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//    public int getKaspi() throws TelegramApiException{
//
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        kaspiAccounts.forEach((e) -> {
//            finalList.add(e.getName()+"|"+e.getPhone());
//            finalList2.add(String.valueOf(e.getId()));
//        });
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2);
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(225), buttonsLeaf.getListButtonInline()));
//    }
//    public static boolean isNumeric(String string) {
//        int intValue;
//
//        if (string == null || string.equals("")) {
//            System.out.println("String cannot be parsed, it is null or empty.");
//            return false;
//        }
//
//        try {
//            intValue = Integer.parseInt(string);
//            return true;
//        } catch (NumberFormatException e) {
//            System.out.println("Input String cannot be parsed to Integer.");
//        }
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
//}
