//package com.akimatBot.command.impl;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.SuggestionComplaint;
//import com.akimatBot.entity.enums.AppealType;
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
//import java.util.Date;
//import java.util.List;
//
//public class id008_Complaint extends Command {
//    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
//    private SuggestionComplaint complaint;
//    private User user;
//    private String phone;
//    private String name;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                sendMessageWithKeyboard("----",21);
//                user = userRepository.findByChatId(chatId);
//                complaint = new SuggestionComplaint();
//                complaint.setAppealType(AppealType.COMPLAINT);
//                complaint.setUser(user);
//                confirmName(user.getFullName());
//                waitingType = WaitingType.CONFIRM_NAME;
//                return COMEBACK;
//            case CONFIRM_NAME:
//                deleteMessage(updateMessageId);
//                if(hasCallbackQuery()){
//                    if(isButton(56)){
//                        if(user.getPhone()==null){
//                            getPhone();
//                            waitingType = WaitingType.CHANGE_PHONE;
//                        }
//                        else{
//                            confirmPhone(user.getPhone());
//                            waitingType = WaitingType.CONFIRM_PHONE;
//                        }
//                    }
//                    else{
//                        getName();
//                        waitingType = WaitingType.CHANGE_NAME;
//                    }
//                }
//                else{
//                    wrongData();
//                    confirmName(user.getFullName());
//                }
//                return COMEBACK;
//            case CHANGE_NAME:
//                deleteMessage(updateMessageId);
//                if(update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().length() <= 50){
//
//                    name = update.getMessage().getText();
//                    user.setFullName(name);
//                    confirmName(name);
//                    waitingType = WaitingType.CONFIRM_NAME;
//
//                }
//                else{
//                    wrongData();
//                    getName();
//                }
//                return COMEBACK;
//            case CONFIRM_PHONE:
//                deleteMessage(updateMessageId);
//                deleteMessage(lastSendMessageID);
//                if (hasCallbackQuery()) {
//                    if (isButton(56)) {
//
//                        getComplaint();
//                        waitingType = WaitingType.GET_COMPLAINT;
//
//                    } else if (isButton(57)) {
//                        getPhone();
//                        waitingType = WaitingType.CHANGE_PHONE;
//                    }
//                }
//                else {
//                    confirmPhone(user.getPhone());
//                }
//                return COMEBACK;
//            case CHANGE_PHONE:
//                deleteMessage(updateMessageId);
//                if(botUtils.hasContactOwner(update)){
//                    String phone = update.getMessage().getContact().getPhoneNumber();
//                    if (phone.charAt(0) == '7') {
//                        phone = phone.replaceFirst("7", "+7");
//                    }
//                    user.setPhone(phone);
//                    getComplaint();
//                    waitingType = WaitingType.GET_COMPLAINT;
//                }
//                else{
//                    wrongData();
//                    getPhone();
//                }
//                return COMEBACK;
//            case GET_COMPLAINT:
//                if(update.hasMessage() && update.getMessage().hasText()){
//                    complaint.setText(update.getMessage().getText());
//                    attachMedia();
//                    waitingType = WaitingType.ATTACH_MEDIA;
//                }
//                else {
//                    wrongData();
//                    getComplaint();
//                }
//                return COMEBACK;
//            case ATTACH_MEDIA:
//                deleteMessage(updateMessageId);
//                if(hasCallbackQuery()){
//                    if(isButton(69)){
//                        finishComplaint();
//                        return EXIT;
//                    }
//                }
//                else if(update.getMessage().hasPhoto()){
//                    complaint.setPhotoUrl(update.getMessage().getPhoto().get(0).getFileId());
//                    finishComplaint();
//                    return EXIT;
//                }
//                else if(update.getMessage().hasVideo()){
//                    complaint.setVideoUrl(update.getMessage().getVideo().getFileId());
//
//                    finishComplaint();
//                    return EXIT;
//                }
//                else{
//                    wrongData();
//                    attachMedia();
//                }
//                return COMEBACK;
//        }
//        return false;
//    }
//    private int finishComplaint() throws TelegramApiException {
//        complaint.setUploadedDate(new Date());
//        complaint.setAnswered(false );
//        suggestionComplaintRepo.save(complaint);
//        List<User> users = userRepository.findAllByRolesContains(new Role(5));
//        Thread sendingThread = new Thread(){
//            int messageCount = 0;
//            @SneakyThrows
//            @Override
//            public void run(){
//                for(User u:users){
//
//                    if(messageCount >= 10){
//                        try{
//                            Thread.sleep(1500);
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        messageCount = 0;
//                    }
//
//                    if(complaint.getPhotoUrl()==null && complaint.getVideoUrl()==null) {
//                        sendMessageWithKeyboardWithChatId(String.format(getText(105), complaint.getId(), complaint.getUser().getFullName(), complaint.getUser().getPhone(), complaint.getText()), 56,u.getChatId());
//                    }
//                    else{
//                        if(complaint.getPhotoUrl()!=null){
//                            sendMessageWithPhotoAndKeyboard(String.format(getText(105), complaint.getId(), complaint.getUser().getFullName(), complaint.getUser().getPhone(), complaint.getText()),56,complaint.getPhotoUrl(), u.getChatId());
//
//                        }
//                        else if(complaint.getVideoUrl()!=null){
//                            sendMessageWithVideoAndKeyboard(String.format(getText(105), complaint.getId(), complaint.getUser().getFullName(), complaint.getUser().getPhone(), complaint.getText()), 56, complaint.getVideoUrl(), u.getChatId());
//                        }
//                    }
//                    messageCount++;
//                }
//
//            }
//        };
//        sendingThread.start();
//        return sendMessageWithKeyboard(String.format(getText(29), user.getFullName(), complaint.getId()), 2);
//
//    }
//    private int confirmPhone(String phone) throws TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(Const.CONFIRM_PHONE), user.getPhone()), 62));
//    }
//    private int getPhone() throws TelegramApiException {
//        return sendMessageWithKeyboard(getText(Const.GET_PHONE), 12);
//    }
//    private int attachMedia() throws TelegramApiException {
//        return sendMessageWithKeyboard(getText(Const.ATTACH_MEDIA), 23);
//    }
//    private int getComplaint() throws TelegramApiException {
//        return sendMessageWithKeyboard(getText(Const.GET_COMPLAINT_TEXT), 21);
//    }
//    private int getName() throws TelegramApiException {
//        return botUtils.sendMessage(Const.GET_NAME, chatId);
//    }
//    private int confirmName(String name) throws TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(Const.CONFIRM_NAME), name), 16));
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//}
