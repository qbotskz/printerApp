//package com.akimatBot.command.impl.courier;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Courier;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class id057_courier_profile_settings extends Command {
//    private User user;
//    private Courier courier;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//
//
//        if(!isCourier(chatId)){
//            sendMessage(Const.NO_ACCESS);
//            return EXIT;
//        }
//        switch (waitingType){
//            case START:
//                user = userRepository.findByChatId(chatId);
//                courier = courierRepository.findCourierByUser(user);
//                sendMessageWithPhotoAndKeyboard(String.format(getText(91), user.getFullName(), user.getPhone())+"\n"+getText(158), 60, courier.getPhotoUrl(), chatId);
//        }
//        return false;
//    }
//}
