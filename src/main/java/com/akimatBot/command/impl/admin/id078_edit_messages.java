//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.enums.Language;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Message;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class id078_edit_messages extends Command {
//    private int inlineMessId;
//    private int wrongMessId;
//    private int infoMessId;
//    private int notFoundMess;
//
//    private Message currentMessage;
//    private Language currentLang;
//
//    private List<Message> searchResultMessage;
//    @Override
//    public boolean execute() throws TelegramApiException {
//
//        if (!isAdmin()){
//            sendMessage(getText(Const.NO_ACCESS));
//            return EXIT;
//        }
//
//        switch (waitingType) {
//            case START:
//                deleteUpdateMess();
////                if (isButton(45)){ // editing mess
////                    infoMessId = sendMessage(186);
////                    waitingType = WaitingType.SEARCH_BUTTON;
////                }
////                else {
////                    sendMessageWithKeyboard(getText(59), 18);
////                }
//                infoMessId = sendMessage(186);
//                waitingType = WaitingType.SEARCH_BUTTON;
//                return COMEBACK;
//            case SEARCH_BUTTON:
//                currentLang = getLanguage(chatId);
//                deleteUpdateMess();
//                deleteNotFoundMess();
//                if (hasMessageText()){
//                    searchResultMessage = messageRepository.findAllByNameContainingAndLangIdOrderById(updateMessageText, currentLang.getId());
//                    if (searchResultMessage.size() != 0){
//                        deleteMessage(infoMessId);
//                        inlineMessId = sendMessage(getInfoMessages(searchResultMessage));
//                        waitingType = WaitingType.CHOOSE_OPTION;
//                    }
//                    else {
//                        sendNotFound();
//                    }
//                }
//                else {
//                    sendNotFound();
//                }
//                return COMEBACK;
//
//            case CHOOSE_OPTION:
//                deleteUpdateMess();
//                deleteWrongMess();
//                if (updateMessageText.contains("/editName")){ //edit name
//                    currentMessage = messageRepository.findByIdAndLangId(getLong(updateMessageText.substring(9)), currentLang.getId());
//                    if (currentMessage == null) {
//                        sendWrongData();
//                        return COMEBACK;
//                    }
//
//                    deleteMessage(inlineMessId);
//                    inlineMessId = sendMessage(getInfoForEdit(currentMessage));
////                    editMessage(getInfoMessage(currentMessage), inlineMessId);
////                    infoMessId = sendMessage(57);
//                    waitingType = WaitingType.SET_TEXT;
//                }
//                else if (updateMessageText.contains("/back")){ // back
//                    deleteMessage(infoMessId);
//                    deleteMessage(inlineMessId);
//                    infoMessId = sendMessage(186);
//                    waitingType = WaitingType.SEARCH_BUTTON;
//                }
//                else if (updateMessageText.contains("/swapLanguage")){ //swap lang
//                    if (currentLang.getId() == 1)
//                        currentLang = Language.kz;
//                    else
//                        currentLang = Language.ru;
//
//                    List<Message> newSearchRes = new ArrayList<>();
//                    for (Message message : searchResultMessage){
//                        newSearchRes.add(messageRepository.findByIdAndLangId(message.getId(), currentLang.getId()));
//                    }
//
//                    searchResultMessage = newSearchRes;
//                    if (currentMessage != null)
//                        currentMessage = messageRepository.findByIdAndLangId(currentMessage.getId(), currentLang.getId());
//
//
//                    newSearchRes = null;
////                    editMessage(getInfoMessages(searchResultMessage), inlineMessId);
//                    editMessage(getInfoMessages(searchResultMessage), inlineMessId);
//
//                }
//                else{
//                    sendWrongData();
//                }
//                return COMEBACK;
//
//            case SET_TEXT:
//                deleteUpdateMess();
//                deleteWrongMess();
//                if (hasMessageText()){
//                    if (updateMessageText.equals("/cancel")){
//                        deleteMessage(infoMessId);
//                        deleteMessage(inlineMessId);
//                        inlineMessId = sendMessage(getInfoMessages(searchResultMessage));
//                        waitingType = WaitingType.CHOOSE_OPTION;
//                        return COMEBACK;
//                    }
//                    else {
//                        messageRepository.update(updateMessageText, currentMessage.getId(), currentLang.getId());
//                        deleteMessage(inlineMessId);
//                        deleteMessage(infoMessId);
//                        currentMessage = messageRepository.findByIdAndLangId(currentMessage.getId(), currentLang.getId());
//                        searchResultMessage =  updateMessages(searchResultMessage);
//
//                        inlineMessId = sendMessage(getInfoMessages(searchResultMessage));
//                        waitingType = WaitingType.CHOOSE_OPTION;
//                    }
//                }
//                else{
//                    sendWrongData();
//                }
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//    private List<Message> updateMessages(List<Message> searchResultMessage) {
//        List<Message> newSearchRes = new ArrayList<>();
//        for (Message message : searchResultMessage){
//            newSearchRes.add(messageRepository.findByIdAndLangId(message.getId(), currentLang.getId()));
//        }
//
//        return newSearchRes;
//    }
//
//    private String getInfoForEdit(Message currentMessage) {
//        return getText(189) + currentMessage.getName() + next +
//                getText(188);
//    }
//
//    private void deleteNotFoundMess() {
//        if (notFoundMess != 0){
//            deleteMessage(notFoundMess);
//        }
//    }
//
//    private String getInfoMessages(List<Message> searchResultMessages) {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(getText(190)).append(next).append(next);
//
//        for (Message message : searchResultMessages){
//            stringBuilder.append(message.getName()).append(" \uD83D\uDD8A /editName").append(message.getId()).append(next).append(next);
//        }
//
//        stringBuilder.append(next).append(next).append(getText(191)).append(currentLang.name()).append(next);
//
//        stringBuilder.append("/swapLanguage").append(" ").append(getText(192)).append(next)
//                .append("/back").append(" ").append(getText(193));
//
//        return stringBuilder.toString();
//    }
//
//    private void sendNotFound() throws TelegramApiException {
//        deleteMessage(updateMessageId);
//        deleteNotFoundMess();
//        notFoundMess = sendMessage(194, chatId);
//    }
//
//
//    private Long getLongTemp(String updateMessageText) {
//        try {
//            return Long.parseLong(updateMessageText);
//        }catch (Exception e){
//            return -1L;
//        }
//    }
//
//    private void deleteUpdateMess() {
//        deleteMessage(updateMessageId);
//    }
//
//
//    private void deleteWrongMess(){
//        if (wrongMessId != 0)
//            deleteMessage(wrongMessId);
//    }
//
//    private void sendWrongData() throws TelegramApiException {
//        deleteMessage(updateMessageId);
//        deleteWrongMess();
//        wrongMessId = sendMessage(Const.WRONG_DATA_TEXT, chatId);
//
//    }
//}
