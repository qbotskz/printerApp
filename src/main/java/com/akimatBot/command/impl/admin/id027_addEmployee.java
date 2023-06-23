//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Courier;
//import com.akimatBot.entity.custom.RestaurantBranch;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.utils.ButtonsLeaf;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class id027_addEmployee extends Command {
//    private Role role;
//    private Courier courier;
//    private List<User> stuffToEdit;
//    private User user;
//    private ButtonsLeaf buttonsLeaf;
//    private List<RestaurantBranch> branches;
//    private RestaurantBranch restaurantBranch;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                deleteMessage(lastSendMessageID);
//
//                deleteMessage(updateMessageId);
//                getEmployee();
//                if(isButton(124)){
//                    role = new Role(3);
//                }
//                else if(isButton(213)){
//                    role = new Role(6);
//                }
//                else if(isButton(238)){
//                    role = new Role(5);
//                }
//                else{
//                    role = new Role(4);
//                }
//                waitingType = WaitingType.GET_EMPLOYEE;
//                return COMEBACK;
//            case GET_EMPLOYEE:
//                deleteMessage(updateMessageId);
//                deleteMessage(lastSendMessageID);
//
//
//                if (hasContact()){
//                    String phone = update.getMessage().getContact().getPhoneNumber();
//
//                    if (phone.charAt(0) == '8') {
//                        phone = phone.replaceFirst("8", "+7");
//                    } else if (phone.charAt(0) == '7') {
//                        phone = phone.replaceFirst("7", "+7");
//                    }
//
//                    user = userRepository.findByPhone(phone);
//                }
//                else if (hasMessageText() && isPhoneNumber(updateMessageText)){
//                    String phone = updateMessageText;
//
//                    if (phone.charAt(0) == '8') {
//                        phone = phone.replaceFirst("8", "+7");
//                    } else if (phone.charAt(0) == '7') {
//                        phone = phone.replaceFirst("7", "+7");
//                    }
//
//                    user = userRepository.findByPhone(phone);
//
//                }
//                else{
//                    wrongData();
//
//                }
//                showEmployee();
//                return COMEBACK;
//
//            case CONFIRM:
//                deleteMessage(updateMessageId);
//                deleteMessage(lastSendMessageID);
//
//                if(hasCallbackQuery()){
//                    if(isButton(56)){
//
//                        if(role.getId()==3){
//                            user.getRoles().add(role);
//
//                            courier = courierRepository.findCourierByUser(user);
//                            if(courier==null){
//                                courier = new Courier();
//                                courier.setUser(user);
//
//                            }
//
//                            user = userRepository.save(user);
//                            getPhoto();
//                            waitingType = WaitingType.SET_PHOTO;
//                            return COMEBACK;
//                        }
//
////
//                        else if(role.getId()==6){
//                            user.getRoles().add(role);
//                            branches = restaurantBranchRepo.findAll();
//                            showBranches();
//                            waitingType = WaitingType.CHOOSE_BRANCH;
//                            return COMEBACK;
//                        }
//                        else if(role.getId()==5){
//                            user.getRoles().add(role);
//                             user = userRepository.save(user);
//                            sendMessage(getText(142));
//                            return EXIT;
//                        }
//                        else if(role.getId()==4){
//                            user.getRoles().add(role);
//                            user = userRepository.save(user);
//                            sendMessage(getText(142));
//                            return EXIT;
//                        }
//                    }
//
//                    else{
//                        getEmployee();
//                        waitingType = WaitingType.GET_EMPLOYEE;
//                    }
//                }
//                return COMEBACK;
//            case CHOOSE_BRANCH:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()) {
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        editMessageWithKeyboard(getText(209), updateMessageId, buttonsLeaf.getListButtonInline());
//                    }
//                    deleteMessage(updateMessageId);
//                    restaurantBranch = branches.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//                    user.setRestaurantBranch(restaurantBranch);
//                    user = userRepository.save(user);
//                    sendMessage(getText(142));
//
//                } else {
//                    wrongData();
//                    showBranches();
//                }
//                return COMEBACK;
//            case SET_PHOTO:
//                deleteMessage(updateMessageId);
//                deleteMessage(lastSendMessageID);
//
//                if(hasCallbackQuery()){
//                    sendMessage(getText(142));
//                    courierRepository.save(courier);
//
//                }
//                else if (update.getMessage().hasPhoto()) {
//                    courier.setPhotoUrl(updateMessagePhoto);
//                    courierRepository.save(courier);
//                    sendMessage(getText(142));
//                } else {
//                    wrongData();
//                    getPhoto();
//                    return COMEBACK;
//                }
//                return EXIT;
//
//        }
//        return false;
//    }
//    private int showBranches() throws TelegramApiException{
//        List<String> finalList = new ArrayList<>();
//        List<String> finalList2 = new ArrayList<>();
//        branches.forEach((e) -> {
//            finalList.add(e.getId() + " | " + e.getBranchName());
//            finalList2.add(String.valueOf(e.getId()));
//        });
////        finalList.add(back);
////        finalList2.add(String.valueOf(foods.size() + 1));
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10);
////        editMessageWithKeyboard(getText(39), updateMessageId, buttonsLeaf.getListButtonInline());
//        return sendMessageWithKeyboard(getText(209), buttonsLeaf.getListButtonInline());
//    }
//    private boolean isPhoneNumber(String phone) {
//
//        if (phone.charAt(0) == '8') {
//            phone = phone.replaceFirst("8", "+7");
//        } else if (phone.charAt(0) == '7') {
//            phone = phone.replaceFirst("7", "+7");
//        }
//        return phone.charAt(0) == '+' && phone.charAt(1) == '7' && phone.substring(2).length() == 10 && isLong(phone.substring(2)) ;
//    }
//
//    public int getPhoto() throws TelegramApiException {
//        return sendMessageWithKeyboard(getText(Const.GET_PHOTO),23);
//    }
//
//    private int showEmployee() throws TelegramApiException {
//        if(user ==null){
//            waitingType = WaitingType.GET_EMPLOYEE;
//            return sendMessage(getText(94));
//        }
//        waitingType = WaitingType.CONFIRM;
//        return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(91), user.getFullName(), user.getPhone())+"\n"+getText(95), 16));
//    }
//    private void tryAgain() throws TelegramApiException {
//        wrongData();
//        getEmployee();
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//    private int getEmployee() throws TelegramApiException{
//        return sendMessage(getText(93));
//    }
//}
