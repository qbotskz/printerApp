//package com.akimatBot.command.impl;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Cashback;
//import com.akimatBot.entity.enums.City;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.utils.ButtonsLeaf;
//import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class id088_user_profile extends Command {
//    private User user;
//    private ButtonsLeaf buttonsLeaf;
//    private ArrayList<City> cities;
//    private Cashback cashback = cashbackRepository.findTopByOrderByIdDesc();
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        //sendMessageWithAddition();
//        switch (waitingType){
//            case START:
//                user = userRepository.findByChatId(chatId);
//                if(cashback!=null || (user.getCashback()!=null && user.getCashback()!=0)) {
//                    sendMessageWithKeyboard(getText(129), 57);
//                }
//                else{
//                    sendMessageWithKeyboard(getText(129), 95);
//                }
//
//                waitingType = WaitingType.CHOOSE_OPTION;
//                return COMEBACK;
//            case CHOOSE_OPTION:
//                    if(isButton(219)){
//                        user = userRepository.findByChatId(chatId);
//                        if(user.getCashback()==null){
//                            user.setCashback(0);
//                        }
//
//                        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
//                        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId()).setText(String.format(getText(204),user.getCashback())).setShowAlert(true);
//                        bot.execute(answerCallbackQuery);
//                    }
//                    else if(isButton(230)){
////                        editMessageWithKeyboard(getText(220), updateMessageId,93);
//                        List<String> list = new ArrayList<>();
//                        ArrayList<String> finalList = new ArrayList<>();
//                        ArrayList<String> finalList2 = new ArrayList<>();
//                        user = userRepository.findByChatId(chatId);
//
//                        cities = new ArrayList<>(Arrays.asList(City.values()));
//                        cities.forEach((e) -> {
//                            if(e == user.getCity()){
//                                finalList.add(e.getName()+"✅");
//                            }
//                            else finalList.add(e.getName());
//                            finalList2.add(String.valueOf(e.getId()));
//                        });
//                        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10, 1, 35, chatId);
//                        editMessageWithKeyboard(getText(220), updateMessageId, buttonsLeaf.getListButtonInlineWithBack());
//                        waitingType = WaitingType.CHOOSE_BRANCH;
//                        return COMEBACK;
//                    }
//                    return COMEBACK;
//            case CHOOSE_BRANCH:
////                deleteMessage(updateMessageId);
//
//                if(hasCallbackQuery()){
//                    if(isButton(67)){
//                        editMessageWithKeyboard(getText(129), updateMessageId,57);
//                        waitingType = WaitingType.CHOOSE_OPTION;
//                        return COMEBACK;
//                    }
//
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        editMessageWithKeyboard(getText(220), updateMessageId, buttonsLeaf.getListButtonInlineWithBack());
//                    }
////                    deleteMessage(updateMessageId);
//                    City city = cities.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//
//                    user.setCity(city);
//                    user = userRepository.save(user);
//
////                    if(isButton(228)){
////                        user.setCity(City.ALMATY);
////                    }
////                    else if(isButton(229)){
////                        user.setCity(City.NUR_SULTAN);
////                    }
//
//
//
//                    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
//                    answerCallbackQuery.setCallbackQueryId(getCallbackQuery().getId()).setText(String.format(getText(221),user.getCity().getName())).setShowAlert(true);
//                    bot.execute(answerCallbackQuery);
//                    editMessageWithKeyboard(getText(129), updateMessageId,57);
//
//                }
//
//                waitingType = WaitingType.CHOOSE_OPTION;
//                return COMEBACK;
//
//        }
//        return EXIT;
//    }
//}
