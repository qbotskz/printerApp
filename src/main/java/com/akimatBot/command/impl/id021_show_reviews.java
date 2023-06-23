//package com.akimatBot.command.impl;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Review;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.ReviewRepository;
//import com.akimatBot.repository.repos.UserRepository;
//import com.akimatBot.utils.ButtonsLeaf;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class id021_show_reviews extends Command {
//    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
//    private final ReviewRepository reviewRepository = TelegramBotRepositoryProvider.getReviewRepository();
//    private Review review;
//    private List<Review> reviews;
//    private ButtonsLeaf buttonsLeaf;
//    private int msgToEdit;
//    private boolean isLast = false;
//    int start = 1;
//    int end = 5;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                reviews = reviewRepository.findAllByOrderByUploadedDateDesc();
//
//                for(int i = start;i<=end;i++){
//                    review = reviews.get(i-1);
//                    if(i == end){
//                        thisIsEnd();
//                        isLast = true;
//                        waitingType = WaitingType.CONFIRM;
//                        return COMEBACK;
//                    }
//                    sendMessage(review.getUser().getFullName()+" ("+review.getUploadedDate()+")\n"+
//                            review.getReviewGrade().getStar()+"\n"+review.getReviewText());
//                }
//            case CONFIRM:
//                if(hasCallbackQuery()){
//                    if(getCallbackQuery().getData().equals("0")){
//                        start+=5;
//                        end+=5;
//                        if(end>reviews.size()){
//                            end = reviews.size()+1;
//                        }
//                        if(isLast) {
//                            editMessage(review.getUser().getFullName()+" ("+review.getUploadedDate()+")\n"+
//                                    review.getReviewGrade().getStar()+"\n"+review.getReviewText(), updateMessageId);
//                        }
//
//                        for(int i = start;i<=end;i++){
//                            review = reviews.get(i-1);
//                            if(i == end){
//                                if(end == reviews.size()+1){
//                                    return EXIT;
//                                }
//                                thisIsEnd();
//                                isLast = true;
//                                return COMEBACK;
//                            }
//                            isLast = false;
//                            sendMessage(review.getUser().getFullName()+" ("+review.getUploadedDate()+")\n"+
//                                    review.getReviewGrade().getStar()+"\n"+review.getReviewText());
//
//                        }
//                    }
//                }
//        }
//
//        return false;
//    }
//    public void thisIsEnd() throws TelegramApiException{
//        List<String> list = new ArrayList<>();
//        List<String> list2 = new ArrayList<>();
//        list.add("Еще");
//        list2.add("0");
//        buttonsLeaf = new ButtonsLeaf(list, list2);
//        sendMessageWithKeyboard(review.getUser().getFullName()+" ("+review.getUploadedDate()+")\n"+
//                review.getReviewGrade().getStar()+"\n"+review.getReviewText(), buttonsLeaf.getListButtonInline());
//    }
//}
