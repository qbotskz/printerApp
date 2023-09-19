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
//    protected long messageId;

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
    protected static final String space = " ";
    protected static final String hyphen = " - ";
    protected String nextButton = ">>";
    protected String prevButton = "<<";
    protected String plus = "❌";

    protected int swap = 62;
    protected int snew = 63;
    protected int sdell = 64;
    protected int sback = 65;

    protected MessageRepository messageRepository = TelegramBotRepositoryProvider.getMessageRepository();
    protected KeyboardMarkUpRepository keyboardMarkUpRepository = TelegramBotRepositoryProvider.getKeyboardMarkUpRepository();
    //    protected AdminRepository adminRepository                               = TelegramBotRepositoryProvider.getAdminRepository();
    protected UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
    protected ButtonRepository buttonRepository = TelegramBotRepositoryProvider.getButtonRepository();


    protected OrderRepository orderRepository = TelegramBotRepositoryProvider.getOrderRepository();

    protected RoleRepository roleRepository = TelegramBotRepositoryProvider.getOperatorRepository();

    protected PropertiesRepo propertiesRepo = TelegramBotRepositoryProvider.getPropertiesRepo();
    protected FoodRepository foodRepository = TelegramBotRepositoryProvider.getFoodRepository();
    protected FoodCategoryRepo foodCategoryRepo = TelegramBotRepositoryProvider.getFoodCategoryRepo();
    //    protected FoodSubCategoryRepo foodSubCategoryRepo = TelegramBotRepositoryProvider.getFoodSubCategoryRepo();
    protected CourierRepository courierRepository = TelegramBotRepositoryProvider.getCourierRepository();
    protected ReviewRepository reviewRepository = TelegramBotRepositoryProvider.getReviewRepository();
    protected SuggestionComplaintRepo suggestionComplaintRepo = TelegramBotRepositoryProvider.getSuggestionComplaintRepo();
    protected CashbackRepository cashbackRepository = TelegramBotRepositoryProvider.getCashbackRepository();
    protected RestaurantBranchRepo restaurantBranchRepo = TelegramBotRepositoryProvider.getRestaurantBranchRepo();

    protected KaspiAccountsRepository kaspiAccountsRepository = TelegramBotRepositoryProvider.getKaspiAccountsRepository();

    public abstract boolean execute() throws TelegramApiException, IOException, SQLException, Exception;

    protected int sendMessageWithKeyboard(int messageId, ReplyKeyboard keyboard) throws TelegramApiException {
        return sendMessageWithKeyboard(getText(messageId), keyboard);
    }

    protected int sendMessageWithKeyboard(String text, int keyboardId) throws TelegramApiException {
        return sendMessageWithKeyboard(text, keyboardMarkUpService.select(keyboardId, chatId));
    }

    protected int sendMessageWithKeyboardWithChatId(String text, int keyboardId, long chatId) throws TelegramApiException {
        return sendMessageWithKeyboardWithChatId(text, keyboardMarkUpService.select(keyboardId, chatId), chatId);
    }

    protected int sendMessageWithKeyboard(String text, ReplyKeyboard keyboard) throws TelegramApiException {
        lastSendMessageID = sendMessageWithKeyboard(text, keyboard, chatId);
        return lastSendMessageID;
    }

    protected int sendMessageWithKeyboardWithChatId(String text, ReplyKeyboard keyboard, long chatId) throws TelegramApiException {
        lastSendMessageID = sendMessageWithKeyboard(text, keyboard, chatId);
        return lastSendMessageID;
    }

    protected void editMessageWithKeyboard(String text, int messageId, int keyboardId) throws TelegramApiException {
//        botUtils.editMessageWithKeyboard(text, chatId, messageId, keyboardMarkUpService.getInlineKeyboardMarkup(keyboardId, getLanguageId()));
    }

    protected void editMessageWithKeyboard(String text, int messageId, InlineKeyboardMarkup inlineKeyboardMarkup) throws TelegramApiException {
//        botUtils.editMessageWithKeyboard(text, chatId, messageId, inlineKeyboardMarkup);
    }

//    protected int sendMessGetMessId(int id)      throws TelegramApiException{
//        return  bot.execute(new SendMessage().setText(getText(id)).setChatId(chatId).setParseMode(String.valueOf(com.akimatBot.entity.enums.ParseMode.html))).getMessageId();
//    }
//
//    protected int sendMessGetMessId(String text)      throws TelegramApiException{
//        return  bot.execute(new SendMessage().setText(text).setChatId(chatId).setParseMode(String.valueOf(com.akimatBot.entity.enums.ParseMode.html))).getMessageId();
//    }

    protected int sendMessageWithKeyboard(String text, ReplyKeyboard keyboard, long chatId) throws TelegramApiException {
        return botUtils.sendMessageWithKeyboard(text, keyboard, chatId);
    }

    protected <T> Map<Food, Long> countByClassicalLoop(List<Food> inputList) {
        Map<Food, Long> resultMap = new TreeMap<>();
        for (Food element : inputList) {
            if (resultMap.containsKey(element)) {
//                if(element.getId() == resultMap.)
                resultMap.put(element, resultMap.get(element) + 1L);
            } else {
                resultMap.put(element, 1L);
            }
        }
        return resultMap;
    }


//    protected int     sendMessageWithPhoto(long messageId, long chatId, String photo, InlineKeyboardMarkup inlineKeyboardMarkup)   throws TelegramApiException {
//        lastSendMessageID = botUtils.sendMessage(messageId, chatId, photo, inlineKeyboardMarkup);
//        return lastSendMessageID;
//    }


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

    private void sendMessageTest(String text, SendMessage sendMessage) throws TelegramApiException {
        try {
            lastSendMessageID = bot.execute(sendMessage).getMessageId();
        } catch (TelegramApiRequestException e) {
            if (e.getApiResponse().contains("Bad Request: can't parse entities")) {
                sendMessage.setParseMode(null);
                sendMessage.setText(text + next + "Wrong number");
                lastSendMessageID = bot.execute(sendMessage).getMessageId();
            } else throw e;
        }
    }


    protected int getLanguageId() {
        return getLanguage(chatId).getId();
    }


    protected String getLinkForUser(long chatId, String userName) {
        return String.format("<a href = \"tg://user?id=%s\">%s</a>", chatId, userName);
    }

    protected String getText(int messageIdFromDb) {
        return messageRepository.findByIdAndLangId(messageIdFromDb, LanguageService.getLanguage(chatId).getId()).getName();
    }

    protected String getText(int messageIdFromDb, long chatId) {
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

    protected boolean hasCallbackQuery() {
        return update.hasCallbackQuery();
    }

    protected CallbackQuery getCallbackQuery() {
        return update.getCallbackQuery();
    }

    protected boolean hasMessageText() {
        return update.hasMessage() && update.getMessage().hasText();
    }

    protected boolean hasPhoto() {
        return update.hasMessage() && update.getMessage().hasPhoto();
    }

    protected boolean hasDocument() {
        return update.hasMessage() && update.getMessage().hasDocument();
    }

    protected boolean hasAudio() {
        return update.hasMessage() && update.getMessage().getAudio() != null;
    }

    protected boolean hasVideo() {
        return update.hasMessage() && update.getMessage().getVideo() != null;
    }

    protected String getBolt(String s) {
        return "<b>" + s + "</b>";
    }

    protected String getButtonText(int id) {
        return buttonRepository.findByIdAndLangId(id, getLanguage(chatId).getId()).getName();
    }

    protected int onlyNumbers(String strNum) {
        return Integer.parseInt(strNum.replaceAll("[^0-9]", ""));
    }


    protected void editMessageWithPhotoAndKeyboard(String text, InlineKeyboardMarkup inlineKeyboardMarkup, long chatId, int messageId) throws TelegramApiException {
        EditMessageCaption editMessageCaption = new EditMessageCaption();
        editMessageCaption.setMessageId(messageId);
        editMessageCaption.setCaption(text);
        editMessageCaption.setChatId(String.valueOf(chatId));
        editMessageCaption.setReplyMarkup(inlineKeyboardMarkup);
        bot.execute(editMessageCaption);
    }


    protected String getColor(Date startDate) {
        String green = "\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2";
        String yellow = "\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1";
        String red = "\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34";
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();

        calendar.setTime(startDate);
        calendar.add(Calendar.HOUR_OF_DAY, 24);

        Date dateDeadline = calendar.getTime();

        if (currentDate.before(dateDeadline)) {
            return green;
        }
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        dateDeadline = calendar.getTime();

        if (currentDate.before(dateDeadline)) {
            return yellow;
        }

        return red;
    }


    protected String getPhoneNumber(String contact) {
//        77006804045
//       +77006804045
//        87006804045
        if (contact.charAt(0) == '8') {
            contact = contact.replaceFirst("8", "+7");
        } else if (contact.charAt(0) == '7') {
            contact = contact.replaceFirst("7", "+7");
        }
        return contact;
    }

    protected void deleteAll(int updMes) {
        deleteMessage(updateMessageId);
        deleteMessage(updMes);
    }


    public Language getLanguage(long chatId) {
        if (chatId == 0) return Language.ru;
        return LanguageService.getLanguage(chatId);
    }

    public Language getLanguage() {
        if (chatId == 0) return Language.ru;
        return LanguageService.getLanguage(chatId);
    }


    protected long getLong(String updateMessageText) {
        try {
            return Long.parseLong(updateMessageText);
        } catch (Exception e) {
            return -1;
        }
    }

    protected boolean isLong(String mess) {
        try {
            Long.parseLong(mess);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected int getInt(String updateMessageText) {
        try {
            return Integer.parseInt(updateMessageText);
        } catch (Exception e) {
            return -1;
        }
    }


    public static String formatPhone(String phoneNumber) {

        if (phoneNumber != null && !phoneNumber.equals("")) {
            if (phoneNumber.startsWith("8")) {
                phoneNumber = phoneNumber.replaceFirst("8", "+7");
            }
            if (phoneNumber.startsWith("7")) {
                phoneNumber = phoneNumber.replaceFirst("7", "+7");
            }
        }
        return phoneNumber;
    }

    protected boolean isIIN(String updateMessageText) {
        try {
            Long.parseLong(updateMessageText);
            return updateMessageText.length() == 12;
        } catch (Exception e) {
            return false;
        }
    }


    protected int getLangId() {
        return getLanguage(chatId).getId();
    }

    public void editMessageWithKeyboard(String text, long chatId, int messageId, InlineKeyboardMarkup replyKeyboard) throws TelegramApiException {
        EditMessageText new_message = new EditMessageText();
        new_message.setChatId(String.valueOf(chatId));
        new_message.setMessageId(messageId);
        new_message.setText(text);
        new_message.setReplyMarkup(replyKeyboard);
        new_message.setParseMode("html");
        try {
            bot.execute(new_message);
        } catch (TelegramApiException e) {
            if (e.toString().contains("Bad Request: can't parse entities")) {
                new_message.setParseMode(null);
                bot.execute(new_message);
            } else e.printStackTrace();
        }

    }

    public void editMessage(String text, long chatId, int messageId) throws TelegramApiException {
        EditMessageText new_message = new EditMessageText();
        new_message.setChatId(String.valueOf(chatId));
        new_message.setMessageId(messageId);
        new_message.setText(text);
        try {
            bot.execute(new_message);
        } catch (TelegramApiException e) {
            if (e.toString().contains("Bad Request: can't parse entities")) {
                new_message.setParseMode(null);
                bot.execute(new_message);
            }
            e.printStackTrace();
        }
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

    public int sendMedia(String fileId, FileType fileType) throws TelegramApiException {
        return sendMedia(fileId, fileType, chatId);
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

    public int sendDocument(String fileId, String mess, long chatId) throws TelegramApiException {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        sendDocument.setDocument(new InputFile(fileId));
        sendDocument.setCaption(mess);
        sendDocument.setParseMode("html");


        try {
            return bot.execute(sendDocument).getMessageId();
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.debug("Can't send document", e);
        }
        return 0;
    }

    public void editMessageWithKeyboard(String text, InlineKeyboardMarkup inlineKeyboardMarkup, int messageId) throws TelegramApiException {
        EditMessageText new_message = new EditMessageText();
        new_message.setChatId(String.valueOf(chatId));
        new_message.setMessageId(messageId);
        new_message.setText(text);
        new_message.setReplyMarkup(inlineKeyboardMarkup);
        new_message.setParseMode("html");
        try {
            bot.execute(new_message);
        } catch (TelegramApiException e) {
            if (e.toString().contains("Bad Request: can't parse entities")) {
                new_message.setParseMode(null);
                bot.execute(new_message);
            }
            e.printStackTrace();
        }
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


    protected boolean isButtonExist(String name) {

        return buttonRepository.countByNameAndLangId(name, getLangId()) > 0;
    }

    protected String getBold(String text) {
        return "<b>" + text + "</b>";
    }


    protected int toDeleteMessage(int messageDeleteId) {
        SetDeleteMessages.addKeyboard(chatId, messageDeleteId);
        return messageDeleteId;
    }

    protected int toDeleteKeyboard(int messageDeleteId) {
        SetDeleteMessages.addKeyboard(chatId, messageDeleteId);
        return messageDeleteId;
    }


    protected void sendAnswerCallbackQuery(String text, boolean showAlert) throws TelegramApiException {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(showAlert);
        answerCallbackQuery.setCacheTime(0);
        answerCallbackQuery.setText(text);
        bot.execute(answerCallbackQuery);
    }


    protected String uploadFile(String fileId) {
        Objects.requireNonNull(fileId);
        GetFile getFile = new GetFile();
        getFile.setFileId(fileId);
        try {
            File file = bot.execute(getFile);
            return file.getFilePath();
        } catch (TelegramApiException e) {
            throw new IllegalStateException(e);
        }
    }


    protected boolean isRegistered() {
        return userRepository.existsByChatIdAndPhoneNotNull(chatId);

    }

    protected boolean isRegistered(long chatId) {
        return userRepository.existsByChatIdAndPhoneNotNull(chatId);

    }

    protected void deleteUpdateMess() {
        deleteMessage(updateMessageId);
    }

}
