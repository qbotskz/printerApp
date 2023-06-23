//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
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
//public class id030_showAdmins extends Command {
//    private User user;
//    private List<User> admins;
//    private List<String> finalList;
//    private List<String> finalList2;
//    private ButtonsLeaf buttonsLeaf;
//    private WaitingType editWaitingType;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//
//        switch (waitingType){
//            case START:
//                admins = userRepository.findAllByRolesContains(new Role(1));
//                showAdmins();
//                waitingType = WaitingType.CHOOSE_EMPLOYEE;
//                return COMEBACK;
//            case CHOOSE_EMPLOYEE:
//                deleteMessage(updateMessageId);
//
//                if (hasCallbackQuery()) {
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        editMessageWithKeyboard(getText(90), updateMessageId, buttonsLeaf.getListButtonInline());
//                    }
//                    deleteMessage(updateMessageId);
//                    user = admins.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//
//                    showAdmin();
//                    waitingType = WaitingType.SHOW_EMPLOYEE;
//                } else {
//                    wrongData();
//                    showAdmins();
//                }
//                return COMEBACK;
//            case SHOW_EMPLOYEE:
//                deleteMessage(updateMessageId);
//
//                if (hasCallbackQuery()) {
//                    if (isButton(82)) {
//                        getName();
//                        editWaitingType = WaitingType.EDIT_NAME;
//                    }
//                    else if(isButton(129)){
//                        getPhone();
//                        editWaitingType = WaitingType.CHANGE_PHONE;
//
//                    }
//                    else if(isButton(130)){
//                        user.deleteAdminRole();
//
//                        user = userRepository.save(user);
//                        admins = userRepository.findAllByRolesContains(new Role(1));
//                        showAdmin();
//                        return COMEBACK;
//                    }
//                    else if(isButton(131)){
//                        user.getRoles().add(new Role(1));
//                        user = userRepository.save(user);
//                        showAdmin();
//                        return COMEBACK;
//                    }
//                    else if (isButton(67)) {
//                        showAdmins();
//                        waitingType = WaitingType.CHOOSE_EMPLOYEE;
//                        return COMEBACK;
//                    }
//                    waitingType = WaitingType.EDIT_EMPLOYEE;
//                }
//                else {
//                    wrongData();
//                    showAdmin();
//                }
//                return COMEBACK;
//            case EDIT_EMPLOYEE:
//                deleteMessage(updateMessageId);
//
//                if (update.hasMessage()) {
//                    switch (editWaitingType) {
//                        case EDIT_NAME:
//                            if (update.getMessage().hasText()) {
//                                user.setFullName(update.getMessage().getText());
//                            } else {
//                                wrongData();
//                                getName();
//                            }
//                            break;
//
//                        case CHANGE_PHONE:
//                            if(update.getMessage().hasText()){
//                                user.setPhone(update.getMessage().getText());
//
//                            }
//                            else{
//                                wrongData();
//                                getPhone();
//                            }
//                    }
//                    userRepository.save(user);
//                    showAdmin();
//                    waitingType = WaitingType.SHOW_EMPLOYEE;
//                    return COMEBACK;
//
//                }
//        }
//
//
//        return false;
//    }
//    private int showAdmins() throws TelegramApiException {
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        admins.forEach((e) -> {
//            finalList.add(e.getFullName());
//            finalList2.add(String.valueOf(e.getId()));
//        });
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10);
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(98), buttonsLeaf.getListButtonInline()));
//    }
//    private int showAdmin() throws TelegramApiException{
//        if (isAdmin(user)){
//            return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(91), user.getFullName(), user.getPhone()), 48));
//        }
//        return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(91), user.getFullName(), user.getPhone()), 49));
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//    private int getPhone() throws TelegramApiException {
//        return sendMessage(getText(Const.GET_PHONE));
//    }
//    private int getName() throws TelegramApiException {
//        return sendMessage(getText(92));
//    }
//}
