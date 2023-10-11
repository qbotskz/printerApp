package com.akimatBot.command;

import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.enums.FileType;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.enums.WaitingType;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.repository.repos.*;
import com.akimatBot.services.KeyboardMarkUpService;
import com.akimatBot.services.LanguageService;
import com.akimatBot.utils.BotUtil;
import com.akimatBot.utils.Const;
import com.akimatBot.utils.SetDeleteMessages;
import com.akimatBot.utils.UpdateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@NoArgsConstructor
public abstract class Command {

    @Getter
    @Setter
    protected long id;
    @Getter
    @Setter
    protected long messageId;

    protected static BotUtil botUtils;
    protected KeyboardMarkUpService keyboardMarkUpService = new KeyboardMarkUpService();
    protected Update update;
    protected DefaultAbsSender bot;
    protected Long chatId;
    protected Message updateMessage;
    protected String updateMessageText;
    protected int updateMessageId;
    //    protected              int                  upMessId;
    protected String editableTextOfMessage;
    protected String updateMessagePhoto;
    protected String updateMessagePhone;
    protected String markChange;
    protected int lastSendMessageID;
    protected final static boolean EXIT = true;
    protected final static boolean COMEBACK = false;
    protected WaitingType waitingType = WaitingType.START;
    protected static final String next = "\n";
    protected String plus = "❌";

    protected MessageRepository messageRepository = TelegramBotRepositoryProvider.getMessageRepository();
    protected UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
    protected ButtonRepository buttonRepository = TelegramBotRepositoryProvider.getButtonRepository();

    public abstract boolean execute() throws TelegramApiException, IOException, SQLException, Exception;

    protected int sendMessageWithKeyboard(String text, int keyboardId) throws TelegramApiException {
        return sendMessageWithKeyboard(text, keyboardMarkUpService.select(keyboardId, chatId));
    }

    protected int sendMessageWithKeyboard(String text, ReplyKeyboard keyboard) throws TelegramApiException {
        lastSendMessageID = sendMessageWithKeyboard(text, keyboard, chatId);
        return lastSendMessageID;
    }

    protected int sendMessageWithKeyboard(String text, ReplyKeyboard keyboard, long chatId) throws TelegramApiException {
        return botUtils.sendMessageWithKeyboard(text, keyboard, chatId);
    }

    public void clear() {
        update = null;
        bot = null;
    }

    protected void deleteMessage(int messageId) {
        if (messageId > 0)
            deleteMessage(chatId, messageId);
    }

    protected void deleteMessage(long chatId, int messageId) {
        botUtils.deleteMessage(chatId, messageId);
    }

    protected String getText(int messageIdFromDb) {
        return messageRepository.findByIdAndLangId(messageIdFromDb, LanguageService.getLanguage(chatId).getId()).getName();
    }

    public boolean isInitNormal(Update update, DefaultAbsSender bot) {
        if (botUtils == null) botUtils = new BotUtil(bot);
        this.update = update;
        this.bot = bot;
        chatId = UpdateUtil.getChatId(update);
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            updateMessage = callbackQuery.getMessage();
            updateMessageText = callbackQuery.getData();
            updateMessageId = updateMessage.getMessageId();
            editableTextOfMessage = callbackQuery.getMessage().getText();
        } else if (update.hasMessage()) {
            updateMessage = update.getMessage();
            updateMessageId = updateMessage.getMessageId();
            if (updateMessage.hasText()) updateMessageText = updateMessage.getText();
            if (updateMessage.hasPhoto()) {
                int size = update.getMessage().getPhoto().size();
                updateMessagePhoto = update.getMessage().getPhoto().get(size - 1).getFileId();
            } else {
                updateMessagePhoto = null;
            }
        }
        if (hasContact()) updateMessagePhone = update.getMessage().getContact().getPhoneNumber();
        if (markChange == null) markChange = getText(Const.EDIT_BUTTON_ICON);
        return false;
    }


    protected boolean hasContact() {
        return update.hasMessage() && update.getMessage().getContact() != null;
    }

    //    protected boolean isButton(int buttonId) { return updateMessageText.equals(buttonRepository.findByIdAndLangId(buttonId, getLanguage(chatId).getId()).getName()); }
    protected boolean isButton(int buttonId) {
        if (updateMessageText.equals(buttonRepository.findByIdAndLangId(buttonId, Language.getById(1).getId()).getName())) {
            return updateMessageText.equals(buttonRepository.findByIdAndLangId(buttonId, Language.getById(1).getId()).getName());
        }
        return updateMessageText.equals(buttonRepository.findByIdAndLangId(buttonId, Language.getById(2).getId()).getName());

    }

    public Language getLanguage(long chatId) {
        if (chatId == 0) return Language.ru;
        return LanguageService.getLanguage(chatId);
    }

    public Language getLanguage() {
        if (chatId == 0) return Language.ru;
        return LanguageService.getLanguage(chatId);
    }

    protected int getLangId() {
        return getLanguage(chatId).getId();
    }

    public int sendMedia(String fileId, FileType fileType, long chatId) throws TelegramApiException {
        if (fileId != null && fileType != null) {
            switch (fileType) {
                case photo:
                    return sendPhoto(fileId, chatId);
                case video:
                    return sendVideo(fileId, chatId);
                case document:
                    return sendDocument(fileId, chatId);
                default:
                    break;
            }
        }

        return 0;
    }
    public int sendPhoto(String photo, long chatId) throws TelegramApiException {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(chatId));
        sendPhoto.setPhoto(new InputFile(photo));

        try {
            return bot.execute(sendPhoto).getMessageId();
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.debug("Can't send photo", e);
        }
        return 0;
    }

    public int sendVideo(String video, long chatId) throws TelegramApiException {

        SendVideo sendVideo = new SendVideo();
        sendVideo.setVideo(new InputFile(video));
        sendVideo.setChatId(String.valueOf(chatId));


        try {
            return bot.execute(sendVideo).getMessageId();
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.debug("Can't send video", e);
        }
        return 0;
    }

    public int sendDocument(String fileId, long chatId) throws TelegramApiException {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        sendDocument.setDocument(new InputFile(fileId));


        try {
            return bot.execute(sendDocument).getMessageId();
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.debug("Can't send document", e);
        }
        return 0;
    }

    protected int sendMessage(long messageId) throws TelegramApiException {
        return sendMessage(messageId, chatId);
    }

    protected int sendMessage(long messageId, long chatId) throws TelegramApiException {
        return sendMessage(messageId, chatId, null);
    }

    protected int sendMessage(long messageId, long chatId, Contact contact) throws TelegramApiException {
        return sendMessage(messageId, chatId, contact, null);
    }

    protected int sendMessage(long messageId, long chatId, Contact contact, String photo) throws TelegramApiException {
//        lastSentMessageID =
        return 0;
//        botUtils.sendMessage(messageId, chatId, contact, photo);
//        return lastSentMessageID;
    }

    protected int sendMessage(String text) throws TelegramApiException {
        return sendMessage(text, chatId);
    }

    protected int sendMessage(String text, long chatId) throws TelegramApiException {
        return sendMessage(text, chatId, null);
    }

    protected int sendMessage(String text, long chatId, Contact contact) throws TelegramApiException {
        int mes = botUtils.sendMessage(text, chatId);
        if (contact != null) {
            botUtils.sendContact(chatId, contact);
        }
        return mes;
    }

    protected int toDeleteKeyboard(int messageDeleteId) {
        SetDeleteMessages.addKeyboard(chatId, messageDeleteId);
        return messageDeleteId;
    }
}
