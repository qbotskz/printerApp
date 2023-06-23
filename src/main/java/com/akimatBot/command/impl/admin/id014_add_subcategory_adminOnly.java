//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.FoodCategory;
//import com.akimatBot.entity.custom.FoodSubCategoryEntity;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.FoodCategoryRepo;
//import com.akimatBot.repository.repos.FoodSubCategoryRepo;
//import com.akimatBot.utils.ButtonsLeaf;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class id014_add_subcategory_adminOnly extends Command {
//    private final FoodSubCategoryRepo foodSubCategoryRepo = TelegramBotRepositoryProvider.getFoodSubCategoryRepo();
//    private final FoodCategoryRepo foodCategoryRepo = TelegramBotRepositoryProvider.getFoodCategoryRepo();
//    private List<FoodCategory> foodCategories;
//    private FoodSubCategoryEntity foodSubCategory;
//    private FoodCategory foodCategory;
//    private WaitingType editWaitingType;
//    private ButtonsLeaf buttonsLeaf;
//    private List<String> finalList;
//    private List<String> finalList2;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        if (!isAdmin()) {
//            sendMessage(Const.NO_ACCESS);
//            return EXIT;
//        }
//        switch (waitingType){
//            case START:
//                deleteMessage(updateMessageId);
//                getCategoryNameRu();
//                foodSubCategory = new FoodSubCategoryEntity();
//                waitingType = WaitingType.GET_NAME_RU;
//                return COMEBACK;
//            case GET_NAME_RU:
//                deleteMessage(updateMessageId);
//                if(update.hasMessage() && update.getMessage().hasText()){
//                    foodSubCategory.setSubCategoryNameRu(update.getMessage().getText());
//                    getCategoryNameKz();
//                    waitingType = WaitingType.GET_NAME_KZ;
//                }
//                else{
//                    wrongData();
//                    getCategoryNameRu();
//                }
//                return COMEBACK;
//            case GET_NAME_KZ:
//                deleteMessage(updateMessageId);
//                if(update.hasMessage() && update.getMessage().hasText()){
//                    foodSubCategory.setSubCategoryNameKaz(update.getMessage().getText());
//                    getCategoryDescriptionRu();
//                    waitingType = WaitingType.GET_DESCRIPTION_RU;
//                }
//                else{
//                    wrongData();
//                    getCategoryNameKz();
//                }
//                return COMEBACK;
//            case GET_DESCRIPTION_RU:
//                deleteMessage(updateMessageId);
//                if(update.hasMessage() && update.getMessage().hasText()){
//                    foodSubCategory.setSubCategoryDescriptionRu(update.getMessage().getText());
//                    getCategoryDescriptionKz();
//                    waitingType = WaitingType.GET_DESCRIPTION_KZ;
//                }
//                else{
//                    wrongData();
//                    getCategoryDescriptionRu();
//                }
//                return COMEBACK;
//            case GET_DESCRIPTION_KZ:
//                deleteMessage(updateMessageId);
//                if(update.hasMessage() && update.getMessage().hasText()){
//                    foodSubCategory.setSubCategoryDescriptionKaz(update.getMessage().getText());
//                    foodCategories = foodCategoryRepo.findAll();
////                    showAllCategories();
////                    waitingType = WaitingType.CHOOSE_CATEGORY;
////                    showCategory();
////                    waitingType = WaitingType.SHOW_CATEGORY;
//                    isInlineMessage();
//                    waitingType = WaitingType.IS_INLINE;
//                }
//                else{
//                    wrongData();
//                    getCategoryDescriptionKz();
//                }
//                return COMEBACK;
//            case IS_INLINE:
//                deleteMessage(updateMessageId);
//                if(hasCallbackQuery()){
//                    if(isButton(56)){
//                        foodSubCategory.setInline(true);
////                        foodSubCategory.setFoodCategory();
//                        showCategory();
//                        waitingType = WaitingType.SHOW_CATEGORY;
//                    }
//                    else if(isButton(57)){
//                        foodSubCategory.setInline(false);
//                        showAllCategories();
//                        waitingType = WaitingType.CHOOSE_CATEGORY;
//                    }
//
//                }
//                else{
//                    wrongData();
//                    isInlineMessage();
//                }
//                return COMEBACK;
//            case CHOOSE_CATEGORY:
//                if (hasCallbackQuery()) {
//
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        editMessageWithKeyboard(getText(9), updateMessageId, buttonsLeaf.getListButtonInline());
////                        deleteMessage(updateMessageId);
//                    }
//
//                    deleteMessage(updateMessageId);
//                    foodCategory = foodCategories.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//                    foodSubCategory.setFoodCategory(foodCategory);
//                    showCategory();
//                    waitingType = WaitingType.SHOW_CATEGORY;
//
//
//                } else {
//                    wrongData();
//                    showAllCategories();
//                }
//                return COMEBACK;
//
//            case SHOW_CATEGORY:
//                deleteMessage(updateMessageId);
//                if(hasCallbackQuery()){
//                    if(isButton(92)){
//                        System.out.println("Редактировать имя на казахском");
//                        getCategoryNameKz();
//                        editWaitingType = WaitingType.EDIT_NAME_KZ;
//                    }
//                    else if(isButton(93)){
//                        System.out.println("Редактировать имя на русском");
//                        getCategoryNameRu();
//                        editWaitingType = WaitingType.EDIT_NAME_RU;
//                    }
//                    else if(isButton(94)){
//                        System.out.println("Редактировать описание на казахском");
//                        getCategoryDescriptionKz();
//                        editWaitingType = WaitingType.EDIT_DESCRIPTION_KZ;
//                    }
//                    else if(isButton(95)){
//                        System.out.println("Редактировать описание на русском");
//                        getCategoryDescriptionRu();
//                        editWaitingType = WaitingType.EDIT_DESCRIPTION_RU;
//                    }
//                    else if(isButton(98)){
//                        System.out.println("Редактировать категорию");
//                        showAllCategories();
//                        waitingType = WaitingType.EDIT_CATEGORY2;
//                        return COMEBACK;
//                    }
//                    else if(isButton(87)){
//                        System.out.println("Завершить");
//                        foodSubCategoryRepo.save(foodSubCategory);
//                        sendMessage(getText(Const.CATEGORY_SUCCESSFULLY_CREATED));
//                        return EXIT;
//
//                    }
//                    waitingType = WaitingType.EDIT_CATEGORY;
//                }
//                else{
//                    wrongData();
//                    showCategory();
//                }
//                return COMEBACK;
//            case EDIT_CATEGORY:
//                if(update.getMessage().hasText() && update.hasMessage()){
//                    switch (editWaitingType){
//                        case EDIT_NAME_KZ:
//                            foodSubCategory.setSubCategoryNameKaz(update.getMessage().getText());
//                            break;
//                        case EDIT_NAME_RU:
//                            foodSubCategory.setSubCategoryNameRu(update.getMessage().getText());
//                            break;
//                        case EDIT_DESCRIPTION_KZ:
//                            foodSubCategory.setSubCategoryDescriptionKaz(update.getMessage().getText());
//                            break;
//                        case EDIT_DESCRIPTION_RU:
//                            foodSubCategory.setSubCategoryDescriptionRu(update.getMessage().getText());
//                            break;
//
//                    }
//                    showCategory();
//                    waitingType = WaitingType.SHOW_CATEGORY;
//
//                }
//                else{
//                    wrongData();
//                    showCategory();
//                    waitingType = WaitingType.SHOW_CATEGORY;
//                }
//                return COMEBACK;
//            case EDIT_CATEGORY2:
//                if(hasCallbackQuery()){
//                    if(buttonsLeaf.isNext(updateMessageText)){
//                        editMessageWithKeyboard(getText(57), updateMessageId, buttonsLeaf.getListButtonInline());
//                    }
//                    foodCategory = foodCategories.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//                    foodSubCategory.setFoodCategory(foodCategory);
//                    showCategory();
//
//
//                }
//                else{
//                    wrongData();
//                    showCategory();
//                }
//                waitingType = WaitingType.SHOW_CATEGORY;
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//    public int showCategory() throws TelegramApiException{
//        if(!foodSubCategory.getInline()){
//            return sendMessageWithKeyboard(String.format(getText(56), foodSubCategory.getSubCategoryNameKaz(), foodSubCategory.getSubCategoryNameRu(), foodSubCategory.getSubCategoryDescriptionKaz(), foodSubCategory.getSubCategoryDescriptionRu(), foodSubCategory.getFoodCategory().getName(getLanguageId())), 36);
//        }
//        else{
//            return sendMessageWithKeyboard(String.format(getText(56), foodSubCategory.getSubCategoryNameKaz(), foodSubCategory.getSubCategoryNameRu(), foodSubCategory.getSubCategoryDescriptionKaz(), foodSubCategory.getSubCategoryDescriptionRu(), "inline"), 36);
//        }
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//    public int getCategoryNameKz() throws TelegramApiException{
//        return  sendMessage(getText(Const.GET_SUB_CATEGORY_NAME_KZ));
//    }
//    public int getCategoryNameRu() throws TelegramApiException{
//        return  sendMessage(getText(Const.GET_SUB_CATEGORY_NAME_RU));
//    }
//    public int getCategoryDescriptionKz() throws TelegramApiException{
//        return sendMessage(getText(Const.GET_SUB_CATEGORY_DESCRIPTION_KZ));
//    }
//    public int getCategoryDescriptionRu() throws TelegramApiException{
//        return sendMessage(getText(Const.GET_SUB_CATEGORY_DESCRIPTION_RU));
//    }
//    public int isInlineMessage() throws TelegramApiException{
//        return sendMessageWithKeyboard(getText(216), 16);
//    }
//    public int showAllCategories() throws TelegramApiException {
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        foodCategories.forEach((e) -> {
//            finalList.add(e.getName(getLanguageId()));
//            finalList2.add(String.valueOf(e.getId()));
//        });
////        finalList.add(back);
////        finalList2.add(back);
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10);
////        editMessageWithKeyboard(getText(57), updateMessageId, buttonsLeaf.getListButtonInline());
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(217), buttonsLeaf.getListButtonInline()));
//    }
//}
