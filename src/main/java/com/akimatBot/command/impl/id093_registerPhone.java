package com.akimatBot.command.impl;

import com.akimatBot.command.Command;
import com.akimatBot.entity.enums.WaitingType;
import com.akimatBot.entity.standart.User;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.utils.Const;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.SQLException;

public class id093_registerPhone extends Command {
    private User user;

    @Override
    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
        switch (waitingType) {
            case START:
//                deleteMessage(updateMessageId);
                getPhone();
                waitingType = WaitingType.GET_PHONE;
                user = userRepository.findByChatId(chatId);
                return COMEBACK;
            case GET_PHONE:
                if (botUtils.hasContactOwner(update)) {
                    String phone = update.getMessage().getContact().getPhoneNumber();
                    if (phone.charAt(0) == '7') {
                        phone = phone.replaceFirst("7", "+7");
                    }
                    user.setPhone(phone);
                    user = userRepository.save(user);

                    TelegramBotRepositoryProvider.getEmployeeRepository().setUser(user.getPhone(), user.getChatId(), user);

//                    deleteMessage(updateMessageId);
                    sendMessage("Успешно сохранен");
                    return EXIT;

                } else {
                    sendMessage("ажмите на клавиатуру!");
                    return COMEBACK;

                }

        }

        return EXIT;
    }

    private int wrongData() throws TelegramApiException {
        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
    }

    private void tryAgain() throws TelegramApiException {
        wrongData();
        getPhone();
    }

    private int getPhone() throws TelegramApiException {
        return sendMessageWithKeyboard("Отправьте номер телефона!", 12);
    }
}
