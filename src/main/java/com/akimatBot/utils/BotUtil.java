package com.akimatBot.utils;

import com.akimatBot.entity.enums.ParseMode;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.repository.repos.MessageRepository;
import com.akimatBot.services.LanguageService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.Objects;

@Slf4j
public class BotUtil {

    private DefaultAbsSender bot;
    private MessageRepository messageRepository = TelegramBotRepositoryProvider.getMessageRepository();

    public BotUtil(DefaultAbsSender bot) {
        this.bot = bot;
    }

    public BotUtil() {

    }


    public void deleteMessage(long chatId, int messageId) {
        try {
            bot.execute(new DeleteMessage(String.valueOf(chatId), messageId));
        } catch (TelegramApiException ignored) {
        }
    }

    public boolean hasContactOwner(Update update) {
        return (update.hasMessage() && update.getMessage().hasContact())
                && Objects.equals(update.getMessage().getFrom().getId(),
                update.getMessage().getContact().getUserId());
    }


    public int sendMessage(SendMessage sendMessage) throws TelegramApiException {
        try {
            return bot.execute(sendMessage).getMessageId();
        } catch (TelegramApiRequestException e) {
            if (e.getApiResponse().contains("Bad Request: can't parse entities")) {
                sendMessage.setParseMode(null);
                sendMessage.setText(sendMessage.getText() + "\nBad tags");
                return bot.execute(sendMessage).getMessageId();
            } else throw e;
        }
    }

    public int sendMessage(String text, long chatId) throws TelegramApiException {
        return sendMessage(text, chatId, ParseMode.html);
    }

    public int sendMessage(String text, long chatId, ParseMode parseMode) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        if (parseMode == ParseMode.WITHOUT) {
            sendMessage.setParseMode(null);
        } else {
            sendMessage.setParseMode(parseMode.name());
        }
        return sendMessage(sendMessage);
    }

    public int sendMessage(long messageId, long chatId) throws TelegramApiException {
        return sendMessage(messageRepository.findByIdAndLangId(messageId, LanguageService.getLanguage(chatId).getId()).getName(), chatId, null);
    }


    public int sendMessageWithKeyboard(String text, ReplyKeyboard keyboard, long chatId) throws TelegramApiException {
        return sendMessageWithKeyboard(text, keyboard, chatId, 0);
    }

    private int sendMessageWithKeyboard(String text, ReplyKeyboard keyboard, long chatId, int replyMessageId) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.html.name());
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboard);
        if (replyMessageId != 0) sendMessage.setReplyToMessageId(replyMessageId);
        return sendMessage(sendMessage);
    }

    public int sendContact(long chatId, Contact contact) throws TelegramApiException {
        SendContact sendContact = new SendContact();
        sendContact.setChatId(String.valueOf(chatId));
        sendContact.setFirstName(contact.getFirstName());
        sendContact.setLastName(contact.getLastName());
        sendContact.setPhoneNumber(contact.getPhoneNumber());

        return bot.execute(sendContact).getMessageId();
    }


}
