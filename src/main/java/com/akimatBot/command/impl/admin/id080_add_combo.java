//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.FoodCategory;
//import com.akimatBot.entity.custom.FoodSubCategoryEntity;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.FoodCategoryRepo;
//import com.akimatBot.repository.repos.FoodRepository;
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
//public class id080_add_combo extends Command {
//    private final FoodRepository foodRepository = TelegramBotRepositoryProvider.getFoodRepository();
//    private final FoodCategoryRepo foodCategoryRepo = TelegramBotRepositoryProvider.getFoodCategoryRepo();
//    private final FoodSubCategoryRepo foodSubCategoryRepo = TelegramBotRepositoryProvider.getFoodSubCategoryRepo();
//    private Food food;
//    private FoodCategory foodCategory;
//    private ButtonsLeaf buttonsLeaf;
//    private List<String> finalList;
//    private List<String> finalList2;
//    private List<FoodSubCategoryEntity> foodSubCategories;
//    private List<FoodCategory> foodCategories;
//    private WaitingType editWaitingType;
//    private FoodSubCategoryEntity foodSubCategory;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        if (!isAdmin()) {
//            sendMessage(Const.NO_ACCESS);
//            return EXIT;
//        }
//        switch (waitingType) {
//            case START:
//                food = new Food();
//                food.setFoodCategory(foodSubCategoryRepo.findFoodSubCategoryEntityById(17));
//                getName();
//                waitingType = WaitingType.GET_NAME;
//                return COMEBACK;
//            case GET_NAME:
//                if (update.hasMessage() && update.getMessage().hasText()) {
//                    food.setNameRu(update.getMessage().getText());
//                    getDescription();
//                    waitingType = WaitingType.GET_DESCRIPTION;
//                } else {
//                    wrongData();
//                    getName();
//                }
//                return COMEBACK;
//            case GET_DESCRIPTION:
//                if (update.hasMessage() && update.getMessage().hasText()) {
//                    food.setDescriptionRu(update.getMessage().getText());
//                    getPrice();
//                    waitingType = WaitingType.GET_PRICE;
//                } else {
//                    wrongData();
//                    getDescription();
//                }
//                return COMEBACK;
//            case GET_PRICE:
//                if (update.hasMessage() && update.getMessage().hasText() && isNumeric(update.getMessage().getText())) {
//                    food.setPrice(Integer.valueOf(update.getMessage().getText()));
//                    getPhoto();
//                    waitingType = WaitingType.GET_PHOTO;
//                } else {
//                    wrongData();
//                    getPrice();
//                }
//                return COMEBACK;
//            case GET_PHOTO:
//                if (update.hasMessage() && update.getMessage().hasPhoto()) {
//                    //System.out.println("This is updateMessagePhoto - "+updateMessagePhoto);
//                    food.setPhoto_url(updateMessagePhoto);
//                    food = foodRepository.save(food);
//                    showFood();
//                    waitingType = WaitingType.SHOW_FOOD;
//                } else {
//                    wrongData();
//                    getPhoto();
//                }
//                return COMEBACK;
//
//            case SHOW_FOOD:
//                if (hasCallbackQuery()) {
//                    if (isButton(82)) {
//                        System.out.println("Редактировать имя");
//                        getName();
//                        editWaitingType = WaitingType.EDIT_NAME;
//                    } else if (isButton(83)) {
//                        getDescription();
//                        System.out.println("Редактировать описание");
//                        editWaitingType = WaitingType.EDIT_DESCRIPTION;
//                    } else if (isButton(84)) {
//                        getPrice();
//                        System.out.println("Редактировать цену");
//                        editWaitingType = WaitingType.EDIT_PRICE;
//                    } else if (isButton(85)) {
//                        System.out.println("Удалить");
//                        editWaitingType = WaitingType.DELETE;
//                    } else if (isButton(86)) {
//                        getPhoto();
//                        System.out.println("Редактировать фото");
//                        editWaitingType = WaitingType.EDIT_PHOTO;
//                    } else if (isButton(87)) {
//                        System.out.println("ЗАВЕРШИТЬ");
//                        foodRepository.save(food);
//                        sendMessage(getText(Const.FOOD_SUCCESSFULLY_CREATED));
//                        waitingType = WaitingType.FINISH;
//                        return EXIT;
//                    } else if (isButton(88)) {
//                        System.out.println("Редактировать суб-категорию");
//                        foodCategories = foodCategoryRepo.findAll();
//                        showAllCategories();
//                        editWaitingType = WaitingType.EDIT_CATEGORY;
//                    }
//                    waitingType = WaitingType.EDIT_FOOD;
//                } else {
//                    wrongData();
//                    showFood();
//                }
//
//                return COMEBACK;
//            case EDIT_FOOD:
//                switch (editWaitingType) {
//                    case EDIT_NAME:
//                        //confirm(update.getMessage().getText());
//                        food.setNameRu(update.getMessage().getText());
//                        break;
//                    case EDIT_DESCRIPTION:
//                        food.setDescriptionRu(update.getMessage().getText());
//                        break;
//                    case EDIT_PRICE:
//                        if (isNumeric(update.getMessage().getText())) {
//                            food.setPrice(Integer.parseInt(update.getMessage().getText()));
//                        } else {
//                            wrongData();
//                            //getPrice();
//                            waitingType = WaitingType.EDIT_FOOD;
//                            return COMEBACK;
//                        }
//                        break;
//                    case EDIT_CATEGORY:
//                        foodCategory = foodCategories.stream()
//                                .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                                .findAny()
//                                .orElse(null);
//                        foodSubCategories = foodSubCategoryRepo.findAllByFoodCategory(foodCategory);
//                        showAllSubCategories();
//                        waitingType = WaitingType.EDIT_CATEGORY2;
//                        return COMEBACK;
//                    case EDIT_PHOTO:
//                        if (update.getMessage().hasPhoto()) {
//                            food.setPhoto_url(updateMessagePhoto);
//                        } else {
//                            wrongData();
//                            getPhoto();
//                            waitingType = WaitingType.EDIT_FOOD;
//                            return COMEBACK;
//                        }
//                        break;
//
//                }
//                showFood();
//                waitingType = WaitingType.SHOW_FOOD;
//                return COMEBACK;
//
//            case EDIT_CATEGORY2:
//                if (hasCallbackQuery()) {
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        editMessageWithKeyboard(getText(48), updateMessageId, buttonsLeaf.getListButtonInline());
//                    }
//                    foodSubCategory = foodSubCategories.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//                    food.setFoodCategory(foodSubCategory);
//                } else {
//                    wrongData();
//                    showAllSubCategories();
//                }
//                showFood();
//                waitingType = WaitingType.SHOW_FOOD;
//                return COMEBACK;
//
//
//        }
//        return false;
//    }
//
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
//
//    public int confirm(String text) throws TelegramApiException {
//        return sendMessageWithKeyboard(String.format(getText(43), text), 16);
//    }
//
//    public int getDescription() throws TelegramApiException {
//        return sendMessage(getText(Const.GET_FOOD_DESCRIPTION));
//    }
//
//    public int creationFinish() throws TelegramApiException {
//        return sendMessage(getText(Const.FOOD_SUCCESSFULLY_CREATED));
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
//    public int getPrice() throws TelegramApiException {
//        return sendMessage(getText(Const.GET_FOOD_PRICE));
//    }
//
//    public int showFood() throws TelegramApiException {
////        editMessageMedia(String.format(getText(47), food.getFoodName(), food.getFoodDescription(), food.getFoodPrice(), foodCategory.getName(getLanguageId()), food.getFoodSubCategory().getName(getLanguageId())), 31, food.getPhoto_url(),chatId,updateMessageId);
//        return sendMessageWithPhotoAndKeyboard(String.format(getText(196), food.getNameRu(), food.getDescriptionRu(), food.getPrice(), food.getFoodCategory().getName(getLanguageId())), 31, food.getPhoto_url(), chatId);
//    }
//
//    public void showAllSubCategories() throws TelegramApiException {
//        List<String> list = new ArrayList<>();
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        foodSubCategories.forEach((e) -> {
//            finalList.add(e.getName(getLanguageId()));
//            finalList2.add(String.valueOf(e.getId()));
//        });
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10, 2);
//        editMessageWithKeyboard(getText(48), updateMessageId, buttonsLeaf.getListButtonInline());
////        return toDeleteKeyboard(sendMessageWithKeyboard(getText(11), buttonsLeaf.getListButtonInline()));
//    }
//
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
////        editMessageWithKeyboard(getText(9), updateMessageId ,buttonsLeaf.getListButtonInline());
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(11), buttonsLeaf.getListButtonInline()));
//    }
//
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//
//}
