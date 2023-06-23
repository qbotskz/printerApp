//package com.akimatBot.command.impl.Responsible;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.SuggestionComplaint;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import lombok.SneakyThrows;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//
//public class id032_SendingSuggestionToResponsible extends Command {
//    private SuggestionComplaint suggestion;
//    private List<User> responsiblePeople;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                parseSuggestion();
//                if(isButton(134)){
//                    if(suggestion.getResponsible()!=null){
//                        sendMessage(String.format(getText(106), suggestion.getResponsible().getFullName(), suggestion.getId()));
//                        return EXIT;
//                    }
//                    responsiblePeople = userRepository.findAllByRolesContains(new Role(5));
//                    suggestion.setResponsible(userRepository.findByChatId(chatId));
//
//                    Thread sendingThread = new Thread(){
//                        int messageCount = 0;
//                        @SneakyThrows
//                        @Override
//                        public void run(){
//                            for(User u:responsiblePeople){
//
//                                if(messageCount >= 10){
//                                    try{
//                                        Thread.sleep(1500);
//                                    }
//                                    catch (Exception e){
//                                        e.printStackTrace();
//                                    }
//                                    messageCount = 0;
//                                }
////                                if(u.getId() == suggestion.getResponsible().getId()){
////                                    continue;
////                                }
////                                sendMessage(String.format(getText(106), suggestion.getResponsible().getFullName(), suggestion.getId()));
//                                messageCount++;
//                            }
//
//                        }
//                    };
//                    sendingThread.start();
//
//                    suggestion = suggestionComplaintRepo.save(suggestion);
//
//                    getText();
//                    waitingType = WaitingType.SET_TEXT;
//                    return COMEBACK;
//                }
//                else{
//                    deleteMessage(updateMessageId);
//                    sendMessage(String.format(getText(108), suggestion.getId()));
//                    return EXIT;
//                }
//            case SET_TEXT:
//                if(update.hasMessage() && update.getMessage().hasText()){
//                    suggestion.setResponse(update.getMessage().getText());
//                    suggestion.setAnswered(true);
//                    suggestion = suggestionComplaintRepo.save(suggestion);
//                    sendMessage(String.format(getText(109), suggestion.getResponsible().getFullName(), suggestion.getId(), suggestion.getResponse()), suggestion.getUser().getChatId());
//                    sendMessage(getText(110));
//                    return EXIT;
//                }
//        }
//
//        return false;
//    }
//    public int getText() throws TelegramApiException{
//        return sendMessage(String.format(getText(107), suggestion.getId()));
//    }
//    public void parseSuggestion(){
////        String myString = getCallbackQuery().getMessage().getText();
////        Scanner scanner = new Scanner(myString);
////        String line="";
////        while (scanner.hasNextLine()) {
////            line = scanner.nextLine();
////            break;
////        }
////        scanner.close();
//        String firstRow="";
//        if(getCallbackQuery().getMessage().hasText()){
//            firstRow = getCallbackQuery().getMessage().getText().split("\n")[0];
//
//        }
//        else{
//            if(updateMessage.getText()==null){
//                firstRow = updateMessage.getCaption().split("\n")[0];
//
//            }
//            else{
//                firstRow = updateMessage.getText().split("\n")[0];
//            }
//        }
//        int suggestionId = Integer.parseInt(firstRow.substring(firstRow.lastIndexOf("№") + 1));
//        suggestion = suggestionComplaintRepo.findSuggestionComplaintById(suggestionId);
//    }
//
//}
