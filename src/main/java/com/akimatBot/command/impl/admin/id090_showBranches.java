//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.RestaurantBranch;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.utils.ButtonsLeaf;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class id090_showBranches extends Command {
//    private List<RestaurantBranch> branches;
//    private RestaurantBranch restaurantBranch;
//    private ButtonsLeaf buttonsLeaf;
//    private WaitingType editWaitingType;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                branches = restaurantBranchRepo.findAll();
//                showBranches();
//                waitingType = WaitingType.CHOOSE_BRANCH;
//                return COMEBACK;
//            case CHOOSE_BRANCH:
//                deleteMessage(updateMessageId);
//
//                if (hasCallbackQuery()) {
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        editMessageWithKeyboard(getText(209), updateMessageId, buttonsLeaf.getListButtonInline());
//                    }
//                    deleteMessage(updateMessageId);
//                    restaurantBranch = branches.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//                    showBranch();
//                    waitingType = WaitingType.SHOW_BRANCH;
//                } else {
//                    wrongData();
//                    showBranches();
//                }
//                return COMEBACK;
//            case SHOW_BRANCH:
//                deleteMessage(updateMessageId);
//
//                if (hasCallbackQuery()) {
//                    if (isButton(82)) {
//                        getBranchName();
//                        editWaitingType = WaitingType.EDIT_NAME;
//                    }
//                    else if (isButton(85)) {
//                        sendMessageWithKeyboard(getText(212), 16);
//                        waitingType = WaitingType.DELETE;
//                        return COMEBACK;
//                    }
//                    else if (isButton(67)) {
//                        showBranches();
//                        waitingType = WaitingType.CHOOSE_BRANCH;
//                        return COMEBACK;
//                    }
//
//                    waitingType = WaitingType.EDIT_BRANCH;
//                }
//                else{
//                    showBranch();
//                }
//                return COMEBACK;
//            case EDIT_BRANCH:
//                deleteMessage(updateMessageId);
//
//                if (update.hasMessage()) {
//                    if (editWaitingType == WaitingType.EDIT_NAME) {
//                        if (update.getMessage().hasText()) {
//                            restaurantBranch.setBranchName(update.getMessage().getText());
//                        } else {
//                            wrongData();
//                            getBranchName();
//                        }
//                    }
//                }
//                return COMEBACK;
//
//            case DELETE:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()){
//                    if(isButton(56)){
//                        String name = restaurantBranch.getBranchName();
//                        List<User> users = userRepository.findAllByRestaurantBranch(restaurantBranch);
//                        for(User u:users){
//                            u.setRestaurantBranch(null);
//                            userRepository.save(u);
//                        }
//                        restaurantBranchRepo.delete(restaurantBranch);
//                        branches = restaurantBranchRepo.findAll();
//                        sendMessage(String.format(getText(213), name));
//                        showBranches();
//                        waitingType = WaitingType.CHOOSE_BRANCH;
//                    }
//                    else{
//                        showBranch();
//                        waitingType = WaitingType.SHOW_BRANCH;
//                    }
//
//                }
//                else{
//                    wrongData();
//                    sendMessageWithKeyboard(getText(212), 16);
//
//                }
//                return COMEBACK;
//
//
//        }
//        return EXIT;
//    }
//    private int getBranchName() throws TelegramApiException{
//        return sendMessage(208);
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//    private int showBranch() throws TelegramApiException{
//        return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(211), restaurantBranch.getId(), restaurantBranch.getBranchName()), 90));
//    }
//    private int showBranches() throws TelegramApiException{
//        List<String> finalList = new ArrayList<>();
//        List<String> finalList2 = new ArrayList<>();
//        branches.forEach((e) -> {
//            finalList.add(e.getId() + " | " + e.getBranchName());
//            finalList2.add(String.valueOf(e.getId()));
//        });
////        finalList.add(back);
////        finalList2.add(String.valueOf(foods.size() + 1));
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10);
////        waitingType = WaitingType.CHOOSE_FOOD;
////        editMessageWithKeyboard(getText(39), updateMessageId, buttonsLeaf.getListButtonInline());
//        return sendMessageWithKeyboard(getText(209), buttonsLeaf.getListButtonInline());
//    }
//}
