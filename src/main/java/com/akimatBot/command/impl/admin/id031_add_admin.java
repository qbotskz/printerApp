//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class id031_add_admin extends Command {
//    private Role role;
//    private User user;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                deleteMessage(lastSendMessageID);
//                deleteMessage(updateMessageId);
//                getEmployee();
//                role = new Role(1);
//                waitingType = WaitingType.GET_EMPLOYEE;
//                return COMEBACK;
//            case GET_EMPLOYEE:
//                deleteMessage(updateMessageId);
//                deleteMessage(lastSendMessageID);
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
//            case CONFIRM:
//                deleteMessage(updateMessageId);
//                deleteMessage(lastSendMessageID);
//
//                if(hasCallbackQuery()){
//                    if(isButton(56)){
//                        user.getRoles().add(role);
//                        user = userRepository.save(user);
//                        sendMessage(getText(142));
//                        return EXIT;
//                    }
//                    else{
//                        getEmployee();
//                        waitingType = WaitingType.GET_EMPLOYEE;
//                    }
//                }
//                return COMEBACK;
//
//        }
//        return false;
//    }
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
//    private boolean isPhoneNumber(String phone) {
//
//        if (phone.charAt(0) == '8') {
//            phone = phone.replaceFirst("8", "+7");
//        } else if (phone.charAt(0) == '7') {
//            phone = phone.replaceFirst("7", "+7");
//        }
//        return phone.charAt(0) == '+' && phone.charAt(1) == '7' && phone.substring(2).length() == 10 && isLong(phone.substring(2)) ;
//    }
//}
