//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.KaspiAccount;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.utils.ButtonsLeaf;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class id097_editKaspi extends Command {
//    private KaspiAccount kaspiAccount;
//    private List<KaspiAccount> kaspiAccounts;
//    private ButtonsLeaf buttonsLeaf;
//    private WaitingType editWaitingType;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        if (!isAdmin()) {
//            sendMessage(Const.NO_ACCESS);
//            return EXIT;
//        }
//        switch (waitingType){
//            case START:
//                deleteMessage(updateMessageId);
//                showKaspiAccounts();
//                waitingType = WaitingType.CHOOSE_OPTION;
//                return COMEBACK;
//            case CHOOSE_OPTION:
//                deleteMessage(updateMessageId);
//                if(hasCallbackQuery()){
//                    if(isButton(67)){
//                        deleteMessage(updateMessageId);
//                        deleteMessage(lastSendMessageID);
//                        return EXIT;
//                    }
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        sendMessageWithKeyboard(225, buttonsLeaf.getListButtonInlineWithBack());
//                    }
//                    kaspiAccount = kaspiAccounts.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//                    showKaspi();
//                    waitingType = WaitingType.SHOW_KASPI;
//                    return COMEBACK;
//                }
//                else{
//                    wrongData();
//                }
//            case SHOW_KASPI:
//                deleteMessage(updateMessageId);
//                if(hasCallbackQuery()){
//                    if(isButton(244)){
//                        System.out.println("Редактировать имя");
//                        getName();
//                        editWaitingType = WaitingType.EDIT_NAME;
//                    }
//                    else if(isButton(245)){
//                        System.out.println("Редактировать номер");
//                        getPhone();
//                        editWaitingType = WaitingType.EDIT_PHONE;
//                    }
//                    else if(isButton(85)){
//                        System.out.println("Удалить");
//                        sendMessageWithKeyboard(getText(229), 16);
//                        waitingType = WaitingType.DELETE;
//                        return COMEBACK;
//                    }
//                    else if(isButton(67)){
//                        System.out.println("НАЗАД");
//                        deleteMessage(updateMessageId);
//                        showKaspiAccounts();
//                        waitingType = WaitingType.CHOOSE_OPTION;
//                        return COMEBACK;
//                    }
//                    waitingType = WaitingType.EDIT_KASPI;
//
//                }
//                else {
//                    wrongData();
//                    showKaspi();
//                }
//
//                return COMEBACK;
//
//            case DELETE:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()){
//                    if(isButton(56)){
//                        kaspiAccountsRepository.delete(kaspiAccount);
//
//                        showKaspiAccounts();
//                        waitingType = WaitingType.CHOOSE_OPTION;
//                    }
//                    else{
//                        showKaspi();
//                        waitingType = WaitingType.SHOW_KASPI;
//                    }
//
//                }
//                else{
//                    wrongData();
//                    sendMessageWithKeyboard(getText(229), 16);
//                }
//                return COMEBACK;
//            case EDIT_KASPI:
//                deleteMessage(updateMessageId);
//                if (hasMessageText()) {
//                    switch (editWaitingType) {
//                        case EDIT_NAME:
//                            //confirm(update.getMessage().getText());
//                            kaspiAccount.setName(update.getMessage().getText());
//                            break;
//                        case EDIT_PHONE:
//                            if (isPhoneNumber(updateMessageText)) {
//                                kaspiAccount.setPhone(updateMessageText);
//                            } else {
//                                wrongData();
//                                getPhone();
//                                waitingType = WaitingType.EDIT_FOOD;
//                                return COMEBACK;
//                            }
//                            break;
//
//                    }
//                    kaspiAccountsRepository.save(kaspiAccount);
//                    showKaspi();
//                    waitingType = WaitingType.SHOW_KASPI;
//                    return COMEBACK;
//                }
//        }
//        return EXIT;
//    }
//    public int getName() throws TelegramApiException{
//        return sendMessage("Введите имя владельца номера");
//    }
//    private boolean isPhoneNumber(String phone) {
//
//        if (phone.charAt(0) == '8') {
//            phone = phone.replaceFirst("8", "+7");
//        } else if (phone.charAt(0) == '7') {
//            phone = phone.replaceFirst("7", "+7");
//        }
//        return phone.charAt(0) == '+' && phone.charAt(1) == '7' && phone.substring(2).length() == 10 && isLong(phone.substring(2)) ;
//    }
//    public int getPhone() throws TelegramApiException{
//        return sendMessage("Введите номер каспи");
//    }
//    private int showKaspi() throws TelegramApiException{
//        return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(228),
//                kaspiAccount.getName(), kaspiAccount.getPhone(), kaspiAccount.getName()+"|"+kaspiAccount.getPhone()), 101));
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//    public int showKaspiAccounts() throws TelegramApiException{
//        List<String> buttonsName = new ArrayList<>();
//        List<String> callbackData = new ArrayList<>();
//
//        kaspiAccounts=kaspiAccountsRepository.findAll();
//        kaspiAccounts.forEach((e) ->{
//            buttonsName.add(e.getName()+"|"+e.getPhone());
//            callbackData.add(String.valueOf(e.getId()));
//        });
//        buttonsLeaf = new ButtonsLeaf(buttonsName, callbackData);
//
//        return sendMessageWithKeyboard(225, buttonsLeaf.getListButtonInlineWithBack());
//
//    }
//}
