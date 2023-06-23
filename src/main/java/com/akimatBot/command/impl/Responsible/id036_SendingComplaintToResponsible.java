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
//public class id036_SendingComplaintToResponsible extends Command {
//    private SuggestionComplaint complaint;
//    private List<User> responsiblePeople;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                parseComplaint();
//                if(isButton(145)){
//                    if(complaint.getResponsible()!=null){
//                        sendMessage(String.format(getText(125), complaint.getResponsible().getFullName(), complaint.getId()));
//                        return EXIT;
//                    }
//                    responsiblePeople = userRepository.findAllByRolesContains(new Role(5));
//                    complaint.setResponsible(userRepository.findByChatId(chatId));
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
////                                if(!u.getChatId().equals(complaint.getResponsible().getChatId()) ){
////                                    sendMessage(String.format(getText(125), complaint.getResponsible().getFullName(), complaint.getId()));
////                                }
//
//                                messageCount++;
//                            }
//
//                        }
//                    };
//                    sendingThread.start();
//
//
//                    complaint = suggestionComplaintRepo.save(complaint);
//
//                    getText();
//                    waitingType = WaitingType.SET_TEXT;
//                    return COMEBACK;
//                }
//                else{
//                    deleteMessage(updateMessageId);
//                    sendMessage(String.format(getText(126), complaint.getId()));
//                    return EXIT;
//                }
//            case SET_TEXT:
//                if(update.hasMessage() && update.getMessage().hasText()){
//                    complaint.setResponse(update.getMessage().getText());
//                    complaint.setAnswered(true);
//                    complaint = suggestionComplaintRepo.save(complaint);
//                    sendMessage(String.format(getText(127), complaint.getResponsible().getFullName(), complaint.getId(), complaint.getResponse()), complaint.getUser().getChatId());
//                    sendMessage(getText(110));
//                    return EXIT;
//                }
//        }
//
//        return false;
//    }
//    public int getText() throws TelegramApiException{
//        return sendMessage(String.format(getText(128), complaint.getId()));
//    }
//    public void parseComplaint(){
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
//             firstRow = getCallbackQuery().getMessage().getText().split("\n")[0];
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
//        int complaintId = Integer.parseInt(firstRow.substring(firstRow.lastIndexOf("№") + 1));
//        complaint = suggestionComplaintRepo.findSuggestionComplaintById(complaintId);
//    }
//
//}
