//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.UserRepository;
//import com.akimatBot.utils.Const;
//import lombok.SneakyThrows;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//
//public class id010_mailing_for_clients extends Command {
//    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
//    private final Role role = new Role(2);
//    private final List<User> users = userRepository.findAllByRolesContains(role);
//    private String text;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                deleteMessage(updateMessageId);
//                getMailing();
//                waitingType = WaitingType.SET_TEXT;
//                return COMEBACK;
//            case SET_TEXT:
//                deleteMessage(updateMessageId);
//                if(update.hasMessage() && update.getMessage().hasText()){
//                    text = update.getMessage().getText();
//                    Thread sendingThread = new Thread() {
//                        int messageCount = 0;
//
//                        @SneakyThrows
//                        @Override
//                        public void run() {
//                            for (User u : users) {
//
//                                if (messageCount >= 10) {
//                                    try {
//                                        Thread.sleep(1500);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                    messageCount = 0;
//                                }
//
//                                try{
//                                    sendMessage(text, u.getChatId());
//                                    messageCount++;
//                                }
//                                catch (Exception e){
//                                    System.out.println(u.getChatId()+" - CHAT NOT FOUND");
//                                }
//
//                            }
//
//                        }
//                    };
//                    sendingThread.start();
//                }
//                else{
//                    wrongData();
//                    getMailing();
//                }
//        }
//        return false;
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//    public int getMailing() throws TelegramApiException{
//        return sendMessage(getText(33), chatId);
//    }
//}
