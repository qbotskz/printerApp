package com.akimatBot.command.impl;

import com.akimatBot.command.Command;
import com.akimatBot.entity.enums.WaitingType;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.SQLException;

public class id009_about_us extends Command {
    @Override
    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
        switch (waitingType) {
            case START:
                sendMessageWithKeyboard(getText(30), 24);
                waitingType = WaitingType.ABOUT_US;
                return COMEBACK;
            case ABOUT_US:
                if (hasCallbackQuery()) {
                    if (isButton(9)) {
                        sendMessage(getText(31));
                        return EXIT;
                    }
                } else {
                    sendMessageWithKeyboard(getText(30), 24);
                }
                return COMEBACK;
        }
        return EXIT;
    }
}
