//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.enums.Language;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Button;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class id077_edit_buttons extends Command {
//    private int inlineMessId;
//    private int wrongMessId;
//    private int infoMessId;
//    private int notFoundMess;
//    private Button currentButton;
//    private Language currentLang ;
//    private List<Button> searchResultButtons;
//    @Override
//    public boolean execute() throws TelegramApiException {
//        if (!isAdmin()) {
//            sendMessage(getText(Const.NO_ACCESS));
//            return EXIT;
//        }
//        switch (waitingType) {
//            case START:
//                deleteUpdateMess();
////                if (isButton(44)) { // editing button
////                    infoMessId = sendMessage(186);
////                    waitingType = WaitingType.SEARCH_BUTTON;
////                } else {
////                    //sendMessageWithKeyboard(getText(59), 18);
////                }
//                infoMessId = sendMessage(186);
//                currentLang = getLanguage(chatId);
//                waitingType = WaitingType.SEARCH_BUTTON;
//                return COMEBACK;
//            case SEARCH_BUTTON:
//                deleteUpdateMess();
//                if (hasMessageText()) {
//                    currentLang = getLanguage(chatId);
//                    searchResultButtons = buttonRepository.findAllByNameContainingAndLangIdOrderById(updateMessageText, currentLang.getId());
//                    if (searchResultButtons.size() != 0) {
//                        deleteMessage(notFoundMess);
//                        deleteMessage(infoMessId);
//                        inlineMessId = sendMessage(getInfoButtons(searchResultButtons));
//                        waitingType = WaitingType.CHOOSE_OPTION;
//                    } else {
//                        sendNotFound();
//                    }
//                } else {
//                    deleteUpdateMess();
//                    sendWrongData();
//                }
//                return COMEBACK;
//            case CHOOSE_OPTION:
//                deleteUpdateMess();
//                if (updateMessageText.contains("/editName")) { //edit name
//                    currentButton = buttonRepository.findByIdAndLangId(getLongEdit(updateMessageText.substring(9)), currentLang.getId());
//                    if (currentButton == null) {
//                        sendWrongData();
//                        return COMEBACK;
//                    }
//                    deleteMessage(inlineMessId);
//                    inlineMessId = sendMessage(getInfoForEdit(currentButton));
////                        editMessage(getInfoForEdit(currentButton), inlineMessId);
////                        infoMessId = sendMessage(188);
//                    waitingType = WaitingType.SET_TEXT;
//                } else if (updateMessageText.contains("/back")) { // back
//                    deleteMessage(infoMessId);
//                    deleteMessage(inlineMessId);
//                    infoMessId = sendMessage(186);
//                    waitingType = WaitingType.SEARCH_BUTTON;
//                } else if (updateMessageText.contains("/swapLanguage")) { //swap lang
//                    if (currentLang.getId() == 1)
//                        currentLang = Language.kz;
//                    else
//                        currentLang = Language.ru;
//                    List<Button> newSearchRes = new ArrayList<>();
//                    for (Button button : searchResultButtons) {
//                        newSearchRes.add(buttonRepository.findByIdAndLangId(button.getId(), currentLang.getId()));
//                    }
//                    searchResultButtons = newSearchRes;
//                    if (currentButton != null)
//                        currentButton = buttonRepository.findByIdAndLangId(currentButton.getId(), currentLang.getId());
//                    newSearchRes = null;
//                    editMessage(getInfoButtons(searchResultButtons), inlineMessId);
//                }
//                return COMEBACK;
//            case SET_TEXT:
//                deleteUpdateMess();
//                if (hasMessageText() && updateMessageText.length() < 100) {
//                    if (updateMessageText.equals("/cancel")) {
//                        deleteMessage(infoMessId);
//                        deleteMessage(inlineMessId);
//                        inlineMessId = sendMessage(getInfoButtons(searchResultButtons));
//                        waitingType = WaitingType.CHOOSE_OPTION;
//                        return COMEBACK;
//                    } else if (buttonRepository.findByNameAndLangId(updateMessageText, 1) != null || buttonRepository.findByNameAndLangId(updateMessageText, 2) != null || updateMessageText.equals("/swapLanguage") || updateMessageText.equals("/back") || updateMessageText.contains("/editName")) {
//                        deleteMessage(infoMessId);
//                        infoMessId = sendMessage(187);
//                        return COMEBACK;
//                    } else {
//                        deleteWrongMess();
//                        buttonRepository.update(updateMessageText, currentButton.getId(), currentLang.getId());
//                        deleteMessage(inlineMessId);
//                        deleteMessage(infoMessId);
//
//                        searchResultButtons =  updateButtons(searchResultButtons);
////                        searchResultButtons = buttonRepository.findAllByNameContainingAndLangIdOrderById(currentSearchValue, currentLang.getId());
//                        currentButton = buttonRepository.findByIdAndLangId(currentButton.getId(), currentLang.getId());
//                        inlineMessId = sendMessage(getInfoButtons(searchResultButtons));
//                        waitingType = WaitingType.CHOOSE_OPTION;
//                    }
//                } else {
//                    sendWrongData();
//                }
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//    private List<Button> updateButtons(List<Button> searchResultButtons) {
//        List<Button> newSearchRes = new ArrayList<>();
//        for (Button button : searchResultButtons) {
//            newSearchRes.add(buttonRepository.findByIdAndLangId(button.getId(), currentLang.getId()));
//        }
//        return newSearchRes;
//    }
//
//    private String getInfoForEdit(Button currentButton) {
//        return getText(189) + currentButton.getName() + next +
//                getText(188);
//    }
//
//    private String getInfoButtons(List<Button> searchResultButtons) {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(getText(190)).append(next).append(next);
//        for (Button button : searchResultButtons) {
//            stringBuilder.append(button.getName()).append(" \uD83D\uDD8A /editName").append(button.getId()).append(next).append(next);
//        }
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
//        deleteMessage(notFoundMess);
//        notFoundMess = sendMessage(194, chatId);
//    }
//
//
//    private Long getLongEdit(String updateMessageText) {
//        try {
//            return Long.parseLong(updateMessageText);
//        } catch (Exception e) {
//            return -1L;
//        }
//    }
//
//    private void deleteUpdateMess() {
//        deleteMessage(updateMessageId);
//    }
//
//    private void deleteWrongMess() {
//        deleteMessage(wrongMessId);
//    }
//
//    private void sendWrongData() throws TelegramApiException {
//        deleteMessage(updateMessageId);
//        deleteMessage(wrongMessId);
//        wrongMessId = sendMessage(4, chatId);
//    }
//}
