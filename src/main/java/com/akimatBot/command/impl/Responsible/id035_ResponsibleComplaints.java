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
//public class id035_ResponsibleComplaints extends Command {
//
//
//    private SuggestionComplaint complaint;
//    private List<SuggestionComplaint> complaints;
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
//                if(isButton(143)){
//                    complaints = suggestionComplaintRepo.findAllByAnsweredFalseAndAppealType(AppealType.COMPLAINT);
//                    if(complaints.isEmpty()){
//                        sendMessage(getText(120));
//                        return EXIT;
//                    }
//                }
//                else if(isButton(144)){
//                    complaints = suggestionComplaintRepo.findAllByAnsweredTrueAndAppealType(AppealType.COMPLAINT);
//                    if(complaints.isEmpty()){
//                        sendMessage(getText(121));
//                        return EXIT;
//                    }
//                }
//                else{
//                    return EXIT;
//                }
//                showComplaints();
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
//                    complaint = complaints.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//                    showComplaint();
//                    waitingType = WaitingType.SHOW_SUGGESTION;
//                    return COMEBACK;
//                }
//            case SHOW_SUGGESTION:
//                deleteMessage(updateMessageId);
//
//                if(hasCallbackQuery()){
//                    if(isButton(67)){
//                        showComplaints();
//                        waitingType = WaitingType.CHOOSE_SUGGESTION;
//                        return COMEBACK;
//                    }
//                }
//        }
//        return false;
//    }
//    private int showComplaint() throws TelegramApiException{
//        if(complaint.getPhotoUrl()==null && complaint.getVideoUrl()==null){
//            if(complaint.isAnswered()){
//                return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(123), complaint.getId(), complaint.getUser().getFullName(), complaint.getUser().getPhone(), complaint.getResponsible().getFullName(), complaint.getText() , complaint.getResponse()),54));
//            }
//            return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(124), complaint.getId(), complaint.getUser().getFullName(), complaint.getUser().getPhone(), complaint.getText()), 56));
//
//        }
//       else{
//
//           if(complaint.getVideoUrl()==null){
//
//               if(complaint.isAnswered()) {
//                   return toDeleteKeyboard(sendMessageWithPhotoAndKeyboard(String.format(getText(123), complaint.getId(), complaint.getUser().getFullName(), complaint.getUser().getPhone(), complaint.getResponsible().getFullName(), complaint.getText() , complaint.getResponse()),54, complaint.getPhotoUrl(),chatId));
//               }
//               return toDeleteKeyboard(sendMessageWithPhotoAndKeyboard(String.format(getText(124), complaint.getId(), complaint.getUser().getFullName(), complaint.getUser().getPhone(), complaint.getText()), 56, complaint.getPhotoUrl(), chatId));
//
//
//           }
//           else{
//               if(complaint.isAnswered()){
//                   return toDeleteKeyboard(sendMessageWithVideoAndKeyboard(String.format(getText(123), complaint.getId(), complaint.getUser().getFullName(), complaint.getUser().getPhone(), complaint.getResponsible().getFullName(), complaint.getText() , complaint.getResponse()),54, complaint.getVideoUrl(),chatId));
//               }
//               return toDeleteKeyboard(sendMessageWithPhotoAndKeyboard(String.format(getText(124), complaint.getId(), complaint.getUser().getFullName(), complaint.getUser().getPhone(), complaint.getText()), 56, complaint.getVideoUrl(), chatId));
//
//           }
//        }
//    }
//    private int showComplaints() throws TelegramApiException {
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        complaints.forEach((e) -> {
//            finalList.add(e.getId()+"|"+e.getUploadedDate().toString());
//            finalList2.add(String.valueOf(e.getId()));
//        });
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10);
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(119), buttonsLeaf.getListButtonInline()));
//    }
//
//}
