//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.FoodCategory;
//import com.akimatBot.entity.custom.FoodSubCategoryEntity;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.utils.ButtonsLeaf;
//import com.akimatBot.utils.ButtonsLeafChanged;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class id079_show_all_combos extends Command {
//    private List<Food> foods;
//    private String back = "\uD83D\uDD19 Назад";
//    private Food food;
//    private ButtonsLeafChanged buttonsLeafChanged;
//    private ButtonsLeaf buttonsLeaf;
//    private List<String> finalList;
//    private List<String> finalList2;
//    private List<FoodSubCategoryEntity> foodSubCategories;
//    private List<FoodCategory> foodCategories;
//    private FoodCategory foodCategory;
//    private FoodSubCategoryEntity foodSubCategory;
//    private WaitingType editWaitingType;
//    private boolean isAllFood;
//    private int specialOfferPrice;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        if(!isAdmin()){
//            sendMessage(Const.NO_ACCESS);
//            return EXIT;
//        }
//        switch (waitingType) {
//            case START:
//                deleteMessage(updateMessageId);
//                foods = foodRepository.findFoodsByFoodCategory(TelegramBotRepositoryProvider.getFoodSubCategoryRepo().findFoodSubCategoryEntityById(17));
//                showAllFoods();
//                waitingType = WaitingType.CHOOSE_FOOD;
//                return COMEBACK;
//
//            case CHOOSE_FOOD:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()) {
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        sendMessageWithKeyboard(getText(39), buttonsLeaf.getListButtonInlineWithBack());
//                    } else {
//                        if (isButton(67)) {
//                            deleteMessage(updateMessageId);
//                            return EXIT;
//                        }
//                        deleteMessage(updateMessageId);
//                        food = foods.stream()
//                                .filter(botEntity -> updateMessageText.equals(String.valueOf(botEntity.getId())))
//                                .findAny()
//                                .orElse(null);
//                        showFood();
//                        waitingType = WaitingType.SHOW_FOOD;
//                    }
//                } else {
//                    wrongData();
//                    showAllFoods();
//                }
//                return COMEBACK;
//            case SHOW_FOOD:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()) {
//                    if (isButton(82)) {
//                        System.out.println("Редактировать имя");
//                        getName();
//                        editWaitingType = WaitingType.EDIT_NAME;
//                    } else if (isButton(83)) {
//                        System.out.println("Редактировать описание");
//                        getDescription();
//                        editWaitingType = WaitingType.EDIT_DESCRIPTION;
//                    } else if (isButton(84)) {
//                        System.out.println("Редактировать цену");
//                        getPrice();
//                        editWaitingType = WaitingType.EDIT_PRICE;
//                    } else if (isButton(85)) {
//                        System.out.println("Удалить");
//                        sendMessageWithKeyboard(getText(62), 16);
//                        waitingType = WaitingType.DELETE;
//                        return COMEBACK;
//                    } else if (isButton(86)) {
//                        System.out.println("Редактировать фото");
//                        getPhoto();
//                        editWaitingType = WaitingType.EDIT_PHOTO;
//                    } else if (isButton(67)) {
//                        System.out.println("НАЗАД");
//                        deleteMessage(updateMessageId);
//                        showAllFoodsSendMessage();
//                        waitingType = WaitingType.CHOOSE_FOOD;
//                        return COMEBACK;
//                    }
//                    else if (isButton(88)){
//                        System.out.println("Редактировать суб-категорию");
//                        foodCategories = foodCategoryRepo.findAll();
//                        showAllCategories();
//                        waitingType = WaitingType.EDIT_CATEGORY;
//                        return COMEBACK;
//                    }
//                    else if(isButton(150)){
//                        System.out.println("");
//                        getSpecialOfferPrice();
//                        waitingType = WaitingType.SET_SPECIAL_OFFER;
//                        return COMEBACK;
//                    }
//                    else if(isButton(151)){
//                        food.setSpecialOfferSum(null);
//                        food = foodRepository.save(food);
//                        showFood();
//                        return COMEBACK;
//                    }
//                    waitingType = WaitingType.EDIT_FOOD;
//                } else {
//                    wrongData();
//                    showFood();
//                }
//
//                return COMEBACK;
//            case SET_SPECIAL_OFFER:
//                if(update.hasMessage()&&update.getMessage().hasText()&&isNumeric(update.getMessage().getText())){
//                    specialOfferPrice = Integer.parseInt(update.getMessage().getText());
//                    confirmSpecialOfferPrice();
//                    waitingType = WaitingType.CONFIRM_SPECIAL_OFFER;
//                }
//                else{
//                    wrongData();
//                    getSpecialOfferPrice();
//                }
//                return COMEBACK;
//            case CONFIRM_SPECIAL_OFFER:
//                if(hasCallbackQuery()){
//                    if(isButton(56)){
//                        food.setSpecialOfferSum(specialOfferPrice);
//                        food = foodRepository.save(food);
//                    }
//                    showFood();
//                    waitingType = WaitingType.SHOW_FOOD;
//                    return COMEBACK;
//                }
//            case DELETE:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()){
//                    if(isButton(56)){
//                        foodRepository.delete(food);
//                        sendMessageWithKeyboard(getText(63), 29);
//                        waitingType = WaitingType.CHOOSE_OPTION;
//                    }
//                    else{
//                        showFood();
//                        waitingType = WaitingType.SHOW_FOOD;
//                    }
//
//                }
//                else{
//                    wrongData();
//                    sendMessageWithKeyboard(getText(62), 16);
//
//                }
//                return COMEBACK;
//            case EDIT_FOOD:
//                deleteMessage(updateMessageId);
//                if (update.hasMessage()) {
//                    switch (editWaitingType) {
//                        case EDIT_NAME:
//                            //confirm(update.getMessage().getText());
//                            food.setNameRu(update.getMessage().getText());
//                            break;
//                        case EDIT_DESCRIPTION:
//                            food.setDescriptionRu(update.getMessage().getText());
//                            break;
//                        case EDIT_PRICE:
//                            food.setPrice(Integer.parseInt(update.getMessage().getText()));
//                            break;
//                        case EDIT_PHOTO:
//                            if (update.getMessage().hasPhoto()) {
//                                food.setPhoto_url(updateMessagePhoto);
//                            } else {
//                                wrongData();
//                                getPhoto();
//                                waitingType = WaitingType.EDIT_FOOD;
//                                return COMEBACK;
//                            }
//                            break;
//
//
//
//                    }
//                    foodRepository.save(food);
//                    showFood();
//                    waitingType = WaitingType.SHOW_FOOD;
//                    return COMEBACK;
//                }
//
//
//
//
//        }
//        return EXIT;
//    }
//    public int confirm(String text) throws TelegramApiException {
//        return sendMessageWithKeyboard(String.format(getText(43), text), 16);
//    }
//
//    public int getDescription() throws TelegramApiException {
//        return sendMessage(getText(Const.GET_FOOD_DESCRIPTION));
//    }
//
//    public int getName() throws TelegramApiException {
//        return sendMessage(getText(Const.GET_FOOD_NAME));
//    }
//
//    public int getPhoto() throws TelegramApiException {
//        return sendMessage(getText(Const.GET_PHOTO));
//    }
//
//    public int getSpecialOfferPrice() throws TelegramApiException {
//        return sendMessage(String.format(getText(138), food.getNameRu(), food.getPrice()));
//    }
//    public int confirmSpecialOfferPrice() throws TelegramApiException {
//        return sendMessageWithKeyboard(String.format(getText(139), food.getNameRu(), food.getPrice(), food.getPrice()-specialOfferPrice), 16);
//    }
//
//    public int getPrice() throws TelegramApiException {
//        return sendMessage(getText(Const.GET_FOOD_PRICE));
//    }
//
//    public int showFood() throws TelegramApiException {
//        String s;
//        int i = getLanguageId();
//
//        if(food.getSpecialOfferSum()==null){
//            s = getText(40);
//            return sendMessageWithPhotoAndKeyboard(String.format(s, food.getNameRu(), food.getDescriptionRu(), food.getPrice(), food.getFoodCategory().getName(getLanguageId())), 30, food.getPhoto_url(), chatId);
//        }
//        s = getText(140);
//        return sendMessageWithPhotoAndKeyboard(String.format(s, food.getNameRu(), food.getDescriptionRu(), food.getPrice(), food.getFoodCategory().getName(getLanguageId()), food.getSpecialOfferSum(), food.getPrice()-food.getSpecialOfferSum()), 59, food.getPhoto_url(), chatId);
//
//    }
//    public static boolean isNumeric(String string) {
//        int intValue;
//
//        if (string == null || string.equals("")) {
//            System.out.println("String cannot be parsed, it is null or empty.");
//            return false;
//        }
//
//        try {
//            intValue = Integer.parseInt(string);
//            return true;
//        } catch (NumberFormatException e) {
//            System.out.println("Input String cannot be parsed to Integer.");
//        }
//        return false;
//    }
//    public int showAllSubCategories() throws TelegramApiException {
//        List<String> list = new ArrayList<>();
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        foodSubCategories.forEach((e) -> {
//            finalList.add(e.getName(getLanguageId()));
//            finalList2.add(String.valueOf(e.getId()));
//        });
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10, 2, 35, chatId);
////        editMessageWithKeyboard(getText(11), updateMessageId, buttonsLeaf.getListButtonInline());
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(11), buttonsLeaf.getListButtonInlineWithBack()));
//    }
//
//    public int showAllCategories() throws TelegramApiException {
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        foodCategories.forEach((e) -> {
//            finalList.add(e.getName(getLanguageId()));
//            finalList2.add(String.valueOf(e.getId()));
//        });
////        finalList.add(back);
////        finalList2.add(back);
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10, 2, 35, chatId);
////        editMessageWithKeyboard(getText(9), updateMessageId, buttonsLeaf.getListButtonInline());
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(9), buttonsLeaf.getListButtonInlineWithBack()));
//    }
//    public int showAllCategoriesSendMessage() throws TelegramApiException {
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        foodCategories.forEach((e) -> {
//            finalList.add(e.getName(getLanguageId()));
//            finalList2.add(String.valueOf(e.getId()));
//        });
////        finalList.add(back);
////        finalList2.add(back);
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10, 2, 35, chatId);
////        editMessageWithKeyboard(getText(9), updateMessageId, buttonsLeaf.getListButtonInline());
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(9), buttonsLeaf.getListButtonInlineWithBack()));
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//
//    public int showAllFoods() throws TelegramApiException {
//
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        foods.forEach((e) -> {
//            finalList.add(e.getId() + " | " + e.getNameRu());
//            finalList2.add(String.valueOf(e.getId()));
//        });
////        finalList.add(back);
////        finalList2.add(String.valueOf(foods.size() + 1));
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 5, 35, chatId);
//        waitingType = WaitingType.CHOOSE_FOOD;
////        editMessageWithKeyboard(getText(39), updateMessageId, buttonsLeaf.getListButtonInline());
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(39), buttonsLeaf.getListButtonInlineWithBack()));
//    }
//
//    public int showAllFoodsSendMessage() throws TelegramApiException {
//
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        foods.forEach((e) -> {
//            finalList.add(e.getId() + " | " + e.getNameRu());
//            finalList2.add(String.valueOf(e.getId()));
//        });
//        finalList.add(back);
//        finalList2.add(String.valueOf(foods.size() + 1));
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 5, 35, chatId);
//        waitingType = WaitingType.CHOOSE_FOOD;
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(39), buttonsLeaf.getListButtonInlineWithBack()));
//
//    }
//}
