//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.FoodCategory;
//import com.akimatBot.entity.custom.FoodSubCategoryEntity;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.FoodCategoryRepo;
//import com.akimatBot.utils.ButtonsLeaf;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class id016_show_category_adminOnly extends Command {
//    private final FoodCategoryRepo foodCategoryRepo = TelegramBotRepositoryProvider.getFoodCategoryRepo();
//    private List<FoodCategory> foodCategories;
//    private List<FoodSubCategoryEntity> foodSubCategories;
//    private FoodCategory foodCategory;
//    private ButtonsLeaf buttonsLeaf;
//    private List<String> finalList;
//    private List<String> finalList2;
//    private WaitingType editWaitingType;
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        if (!isAdmin()) {
//            sendMessage(Const.NO_ACCESS);
//            return EXIT;
//        }
//        switch (waitingType){
//            case START:
//                foodCategories = foodCategoryRepo.findAll();
//                showAllCategories();
//                waitingType = WaitingType.CHOOSE_CATEGORY;
//                return COMEBACK;
//            case CHOOSE_CATEGORY:
//                if (hasCallbackQuery()) {
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        editMessageWithKeyboard(getText(9), updateMessageId, buttonsLeaf.getListButtonInline());
//                    }
//
//
//                    //deleteMessage(updateMessageId);
//                    foodCategory = foodCategories.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//                    showCategory();
//                    waitingType = WaitingType.SHOW_CATEGORY;
//
//                } else {
//                    wrongData();
//                    showAllCategories();
//                }
//                return COMEBACK;
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
//                    else if(isButton(85)){
//                        System.out.println("Удалить");
//                        foodCategoryRepo.delete(foodCategory);
//                        sendMessage(getText(Const.CATEGORY_SUCCESSFULLY_DELETE));
//                        return EXIT;
//                    }
//                    else if(isButton(67)){
//                        System.out.println("Назад");
//                        showAllCategories();
//                        waitingType = WaitingType.CHOOSE_CATEGORY;
//                        return COMEBACK;
//                    }
//                    waitingType = WaitingType.EDIT_CATEGORY;
//                }
//                else {
//                    wrongData();
//                    showCategory();
//                }
//                return COMEBACK;
//            case EDIT_CATEGORY:
//                if(update.getMessage().hasText() && update.hasMessage()){
//                    deleteMessage(updateMessageId);
//                    switch (editWaitingType){
//                        case EDIT_NAME_KZ:
//                            foodCategory.setCategoryNameKaz(update.getMessage().getText());
//                            break;
//                        case EDIT_NAME_RU:
//                            foodCategory.setCategoryNameRu(update.getMessage().getText());
//                            break;
//                        case EDIT_DESCRIPTION_KZ:
//                            foodCategory.setCategoryDescriptionKaz(update.getMessage().getText());
//                            break;
//                        case EDIT_DESCRIPTION_RU:
//                            foodCategory.setCategoryDescriptionRu(update.getMessage().getText());
//                            break;
//                    }
//                    foodCategoryRepo.save(foodCategory);
//                    showCategorySend();
//                    waitingType = WaitingType.SHOW_CATEGORY;
//
//                }
//                else{
//                    wrongData();
//                    showCategorySend();
//                    waitingType = WaitingType.SHOW_CATEGORY;
//                }
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//    public int getCategoryNameKz() throws TelegramApiException{
//        return  sendMessage(getText(Const.GET_CATEGORY_NAME_KZ));
//    }
//    public int getCategoryNameRu() throws TelegramApiException{
//        return  sendMessage(getText(Const.GET_CATEGORY_NAME_RU));
//    }
//    public int getCategoryDescriptionKz() throws TelegramApiException{
//        return sendMessage(getText(Const.GET_CATEGORY_DESCRIPTION_KZ));
//    }
//    public int getCategoryDescriptionRu() throws TelegramApiException{
//        return sendMessage(getText(Const.GET_CATEGORY_DESCRIPTION_RU));
//    }
//    public void showCategory() throws TelegramApiException{
//         editMessageWithKeyboard(String.format(getText(53), foodCategory.getCategoryNameKaz(), foodCategory.getCategoryNameRu(), foodCategory.getCategoryDescriptionKaz(), foodCategory.getCategoryDescriptionRu()),updateMessageId, 34);
//    }
//    public void showCategorySend() throws TelegramApiException{
//        sendMessageWithKeyboard(String.format(getText(53), foodCategory.getCategoryNameKaz(), foodCategory.getCategoryNameRu(), foodCategory.getCategoryDescriptionKaz(), foodCategory.getCategoryDescriptionRu()), 34);
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//    public int showAllCategories() throws TelegramApiException {
//        List<String> list = new ArrayList<>();
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        foodCategories.forEach((e) -> {
//            finalList.add(e.getName(getLanguageId()));
//            finalList2.add(String.valueOf(e.getId()));
//        });
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10, 2);
//
////        editMessageWithKeyboard(getText(9), updateMessageId ,buttonsLeaf.getListButtonInline());
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(11), buttonsLeaf.getListButtonInline()));
//    }
//    public void showAllCategoriesEdit() throws TelegramApiException {
//        List<String> list = new ArrayList<>();
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        foodCategories.forEach((e) -> {
//            finalList.add(e.getName(getLanguageId()));
//            finalList2.add(String.valueOf(e.getId()));
//        });
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10, 2);
////        editMessageWithKeyboard(getText(9), updateMessageId ,buttonsLeaf.getListButtonInline());
//        editMessageWithKeyboard(getText(11), updateMessageId,buttonsLeaf.getListButtonInline());
//    }
//}
