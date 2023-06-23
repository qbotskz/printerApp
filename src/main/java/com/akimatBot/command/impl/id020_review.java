package com.akimatBot.command.impl;

import com.akimatBot.command.Command;
import com.akimatBot.entity.custom.Review;
import com.akimatBot.entity.enums.StarGrade;
import com.akimatBot.entity.enums.WaitingType;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.repository.repos.ReviewRepository;
import com.akimatBot.utils.ButtonsLeaf;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class id020_review extends Command {
    private final ReviewRepository reviewRepository = TelegramBotRepositoryProvider.getReviewRepository();
    private Review review;
    private ButtonsLeaf buttonsLeaf;
    @Override
    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
        switch (waitingType){
            case START:
                if(isButton(118)){
                    deleteMessage(updateMessageId);
                    sendMessageWithKeyboard("Главное меню",2);
                    return EXIT;
                }
                getGrade();
                waitingType = WaitingType.GRADING;
                review = new Review();
                review.setUser(userRepository.findByChatId(chatId));
                return COMEBACK;
            case GRADING:
                if(hasCallbackQuery()){

                    switch (getCallbackQuery().getData()) {
                        case "0":
                            review.setReviewGrade(StarGrade.ONE_STAR);
                            break;
                        case "1":
                            review.setReviewGrade(StarGrade.TWO_STAR);
                            break;
                        case "2":
                            review.setReviewGrade(StarGrade.THREE_STAR);
                            break;
                        case "3":
                            review.setReviewGrade(StarGrade.FOUR_STAR);
                            break;
                        case "4":
                            review.setReviewGrade(StarGrade.FIVE_STAR);
                            break;
                    }
                    getReviewText();
                    waitingType = WaitingType.SET_TEXT;
                    return COMEBACK;
                }
            case SET_TEXT:
                if(hasCallbackQuery()){
                    review.setReviewText(null);
                }
                if(update.hasMessage() && update.getMessage().hasText()){
                    review.setReviewText(update.getMessage().getText());
                }
                review.setUploadedDate(new Date());
                reviewRepository.save(review);
                sendMessage(83);
                return EXIT;
        }
        return false;
    }
    private void getReviewText() throws TelegramApiException{
         editMessageWithKeyboard(getText(82),updateMessageId ,23);
    }
    private void getGrade() throws TelegramApiException{
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for(int i = 0;i<5;i++){
            list.add("⭐");
            list2.add(String.valueOf(i));
        }
        buttonsLeaf = new ButtonsLeaf(list, list2, 5,5);
        sendMessageWithKeyboard(getText(81) ,buttonsLeaf.getListButtonInline());
    }
}
