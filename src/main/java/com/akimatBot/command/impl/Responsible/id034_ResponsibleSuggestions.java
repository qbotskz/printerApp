//package com.akimatBot.command.impl.Responsible;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.SuggestionComplaint;
//import com.akimatBot.entity.enums.AppealType;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.utils.ButtonsLeaf;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class id034_ResponsibleSuggestions extends Command {
//    private SuggestionComplaint suggestion;
//    private List<SuggestionComplaint> suggestions;
//    private List<String> finalList;
//    private List<String> finalList2;
//    private ButtonsLeaf buttonsLeaf;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                deleteMessage(updateMessageId);
//
//                if(isButton(137)){
//                    suggestions = suggestionComplaintRepo.findAllByAnsweredFalseAndAppealType(AppealType.SUGGESTION);
//                    if(suggestions.isEmpty()){
//                        sendMessage(getText(116));
//                        return EXIT;
//                    }
//                }
//                else if(isButton(138)){
//                    suggestions = suggestionComplaintRepo.findAllByAnsweredTrueAndAppealType(AppealType.SUGGESTION);
//                    if(suggestions.isEmpty()){
//                        sendMessage(getText(117));
//                        return EXIT;
//                    }
//                }
//                else{
//                    return EXIT;
//                }
//                showSuggestions();
//                waitingType = WaitingType.CHOOSE_SUGGESTION;
//                return COMEBACK;
//            case CHOOSE_SUGGESTION:
//                deleteMessage(updateMessageId);
//
//                if(hasCallbackQuery()){
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        editMessageWithKeyboard(getText(113), updateMessageId, buttonsLeaf.getListButtonInline());
//                    }
//                    deleteMessage(updateMessageId);
//                    suggestion = suggestions.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//                    showSuggestion();
//                    waitingType = WaitingType.SHOW_SUGGESTION;
//                    return COMEBACK;
//                }
//            case SHOW_SUGGESTION:
//                deleteMessage(updateMessageId);
//
//                if(hasCallbackQuery()){
//                    if(isButton(67)){
//                        showSuggestions();
//                        waitingType = WaitingType.CHOOSE_SUGGESTION;
//                        return COMEBACK;
//                    }
//                }
//        }
//        return false;
//    }
//    private int showSuggestion() throws TelegramApiException{
////        if(suggestion.isAnswered()){
////            return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(114), suggestion.getId(), suggestion.getUser().getFullName(), suggestion.getUser().getPhone(), suggestion.getResponsible().getFullName(),suggestion.getText() ,suggestion.getResponse()),54));
////        }
////
////        return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(115), suggestion.getId(), suggestion.getUser().getFullName(), suggestion.getUser().getPhone(), suggestion.getText()), 51));
//        if(suggestion.getPhotoUrl()==null && suggestion.getVideoUrl()==null){
//            if(suggestion.isAnswered()){
//                return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(114), suggestion.getId(), suggestion.getUser().getFullName(), suggestion.getUser().getPhone(), suggestion.getResponsible().getFullName(), suggestion.getText() , suggestion.getResponse()),54));
//            }
//            return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(115), suggestion.getId(), suggestion.getUser().getFullName(), suggestion.getUser().getPhone(), suggestion.getText()), 51));
//
//        }
//        else{
//
//            if(suggestion.getVideoUrl()==null){
//
//                if(suggestion.isAnswered()) {
//                    return toDeleteKeyboard(sendMessageWithPhotoAndKeyboard(String.format(getText(114), suggestion.getId(), suggestion.getUser().getFullName(), suggestion.getUser().getPhone(), suggestion.getResponsible().getFullName(), suggestion.getText() , suggestion.getResponse()),54, suggestion.getPhotoUrl(),chatId));
//                }
//                return toDeleteKeyboard(sendMessageWithPhotoAndKeyboard(String.format(getText(115), suggestion.getId(), suggestion.getUser().getFullName(), suggestion.getUser().getPhone(), suggestion.getText()), 51, suggestion.getPhotoUrl(), chatId));
//
//
//            }
//            else{
//                if(suggestion.isAnswered()){
//                    return toDeleteKeyboard(sendMessageWithVideoAndKeyboard(String.format(getText(114), suggestion.getId(), suggestion.getUser().getFullName(), suggestion.getUser().getPhone(), suggestion.getResponsible().getFullName(), suggestion.getText() , suggestion.getResponse()),54, suggestion.getVideoUrl(),chatId));
//                }
//                return toDeleteKeyboard(sendMessageWithPhotoAndKeyboard(String.format(getText(115), suggestion.getId(), suggestion.getUser().getFullName(), suggestion.getUser().getPhone(), suggestion.getText()), 51, suggestion.getVideoUrl(), chatId));
//
//            }
//        }
//    }
//    private int showSuggestions() throws TelegramApiException {
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        suggestions.forEach((e) -> {
//            finalList.add(e.getId()+"|"+e.getUploadedDate().toString());
//            finalList2.add(String.valueOf(e.getId()));
//        });
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10);
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(113), buttonsLeaf.getListButtonInline()));
//    }
//
//}
