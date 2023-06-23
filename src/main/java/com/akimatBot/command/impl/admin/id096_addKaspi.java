//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.KaspiAccount;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class id096_addKaspi extends Command {
//    private KaspiAccount kaspiAccount;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        if (!isAdmin()) {
//            sendMessage(Const.NO_ACCESS);
//            return EXIT;
//        }
//        switch (waitingType){
//            case START:
//                deleteMessage(updateMessageId);
//                getPhone();
//                kaspiAccount = new KaspiAccount();
//                waitingType = WaitingType.GET_PHONE;
//                return COMEBACK;
//            case GET_PHONE:
//                deleteMessage(updateMessageId);
//
//                if (hasMessageText() && isPhoneNumber(updateMessageText)){
//                    String phone = updateMessageText;
//
//                    if (phone.charAt(0) == '8') {
//                        phone = phone.replaceFirst("8", "+7");
//                    } else if (phone.charAt(0) == '7') {
//                        phone = phone.replaceFirst("7", "+7");
//                    }
//
//                    kaspiAccount.setPhone(phone);
//                    getName();
//                    waitingType = WaitingType.GET_NAME;
//                }
//                else{
//                    wrongData();
//                }
//                return COMEBACK;
//            case GET_NAME:
//                deleteMessage(updateMessageId);
//
//                if(hasMessageText()){
//                    kaspiAccount.setName(updateMessageText);
//                    kaspiAccountsRepository.save(kaspiAccount);
//                    sendMessage(142);
//                    return EXIT;
//                }
//                else{
//                    wrongData();
//                    return COMEBACK;
//                }
//        }
//        return EXIT;
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//    public int getName() throws TelegramApiException{
//        return sendMessage("Введите имя владельца номера");
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
//    public int getPhone() throws TelegramApiException{
//        return sendMessage("Введите номер каспи");
//    }
//}
