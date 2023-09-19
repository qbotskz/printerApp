package com.akimatBot.command.impl;

import com.akimatBot.command.Command;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

public class id003_Market extends Command {

    // it is about choosing language lmao
    @Override
    public boolean execute() throws TelegramApiException {
        deleteMessage(updateMessageId);
        sendWebApp();
        return EXIT;
    }

    private void sendWebApp() throws TelegramApiException {

//        sendMessage("<i>asdsadsad</i>");

        WebAppData webAppData = new WebAppData();
        webAppData.setData("asd");

//        AnswerWebAppQuery answerWebAppQuery = new AnswerWebAppQuery();
//        answerWebAppQuery.setQueryResult();


        WebAppInfo webAppInfo = new WebAppInfo();
//        webAppInfo.setUrl("https://109.233.108.126/durger%20test/");
        webAppInfo.setUrl("https://109.233.108.126:443/rest/#/waiter?chatId=795959817");
//        webAppInfo.setUrl("https://109.233.108.126:8000/test?chatId=" + chatId);


        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setWebApp(webAppInfo);
        inlineKeyboardButton.setText("QRestoran");

        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Arrays.asList(Arrays.asList(inlineKeyboardButton)));

        sendMessageWithKeyboard("Нажмите кнопку", replyKeyboardMarkup);
    }
}
