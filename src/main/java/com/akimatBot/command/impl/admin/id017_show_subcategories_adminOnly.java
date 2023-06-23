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
//public class id017_show_subcategories_adminOnly extends Command {
//    private final FoodCategoryRepo foodCategoryRepo = TelegramBotRepositoryProvider.getFoodCategoryRepo();
//    private final FoodSubCategoryRepo foodSubCategoryRepo = TelegramBotRepositoryProvider.getFoodSubCategoryRepo();
//    private FoodSubCategoryEntity foodSubCategory;
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
//                deleteMessage(updateMessageId);
//                foodSubCategories = foodSubCategoryRepo.findAll();
//                showAllSubCategories();
//                waitingType = WaitingType.CHOOSE_CATEGORY;
//                return COMEBACK;
//            case CHOOSE_CATEGORY:
//                if(hasCallbackQuery()){
//                    if(buttonsLeaf.isNext(updateMessageText)){
//                        editMessageWithKeyboard(getText(9), updateMessageId, buttonsLeaf.getListButtonInline());
//                    }
//                    foodSubCategory = foodSubCategories.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//
//                    showCategory();
//                    waitingType = WaitingType.SHOW_CATEGORY;
//                }
//                else{
//                    wrongData();
//                    showAllSubCategories();
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
//                    else if(isButton(98)){
//                        System.out.println("Редактировать категорию");
//                        foodCategories  = foodCategoryRepo.findAll();
//
//                        showAllCategories();
//                        waitingType = WaitingType.EDIT_CATEGORY2;
//                        return COMEBACK;
//                    }
//                    else if(isButton(85)){
//                        System.out.println("УДАЛИТЬ");
//                        sendMessageWithKeyboard(getText(64), 16);
//                        waitingType = WaitingType.DELETE;
//                        return COMEBACK;
//
//                    }
//                    else if(isButton(67)){
//                        System.out.println("НАЗАД");
//                        deleteMessage(updateMessageId);
//                        showAllSubCategories();
//                        waitingType = WaitingType.CHOOSE_CATEGORY;
//                        return COMEBACK;
//                    }
//                    else if(isButton(233)){
//                        foodSubCategory.setInline(true);
//                        foodSubCategory = foodSubCategoryRepo.save(foodSubCategory);
//                        showCategory();
//                        return COMEBACK;
//                    }
//                    else if(isButton(234)){
//                        foodSubCategory.setInline(false);
//                        foodSubCategory = foodSubCategoryRepo.save(foodSubCategory);
//                        showCategory();
//                        return COMEBACK;
//                    }
//                    waitingType = WaitingType.EDIT_CATEGORY;
//                }
//                else{
//                    wrongData();
//                    showCategory();
//                }
//                return COMEBACK;
//            case EDIT_CATEGORY:
//
//                if(update.getMessage().hasText() && update.hasMessage()){
//                    deleteMessage(updateMessageId);
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
//                    foodSubCategoryRepo.save(foodSubCategory);
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
//            case EDIT_CATEGORY2:
//                deleteMessage(updateMessageId);
//                if(hasCallbackQuery()){
//                    if(buttonsLeaf.isNext(updateMessageText)){
//                        editMessageWithKeyboard(getText(57), updateMessageId, buttonsLeaf.getListButtonInline());
//                    }
//
//                    if(updateMessageText.equals("Inline")){
//                        foodSubCategory.setInline(true);
//                        foodCategory=null;
//
//                    }
//                    else{
//                        foodCategory = foodCategories.stream()
//                                .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                                .findAny()
//                                .orElse(null);
//
//
//
//                    }
//                    foodSubCategory.setFoodCategory(foodCategory);
//                    foodSubCategory = foodSubCategoryRepo.save(foodSubCategory);
//                    showCategory();
//
//                }
//                else{
//                    wrongData();
//                    showCategory();
//                }
//                waitingType = WaitingType.SHOW_CATEGORY;
//                return COMEBACK;
//            case DELETE:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()){
//                    if(isButton(56)){
//                        foodSubCategoryRepo.delete(foodSubCategory);
//                        showAllSubCategories();
//                        waitingType = WaitingType.CHOOSE_CATEGORY;
//                    }
//                    else{
//                        showCategory();
//                        waitingType = WaitingType.SHOW_CATEGORY;
//                    }
//
//                }
//                else{
//                    wrongData();
//                    sendMessageWithKeyboard(getText(62), 16);
//
//                }
//                return COMEBACK;
//
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
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10, 2);
////        editMessageWithKeyboard(getText(11), updateMessageId, buttonsLeaf.getListButtonInline());
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(217), buttonsLeaf.getListButtonInline()));
//    }
//    public void showCategory() throws TelegramApiException{
//         String info = "UNDEFINED";
//         if(foodSubCategory.getFoodCategory()!=null){
//             info = foodSubCategory.getFoodCategory().getCategoryNameRu();
//         }
//
//        if(foodSubCategory.getInline()){
//            editMessageWithKeyboard(String.format(getText(66), foodSubCategory.getSubCategoryNameKaz(), foodSubCategory.getSubCategoryNameRu(), foodSubCategory.getSubCategoryDescriptionKaz(), foodSubCategory.getSubCategoryDescriptionRu(),info),updateMessageId ,94);
//        }
//         else{
//             editMessageWithKeyboard(String.format(getText(66), foodSubCategory.getSubCategoryNameKaz(), foodSubCategory.getSubCategoryNameRu(), foodSubCategory.getSubCategoryDescriptionKaz(), foodSubCategory.getSubCategoryDescriptionRu(),info),updateMessageId ,37);
//         }
//    }
//    public void showCategorySend() throws TelegramApiException{
//        if(!foodSubCategory.getInline()){
//            sendMessageWithKeyboard(String.format(getText(66), foodSubCategory.getSubCategoryNameKaz(), foodSubCategory.getSubCategoryNameRu(), foodSubCategory.getSubCategoryDescriptionKaz(), foodSubCategory.getSubCategoryDescriptionRu(), foodSubCategory.getFoodCategory().getName(getLanguageId())) ,37);
//        }
//        else{
//            sendMessageWithKeyboard(String.format(getText(66), foodSubCategory.getSubCategoryNameKaz(), foodSubCategory.getSubCategoryNameRu(), foodSubCategory.getSubCategoryDescriptionKaz(), foodSubCategory.getSubCategoryDescriptionRu(),"inline") ,37);
//
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
//    public int showAllCategories() throws TelegramApiException {
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        foodCategories.forEach((e) -> {
//            finalList.add(e.getName(getLanguageId()));
//            finalList2.add(String.valueOf(e.getId()));
//        });
//
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10);
////        editMessageWithKeyboard(getText(57), updateMessageId, buttonsLeaf.getListButtonInline());
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(9), buttonsLeaf.getListButtonInline()));
//    }
//}
