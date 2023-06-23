package com.akimatBot.command.impl;

import com.akimatBot.command.Command;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.enums.WaitingType;
import com.akimatBot.entity.standart.Role;
import com.akimatBot.entity.standart.User;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.repository.repos.UserRepository;
import com.akimatBot.services.LanguageService;
import com.akimatBot.utils.Const;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class id001_ShowInfo extends Command {
    private User user;
    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
    private final List<Role> roles = new ArrayList<>(Collections.singletonList(new Role(2)));


    // This command is executed when /start was clicked
    @Override
    public boolean execute() throws TelegramApiException {

        sendWebApp();

//        if (!isRegistered()) {
//            user = new User();
//            user.setChatId(chatId);
//            user.setFullName(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName());
//            user.setUserName(update.getMessage().getFrom().getUserName());
//            user.setRoles(roles);
//            user.setCashback(0);
//            user.setLanguage(Language.ru);
//            user = userRepository.save(user);
//
//            switch (waitingType){
//                case START:
//
//                    sendMessageWithKeyboard(getText(220), 93);
//                    waitingType = WaitingType.CHOOSE_OPTION;
//                    return COMEBACK;
//                case CHOOSE_OPTION:
//                    if(hasCallbackQuery()){
////                        if(isButton(228)){
////                            user.setCity(City.ALMATY);
////                        }
////                        else if(isButton(229)){
////                            user.setCity(City.NUR_SULTAN);
////                        }
//                        break;
//                    }
//
//            }
////            switch (waitingType) {
////                case START:
////                    user = new User();
////                    user.setChatId(chatId);
////                    user.setFullName(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName());
////                    user.setUserName(update.getMessage().getFrom().getUserName());
////                    user.setRoles(roles);
////                    shortInfo();
////                    getName();
////                    //getPhone();
////                    waitingType = WaitingType.CONFIRM_NAME;
////                    return COMEBACK;
////                case CONFIRM_NAME:
////                    if(update.hasMessage()&&update.getMessage().hasText()){
////                        user.setFullName(update.getMessage().getText());
////                        getPhone();
////                        waitingType = WaitingType.CONFIRM_PHONE;
////                        return COMEBACK;
////                    }
////                case CONFIRM_PHONE:
////                    if (botUtils.hasContactOwner(update)) {
////                        String phone = update.getMessage().getContact().getPhoneNumber();
////                        if (phone.startsWith("7")){
////                            phone = "+"+phone;
////                        }
////                        else if(phone.startsWith("8")){
////                            phone = phone.replaceFirst("8", "+7");
////                        }
////                        user.setPhone(phone);
////
////                        getGender();
////                        waitingType = WaitingType.CHOOSE_GENDER;
////                    } else {
////                        getPhone();
////                    }
////                    return COMEBACK;
////                case CHOOSE_GENDER:
////
////                    if (isButton(103)) {
////                        user.setGender(Gender.MAN);
////                    } else {
////                        user.setGender(Gender.WOMAN);
////                    }
////                    userRepository.save(user);
////                    sendMessageWithAddition();
////                    return EXIT;
////            }
//
//        }
        deleteMessage(updateMessageId);
        LanguageService.setLanguage(chatId, Language.ru);
//        sendMessageWithKeyboard("Главное меню", 2);
//        sendMessageWithAddition();
        //if(!isButton(1)) sendMessageWithAddition();

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
        webAppInfo.setUrl("https://109.233.108.126/qtest/#/waiter");
//        webAppInfo.setUrl("https://109.233.108.126:8000/test?chatId=" + chatId);


        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setWebApp(webAppInfo);
        inlineKeyboardButton.setText("Restoran");
//        inlineKeyboardButton.setText("Durger king");

        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Arrays.asList(Arrays.asList(inlineKeyboardButton)));

        sendMessageWithKeyboard("Нажмите кнопку", replyKeyboardMarkup);
    }


    private int getGender() throws TelegramApiException {
        return toDeleteKeyboard(sendMessageWithKeyboard(getText(Const.GET_GENDER), 40));
    }

    private int shortInfo() throws TelegramApiException {
        return sendMessage(getText(100));
    }

    private int getPhone() throws TelegramApiException {
        return sendMessageWithKeyboard(getText(Const.GET_PHONE), 12);
    }

    private int getName() throws TelegramApiException {
        return botUtils.sendMessage(Const.GET_NAME, chatId);
    }

}
