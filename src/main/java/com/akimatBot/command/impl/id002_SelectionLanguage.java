package com.akimatBot.command.impl;

import com.akimatBot.command.Command;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.services.LanguageService;
import com.akimatBot.utils.Const;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class id002_SelectionLanguage extends Command {

    // it is about choosing language lmao
    @Override
    public boolean  execute() throws TelegramApiException {
        deleteMessage(updateMessageId);
        chosenLanguage();
        toDeleteMessage(sendMessage(Const.WELCOME_TEXT_WHEN_START));
        return EXIT;
    }

    private void    chosenLanguage() {
        if (isButton(Const.RU_LANGUAGE)) LanguageService.setLanguage(chatId, Language.ru);
        if (isButton(Const.KZ_LANGUAGE)) LanguageService.setLanguage(chatId, Language.kz);
    }
}
