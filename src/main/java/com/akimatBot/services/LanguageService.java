package com.akimatBot.services;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.standart.User;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.repository.repos.UserRepository;
import org.springframework.stereotype.Component;


@Component
public class LanguageService {

    private static UserRepository usersRepo = TelegramBotRepositoryProvider.getUserRepository();

    public static Language getLanguage(long chatId) {

        Language language = usersRepo.getLanguageByChatId(chatId);
        if (language != null) {
            return language;
        } else {
            return Language.ru;
        }
    }

    public static void setLanguage(long chatId, Language language) {
        User user = usersRepo.findByChatId(chatId);
        if (user == null) {
            user = new User();
            user.setChatId(chatId);
        }
        user.setLanguage(language);
        usersRepo.save(user);

    }
}
