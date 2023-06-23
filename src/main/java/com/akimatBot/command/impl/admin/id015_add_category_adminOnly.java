//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.FoodCategory;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.FoodCategoryRepo;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class id015_add_category_adminOnly extends Command {
//    private final FoodCategoryRepo foodCategoryRepo = TelegramBotRepositoryProvider.getFoodCategoryRepo();
//    private FoodCategory foodCategory;
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
//                getCategoryNameRu();
//                foodCategory = new FoodCategory();
//                waitingType = WaitingType.GET_NAME_RU;
//                return COMEBACK;
//            case GET_NAME_RU:
//                deleteMessage(updateMessageId);
//                if(update.hasMessage() && update.getMessage().hasText()){
//                    foodCategory.setCategoryNameRu(update.getMessage().getText());
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
//                    foodCategory.setCategoryNameKaz(update.getMessage().getText());
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
//                    foodCategory.setCategoryDescriptionRu(update.getMessage().getText());
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
//                    foodCategory.setCategoryDescriptionKaz(update.getMessage().getText());
//
//                    showCategory();
//                    waitingType = WaitingType.SHOW_CATEGORY;
//                }
//                else{
//                    wrongData();
//                    getCategoryDescriptionKz();
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
//                    else if(isButton(87)){
//                        System.out.println("Завершить");
//                        foodCategoryRepo.save(foodCategory);
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
//        }
//        return EXIT;
//    }
//
//    public int showCategory() throws TelegramApiException{
//        return sendMessageWithKeyboard(String.format(getText(53), foodCategory.getCategoryNameKaz(), foodCategory.getCategoryNameRu(), foodCategory.getCategoryDescriptionKaz(), foodCategory.getCategoryDescriptionRu()), 33);
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
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
//}
