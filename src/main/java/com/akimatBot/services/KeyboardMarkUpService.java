package com.akimatBot.services;


import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.standart.Button;
import com.akimatBot.entity.standart.Keyboard;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.repository.repos.ButtonRepository;
import com.akimatBot.repository.repos.KeyboardMarkUpRepository;
import com.akimatBot.utils.Const;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyboardMarkUpService {
    private ButtonRepository buttonRepository = TelegramBotRepositoryProvider.getButtonRepository();
    private KeyboardMarkUpRepository keyboardMarkUpRepository = TelegramBotRepositoryProvider.getKeyboardMarkUpRepository();

    public InlineKeyboardMarkup getInlineKeyboardMarkup(long keyboardId, int langId) {
        Keyboard keyboard = keyboardMarkUpRepository.findById(keyboardId);

        String[] rows = keyboard.getButtonIds().split(";");
        return getInlineKeyboard(rows,langId);

    }

    public ReplyKeyboard  select(long keyboardMarkUpId, long chatId) {
        if (keyboardMarkUpId < 0) {
            return new ReplyKeyboardRemove();
        }
        if (keyboardMarkUpId == 0) {
            return null;
        }

        Language language = getLanguage(chatId);
        if(language == null){
            language = Language.ru;
        }
        return getKeyboard(keyboardMarkUpRepository.findById(keyboardMarkUpId), language.getId());
    }

    private ReplyKeyboard           getKeyboard(Keyboard keyboard, int langId) {
        String buttonIds = keyboard.getButtonIds();
        if (buttonIds == null) {
            return null;
        }
        String[] rows = buttonIds.split(";");
        if (keyboard.isInline()) {
            return getInlineKeyboard(rows, langId);
        } else {
            return getReplyKeyboard(rows, langId);
        }
    }

    public List<List<InlineKeyboardButton>> getRowsKeyboard(int keyId, long chatId){

        Keyboard keyboard = keyboardMarkUpRepository.findById(keyId);
        String[] rowIds = keyboard.getButtonIds().split(Const.SPLIT);


        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (String buttonIdsString : rowIds) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            String[] buttonIds = buttonIdsString.split(",");
            for (String buttonId : buttonIds) {
                Button buttonFromDb = buttonRepository.findByIdAndLangId(Integer.parseInt(buttonId), getLanguage(chatId).getId());
                InlineKeyboardButton button = new InlineKeyboardButton();
                String buttonText = buttonFromDb.getName();
                button.setText(buttonText);

                buttonText = buttonText.length() < 64 ? buttonText : buttonText.substring(0, 64);
                button.setCallbackData(buttonText);
                if (buttonFromDb.getId() == 148){
//                    button.setSwitchInlineQuery("123");
                    button.setSwitchInlineQueryCurrentChat("set");
                    button.setCallbackData(null);
                }
                else if(buttonFromDb.getId() == 167){
                    button.setCallbackData(String.valueOf(1));
                }
                else if(buttonFromDb.getId() == 168){
                    button.setCallbackData(String.valueOf(2));
                }
                if(buttonFromDb.getId() == 169){
                    button.setCallbackData(String.valueOf(3));
                }
                else if(buttonFromDb.getId()==8){
                    button.setSwitchInlineQueryCurrentChat("spec");
                    button.setCallbackData(null);
                }
                row.add(button);
            }
            rows.add(row);
        }

        return rows;
    }

    private InlineKeyboardMarkup    getInlineKeyboard(String[] rowIds, int langId) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (String buttonIdsString : rowIds) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            String[] buttonIds = buttonIdsString.split(",");
            for (String buttonId : buttonIds) {
                Button buttonFromDb = buttonRepository.findByIdAndLangId(Long.parseLong(buttonId), langId);
                InlineKeyboardButton button = new InlineKeyboardButton();
                String buttonText = buttonFromDb.getName();
                button.setText(buttonText);
                String url = buttonFromDb.getUrl();
                if (url != null) {
                    button.setUrl(url);
                } else {
                    buttonText = buttonText.length() < 64 ? buttonText : buttonText.substring(0,64);
                    button.setCallbackData(buttonText);
                }
                row.add(button);
            }
            rows.add(row);
        }
        keyboard.setKeyboard(rows);
        return keyboard;
    }

    private ReplyKeyboard           getReplyKeyboard(String[] rows, int langId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        boolean isRequestContact = false;
        for (String buttonIdsString : rows) {
            KeyboardRow keyboardRow = new KeyboardRow();
            String[] buttonIds = buttonIdsString.split(",");
            for (String buttonId : buttonIds) {
                Button buttonFromDb = buttonRepository.findByIdAndLangId(Integer.parseInt(buttonId), langId);
                KeyboardButton button = new KeyboardButton();
                String buttonText = buttonFromDb.getName();
                button.setText(buttonText);
                button.setRequestContact(buttonFromDb.getRequestContact());
                if (buttonFromDb.getRequestContact()) {
                    isRequestContact = true;
                }
                if(buttonFromDb.getId()==Const.REQUEST_LOCATION_BUTTON){
                    button.setRequestLocation(true);
                }
                keyboardRow.add(button);
            }
            keyboardRowList.add(keyboardRow);
        }
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setOneTimeKeyboard(isRequestContact);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboard  selectForEdition(long keyboardMarkUpId, Language language) {
        if (keyboardMarkUpId < 0) {
            return new ReplyKeyboardRemove();
        }
        if (keyboardMarkUpId == 0) {
            return null;
        }

        return getKeyboardForEdition(keyboardMarkUpRepository.findById((int)keyboardMarkUpId), language);
    }

    private ReplyKeyboard           getKeyboardForEdition(Keyboard keyboard, Language language) {
        String buttonIds = keyboard.getButtonIds();
        if (buttonIds == null) {
            return null;
        }
        String[] rows = buttonIds.split(";");
        return getInlineKeyboardForEdition(rows, language);
    }

    private InlineKeyboardMarkup    getInlineKeyboardForEdition(String[] rowIds, Language language) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (String buttonIdsString : rowIds) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            String[] buttonIds = buttonIdsString.split(",");
            for (String buttonId : buttonIds) {
                Button buttonFromDb = buttonRepository.findByIdAndLangId(Integer.parseInt(buttonId), language.getId());
                InlineKeyboardButton button = new InlineKeyboardButton();
                String buttonText = buttonFromDb.getName();
                button.setText(buttonText);
                String url = buttonFromDb.getUrl();
                if (url != null) {
                    button.setUrl(url);
                } else {
                    button.setCallbackData(buttonId);
                }
                row.add(button);
            }
            rows.add(row);
        }
        keyboard.setKeyboard(rows);
        return keyboard;
    }

    public boolean isInline(long keyboardMarkUpId) {
        return keyboardMarkUpRepository.findByIdAndInlineIsTrue((int)keyboardMarkUpId);
    }

    public List<Button> getListForEdit(long keyId, int langId) {
        List<Button> list = new ArrayList<>();
        for (String x : getButtonString(keyId).split(";")) {
            list.add(buttonRepository.findByIdAndLangId(Integer.parseInt(x), langId));
        }
        return list;
    }

    public String getButtonString(long id) {
        return keyboardMarkUpRepository.findById(id).getButtonIds();
    }

    private Language getLanguage(long chatId) {
        if (chatId == 0) return Language.ru;
        return LanguageService.getLanguage(chatId);
    }

}
