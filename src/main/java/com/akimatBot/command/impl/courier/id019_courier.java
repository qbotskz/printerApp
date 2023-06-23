//package com.akimatBot.command.impl.courier;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.OrderRepository;
//import com.akimatBot.repository.repos.UserRepository;
//import com.akimatBot.utils.ButtonsLeaf;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Scanner;
//
//public class id019_courier extends Command {
//    private final OrderRepository orderRepository = TelegramBotRepositoryProvider.getOrderRepository();
//    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
//    private List<User> couriers ;
//    private User courier;
//    private Order order;
//    private List<String> finalList;
//    private List<String> finalList2;
//    private ButtonsLeaf buttonsLeaf;
//    private String deliveryStatus;
//    private boolean isAccepted;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//
//                if(hasCallbackQuery()){
//
//                    String myString = getCallbackQuery().getMessage().getText();
//                    Scanner scanner = new Scanner(myString);
//                    String line="";
//                    while (scanner.hasNextLine()) {
//                        line = scanner.nextLine();
//                        break;
//                    }
//                    scanner.close();
//                    int orderNum = Integer.parseInt(line.substring(line.lastIndexOf("№") + 1));
//                    System.out.println(orderNum);
//                    order = orderRepository.findOrderById(orderNum);
//                    if(isButton(101)){
//                        selectStatus(getCallbackQuery().getData());
//                        waitingType = WaitingType.SELECT_STATUS;
//                        return COMEBACK;
//                    }
//
//                    else{
//                        editMessage(String.format(getText(68),order.getCourier().getFullName(), order.getId()), messageToEdit.getOrder().getOperator().getChatId(),(int) messageToEdit.getMsgId() );
//                        return EXIT;
//                    }
//                }
//            case SELECT_STATUS:
//                if(hasCallbackQuery()){
//                    isAccepted=false;
//                    selectStatus(getCallbackQuery().getData());
//                    return COMEBACK;
//                }
//            case CONFIRM_STATUS:
//                isAccepted = false;
//                selectStatus(getCallbackQuery().getData());
//                return COMEBACK;
//            case FINISH_ORDER:
//
//                return EXIT;
//
//        }
//        return false;
//    }
//    private void updateStatus() throws TelegramApiException {
//        if(isAccepted && !deliveryStatus.equals(getButtonText(115))){
//
//            sendMessage(String.format(getText(70), order.getId(), deliveryStatus, order.getCourier().getFullName()), order.getUser().getChatId());
//            editMessage(String.format(getText(70), order.getId(), deliveryStatus, order.getCourier().getFullName()),messageToEdit.getOrder().getOperator().getChatId(),(int) messageToEdit.getMsgId());
//
//        }
//        if(deliveryStatus.equals(getButtonText(115)) && isAccepted){
//            editMessage(String.format(getText(69), order.getId(), order.getCourier().getFullName()), updateMessageId);
//            sendMessageWithKeyboardWithChatId(String.format(getText(80), order.getId(), order.getCourier().getFullName()),42 ,order.getUser().getChatId());
//            sendMessage(String.format(getText(69), order.getId(), order.getCourier().getFullName()),order.getOperator().getChatId());
//            waitingType = WaitingType.FINISH_ORDER;
//            //return COMEBACK;
//        }
//    }
//    private void selectStatus(String data) throws TelegramApiException {
//
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        int i = 0;
//        List<String> list = new ArrayList<>(Arrays.asList(getButtonText(111),getButtonText(112),getButtonText(113),getButtonText(114) ,getButtonText(115),getButtonText(116)));
//
//        list.forEach((e) -> {
//            if(data.equals(e)){
//                if(data.equals(getButtonText(116))){
//                    isAccepted = true;
//                    waitingType = WaitingType.CONFIRM_STATUS;
//                    try {
//                        updateStatus();
//                    } catch (TelegramApiException telegramApiException) {
//                        telegramApiException.printStackTrace();
//                    }
//                    //return;
//                }
//                else{
//                    deliveryStatus = e;
//                    finalList.add(e+" ✅");
//                }
//
//
//            }
//            else{
//                finalList.add(e);
//            }
//            finalList2.add(String.valueOf(e));
//        });
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2);
//        if(deliveryStatus==null) {
//            editMessageWithKeyboard(String.format(getText(71), order.getId(), "null"), updateMessageId, buttonsLeaf.getListButtonInline());
//        }
//        else{
//            editMessageWithKeyboard(String.format(getText(71), order.getId(), deliveryStatus), updateMessageId, buttonsLeaf.getListButtonInline());
//        }
//    }
//}
