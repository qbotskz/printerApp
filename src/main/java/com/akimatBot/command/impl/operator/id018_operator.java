//package com.akimatBot.command.impl.operator;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.OrderRepository;
//import com.akimatBot.repository.repos.UserRepository;
//import com.akimatBot.utils.ButtonsLeaf;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.*;
//
//public class id018_operator extends Command {
//    private final OrderRepository orderRepository = TelegramBotRepositoryProvider.getOrderRepository();
//    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
//    private List<User> couriers;
//    private User courier;
//    private Order order;
//    private List<String> finalList;
//    private List<String> finalList2;
//    private ButtonsLeaf buttonsLeaf;
//
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType) {
//            case START:
//
//                if (hasCallbackQuery()) {
//                    String myString = getCallbackQuery().getMessage().getText();
//                    Scanner scanner = new Scanner(myString);
//                    String line = "";
//                    while (scanner.hasNextLine()) {
//                        line = scanner.nextLine();
//                        break;
//                    }
//                    scanner.close();
//                    int orderNum = Integer.parseInt(line.substring(line.lastIndexOf("№") + 1));
//                    System.out.println(orderNum);
//
//                    order = orderRepository.findOrderById(orderNum);
//                    //messageToEdit = messageToEditRepo.findMessageToEditByOrderAndChatId(order, order.getUser().getChatId());
//                    //order.setOperator(userRepository.findByChatId(chatId));
//                    if (isButton(99)) {
//                        //editMessage(finishOrderW(), order.getUser().getChatId(), (int) messageToEdit.getMsgId());
//                        if (order.getDeliveryMethod(getLanguageId()).equals("\uD83D\uDEF5 Курьером") || order.getDeliveryMethod(getLanguageId()).equals("\uD83D\uDEF5 Курьер")) {
//                            couriers = userRepository.findAllByRolesContains(new Role(3));
//                            order.setOperator(userRepository.findByChatId(chatId));
//                            showCouriers();
//                            waitingType = WaitingType.CHOOSE_COURIER;
//                        } else {
//                            finishOrder();
//                        }
//                    } else {
//                        sendMessage(String.format(getText(72), order.getId()), order.getUser().getChatId());
//                        orderRepository.delete(order);
//
//                    }
//                    return COMEBACK;
//
//                }
//            case CHOOSE_COURIER:
//                if (hasCallbackQuery()) {
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        editMessageWithKeyboard(String.format(getText(73), order.getId()), updateMessageId, buttonsLeaf.getListButtonInline());
////                        editMessageWithKeyboard("Какому курьеру доверить доставку",updateMessageId ,buttonsLeaf.getListButtonInline());
//                    }
//                    courier = couriers.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//                    order.setCourier(courier);
//                    MessageToEdit messageToEdit = new MessageToEdit();
//                    messageToEdit.setOrder(order);
//                    messageToEdit.setMsgId(updateMessageId);
//                    messageToEdit.setChatId(chatId);
//                    messageToEditRepo.save(messageToEdit);
//
//                    order = orderRepository.save(order);
//                    sendOrderToCourier(courier.getChatId());
//                    editMessage(String.format(getText(74), order.getId(), courier.getFullName()), updateMessageId);
////                    editMessage("Заказ №"+ order.getId()+" был передан курьеру - " + courier.getFullName()+"\nВ ожидании ответа курьера.....", updateMessageId);
//                    waitingType = WaitingType.WAIT;
//                    return COMEBACK;
//                }
//            case WAIT:
//                System.out.println("WAITING FOR RESPONSE");
//                return EXIT;
//
//        }
//        return false;
//    }
//
//    private void showCouriers() throws TelegramApiException {
//        List<String> list = new ArrayList<>();
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        couriers.forEach((e) -> {
//            finalList.add(e.getFullName());
//            finalList2.add(String.valueOf(e.getId()));
//        });
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10);
//        editMessageWithKeyboard(String.format(getText(73), order.getId()), updateMessageId, buttonsLeaf.getListButtonInline());
//    }
//
//    private void finishOrder() throws TelegramApiException {
//
//
//        String text = "";
//        Map<Food, Long> foodMap = countByClassicalLoop(order.getUser().getFoods());
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        int sum = 0;
//        int count = 1;
//        while (itr.hasNext()) {
//            Map.Entry<Food, Long> entry = itr.next();
//            text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + entry.getKey().getFoodPrice() + "₸ X " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice() * entry.getValue() + "₸\n";
//            sum += entry.getKey().getFoodPrice() * entry.getValue();
//            count++;
//        }
//        String message;
//        if (order.getPaymentNalom() != null) {
//            message = String.format(getText(77), order.getId()) + "\n" + String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getSum(),order.getPaymentNalom());
//            sendMessageWithKeyboard(message, 2);
//        } else {
//            message = String.format(getText(77), order.getId()) + "\n" + String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getSum());
//            sendMessageWithKeyboard(message, 2);
//        }
//
//    }
//
//    private String finishOrderW() throws TelegramApiException {
//
//        String text = "";
//        Map<Food, Long> foodMap = countByClassicalLoop(order.getUser().getFoods());
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        int sum = 0;
//        int count = 1;
//        while (itr.hasNext()) {
//            Map.Entry<Food, Long> entry = itr.next();
//            text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + entry.getKey().getFoodPrice() + "₸ X " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice() * entry.getValue() + "₸\n";
//            sum += entry.getKey().getFoodPrice() * entry.getValue();
//            count++;
//        }
//        String message;
//        if (order.getPaymentNalom() != null) {
//            message = String.format(getText(77), order.getId()) + "\n" + String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getSum(),order.getPaymentNalom());
//        } else {
//            message = String.format(getText(77), order.getId()) + "\n" + String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getSum());
//        }
//
//        return message;
//    }
//
//    private void sendOrderToCourier(long chatId) throws TelegramApiException {
//
//
//        String text = "";
//        Map<Food, Long> foodMap = countByClassicalLoop(order.getFoods());
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        int sum = 0;
//        int count = 1;
//        while (itr.hasNext()) {
//            Map.Entry<Food, Long> entry = itr.next();
//            text += count + ". " + entry.getKey().getFoodName(getLanguageId()) + " " + entry.getKey().getFoodPrice() + "₸ X " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice() * entry.getValue() + "₸\n";
//            sum += entry.getKey().getFoodPrice() * entry.getValue();
//            count++;
//        }
//        String message = "";
//
//        if (order.getPaymentNalom() != null) {
//            message = String.format(getText(78), order.getId()) + "\n" + String.format(getText(76), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()), order.getSum(),order.getPaymentNalom());
//            sendMessageWithKeyboardWithChatId(message, 39, chatId);
//        } else {
//            message = String.format(getText(78), order.getId()) + "\n" + String.format(getText(75), text, order.getDeliveryMethod(getLanguageId()), order.getContact(), order.getLocation(), order.getPaymentMethod().getName(getLanguageId()),order.getSum());
//            sendMessageWithKeyboardWithChatId(message, 39, chatId);
//        }
//
//
//        orderRepository.save(order);
//
//    }
//
////        Map<T, Long> resultMap = new LinkedHashMap<>();
////        for (T element : inputList) {
////            if (resultMap.containsKey(element)) {
////                resultMap.put(element, resultMap.get(element) + 1L);
////            } else {
////                resultMap.put(element, 1L);
////            }
////        }
////        return resultMap;
////    }
//}
