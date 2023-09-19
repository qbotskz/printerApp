package com.akimatBot.services;

import com.akimatBot.command.Command;
import com.akimatBot.command.CommandFactory;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.standart.Button;
import com.akimatBot.exceptions.CommandNotFoundException;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.repository.repos.ButtonRepository;
import com.akimatBot.utils.Const;
import com.akimatBot.utils.UpdateUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class CommandService {

    private long chatId;
    private ButtonRepository buttonRepo = TelegramBotRepositoryProvider.getButtonRepository();

    public Command getCommand(Update update) throws CommandNotFoundException {
        chatId = UpdateUtil.getChatId(update);
        Message updateMessage = update.getMessage();
        String inputtedText;
        if (update.hasCallbackQuery()) {
            inputtedText = update.getCallbackQuery().getData().split(Const.SPLIT)[0];
            updateMessage = update.getCallbackQuery().getMessage();
            try {
                if (inputtedText != null && inputtedText.substring(0, 6).equals(Const.ID_MARK)) {
                    try {
                        return getCommandById(Integer.parseInt(inputtedText.split(Const.SPLIT)[0].replaceAll(Const.ID_MARK, "")));
                    } catch (Exception e) {
                        inputtedText = updateMessage.getText();
                    }
                }
            } catch (Exception e) {
            }
        } else {
            try {
                inputtedText = updateMessage.getText();
            } catch (Exception e) {
                throw new CommandNotFoundException(new Exception("No data is available"));
            }
        }
        Button button = buttonRepo.findByNameAndLangId(inputtedText, getLanguage().getId());
        return getCommand(button);
    }

    protected Language getLanguage() {
        if (chatId == 0) return Language.ru;
        return LanguageService.getLanguage(chatId);
    }

    private Command getCommand(Button button) throws CommandNotFoundException {
        if (button == null || button.getCommandId() == null || button.getCommandId() == 0)
            throw new CommandNotFoundException(new Exception("No data is available"));
        Command command = CommandFactory.getCommand(button.getCommandId());
        command.setId(button.getCommandId());
        command.setMessageId(button.getMessageId() == null ? 0 : button.getMessageId());
        return command;
    }

    private Command getCommandById(int id) {
        return CommandFactory.getCommand(id);
    }
}
