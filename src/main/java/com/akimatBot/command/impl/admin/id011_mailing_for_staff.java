//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.UserRepository;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//
//public class id011_mailing_for_staff extends Command {
//    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
//    private List<User> users;
//    private String text;
//    private List<Role> roles;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                sendMessageWithKeyboard(getText(34), 26);
//                //getMailing();
//                waitingType = WaitingType.CHOOSE_STAFF;
//                return COMEBACK;
//            case CHOOSE_STAFF:
//                if(hasCallbackQuery()){
//                    getMailing();
//                    if(isButton(74)){
//                        waitingType = WaitingType.ALL_STAFF;
//                    }
//                    else if(isButton(75)){
//                        waitingType = WaitingType.COURIER;
//                    }
//                    else{
//                        waitingType = WaitingType.OPERATOR;
//                    }
//                }
//                else{
//                    wrongData();
//                    sendMessageWithKeyboard(getText(34), 26);
//                }
//                return COMEBACK;
//            case ALL_STAFF:
//                if(update.getMessage().hasText() && update.hasMessage()){
//                    text = update.getMessage().getText();
//                    users = userRepository.findAllByRolesContains(new Role(3));
//                    users.addAll(userRepository.findAllByRolesContains(new Role(4)));
//                    sendMailing();
//                    return EXIT;
//                }
//                else{
//                    wrongData();
//                    getMailing();
//                }
//                return COMEBACK;
//            case COURIER:
//                if(update.getMessage().hasText() && update.hasMessage()){
//                    text = update.getMessage().getText();
//                    users = userRepository.findAllByRolesContains(new Role(3));
//                    return EXIT;
//                }
//                else{
//                    wrongData();
//                    getMailing();
//                }
//                return COMEBACK;
//            case OPERATOR:
//                if(update.getMessage().hasText() && update.hasMessage()){
//                    text = update.getMessage().getText();
//                    users = userRepository.findAllByRolesContains(new Role(4));
//                    return EXIT;
//                }
//                else{
//                    wrongData();
//                    getMailing();
//                }
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//    private void sendMailing() throws TelegramApiException {
//        for(User u : users){
//            sendMessage(text, u.getChatId());
//        }
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//    public int getMailing() throws TelegramApiException{
//        return sendMessage(getText(33), chatId);
//    }
//}
