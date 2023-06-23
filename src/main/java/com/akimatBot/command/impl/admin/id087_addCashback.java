//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Cashback;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//
//public class id087_addCashback extends Command {
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        if (!isAdmin()) {
//            sendMessage(Const.NO_ACCESS);
//            return EXIT;
//        }
//        switch (waitingType){
//            case START:
//                deleteMessage(updateMessageId);
//                getCashbackPercentage();
//                waitingType = WaitingType.SET_CASHBACK;
//                return COMEBACK;
//            case SET_CASHBACK:
//                if(update.hasMessage()&&update.getMessage().hasText() && isNumeric(update.getMessage().getText())){
//                    deleteMessage(updateMessageId);
//
//                    Cashback cashback = new Cashback();
//                    cashback.setCashbackPercentage(Integer.parseInt(update.getMessage().getText()));
//
//
//                    List<Cashback> cashbacks = cashbackRepository.findAll();
//                    if(!cashbacks.isEmpty()){
//                        for(Cashback c:cashbacks){
//                            cashbackRepository.delete(c);
//                        }
//                    }
//                    cashbackRepository.save(cashback);
//
//                    sendMessage(getText(142));
//                    return EXIT;
//                }
//                else{
//                    wrongData();
//                    getCashbackPercentage();
//                    return COMEBACK;
//                }
//        }
//        return EXIT;
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
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
//
//    private void getCashbackPercentage() throws TelegramApiException {
//        sendMessage(getText(202));
//    }
//}
