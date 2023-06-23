//package com.akimatBot.utils;
//
//import com.akimatBot.entity.custom.Food;
//import com.akimatBot.entity.custom.FoodSubCategoryEntity;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.FoodRepository;
//import com.akimatBot.repository.repos.UserRepository;
//import com.akimatBot.services.KeyboardMarkUpService;
//import com.akimatBot.services.LanguageService;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//@Data
//public class ButtonsLeafChanged {
//    private final String back = "\uD83D\uDD19";
//    List<Food> keys;
//    @Getter
//    private boolean isCartEmpty = false;
//
//    @Getter
//    private long countOfFood;
//
//    @Getter
//    private Map<Food, Long> foodMap;
//    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
//    private final FoodRepository foodRepository = TelegramBotRepositoryProvider.getFoodRepository();
//    @Setter
//    @Getter
//    private User user;
//    @Getter
//    @Setter
//    private Food currentFood;
//    private List<Food> userFoods;
//    @Setter
//    private List<Food> foods;
//    @Getter
//    @Setter
//    private List<String> allNamesButtonList;
//    @Getter
//    @Setter
//    private List<String> ids;
//    private int          indexCurrentButton;
//    @Getter
//    @Setter
//    private int          page             = 1;
//
//    private int          countColumn      = 1;
//    private int          countButtons     = 15;
//    @Getter
//    private String       left             = "<<";
//    @Getter
//    private String       right            = ">>";
//    private String       destroy = "❌";
////    private String add = "\uD83D\uDD3A";
////    private String minus = "\uD83D\uDD3B";
//    private String add = "➕";
//    private String minus = "➖";
//    private boolean      isHorizonSort    = false;
//    private boolean      isAddNextButtons = false;
//    private TypeKeyboard typeKeyboard     = TypeKeyboard.INLINE;
//    private int addKeyboardId = 0;
//    private long chatId = 0;
//    private KeyboardMarkUpService keyboardMarkUpService = new KeyboardMarkUpService();
//
//    @Getter
//    @Setter
//    public InlineKeyboardMarkup keyboardMarkup;
//
//    public static <T>ArrayList<T> removeDuplicates(List<T> list)
//    {
//        ArrayList<T> newList = new ArrayList<T>();
//        for (T element : list) {
//            if (!newList.contains(element)) {
//                newList.add(element);
//            }
//        }
//        return newList;
//    }
//
//
//
//    public void setFoodMap(Map<Food, Long> foodMap) {
//        this.foodMap = foodMap;
//        keys = new ArrayList<>(foodMap.keySet());
//
//    }
//
//    public ButtonsLeafChanged(List<String> allNamesButtonList) {
//        this.allNamesButtonList = allNamesButtonList;
//        this.countButtons       = allNamesButtonList.size();
//    }
//    public                                      ButtonsLeafChanged(List<String> allNamesButtonList, List<String> ids, int countButtons, int countColumn, int addKeyboardId, long chatId) {
//        this.allNamesButtonList = allNamesButtonList;
//        this.countButtons = Math.min(allNamesButtonList.size(), countButtons);
//        this.countColumn = Math.min(allNamesButtonList.size(), countColumn);
//        this.ids = ids;
//        this.addKeyboardId = addKeyboardId;
//        this.chatId = chatId;
//    }
//    public ButtonsLeafChanged(List<String> allNamesButtonList, List<String> ids, int countButtons) {
//        this.allNamesButtonList = allNamesButtonList;
//        this.countButtons = Math.min(allNamesButtonList.size(), countButtons);
//        this.ids = ids;
//    }
//    public ButtonsLeafChanged(List<String> allNamesButtonList, List<String> ids, int countButtons, int countColumn) {
//        this.allNamesButtonList = allNamesButtonList;
//        this.countButtons = Math.min(allNamesButtonList.size(), countButtons);
//        this.ids = ids;
//        this.countColumn = countColumn;
//    }
//    public ButtonsLeafChanged(List<String> allNamesButtonList, List<String> ids) {
//        this.allNamesButtonList = allNamesButtonList;
//        this.countButtons       = allNamesButtonList.size();
//        this.ids = ids;
//    }
//
//    public ButtonsLeafChanged(List<String> allNamesButtonList, int countButtons, String left, String right) {
//        this.allNamesButtonList = allNamesButtonList;
//        if (allNamesButtonList.size() > countButtons) {
//            this.countButtons   = countButtons;
//        } else {
//            this.countButtons   = allNamesButtonList.size();
//        }
//        this.left               = left;
//        this.right              = right;
//    }
//    public ButtonsLeafChanged(List<String> allNamesButtonList, int countButtons) {
//        this.allNamesButtonList = allNamesButtonList;
//        this.countButtons = Math.min(allNamesButtonList.size(), countButtons);
//    }
//
//    public ButtonsLeafChanged() {
//    }
//
//    public void                                 setAddNextButtons(boolean addNextButtons) { isAddNextButtons = addNextButtons; }
//
//    public ReplyKeyboard getListButton() {
//        indexCurrentButton                  = (page - 1) * countButtons;
//        List<String> currentButtonNames     = new ArrayList<>();
//        List<String> callbackDataButtons    = new ArrayList<>();
//        try {
//            for (int i = 0; i < countButtons; i++) {
//                currentButtonNames.add(allNamesButtonList.get(indexCurrentButton));
//                callbackDataButtons.add(String.valueOf(indexCurrentButton));
//                indexCurrentButton++;
//            }
//        } catch (Exception e) {}
//        if (typeKeyboard == TypeKeyboard.REPLY) {
//            if (countButtons >= allNamesButtonList.size()) return getReplyKeyboard(currentButtonNames);
//            return addButtonLeaf(getReplyKeyboard(currentButtonNames));
//        }
//        if (countButtons >= allNamesButtonList.size() && !isAddNextButtons) return getInlineKeyboard(currentButtonNames, callbackDataButtons);
//        return addButtonLeaf(getInlineKeyboard(currentButtonNames, callbackDataButtons));
//    }
//
//    public InlineKeyboardMarkup  getListButtonInline() {
////        indexCurrentButton                  = (getCurrentPage() - 1) * countButtons;
//
//        indexCurrentButton = 0;
//        List<String> currentButtonNames     = new ArrayList<>();
//        List<String> callbackDataButtons    = new ArrayList<>();
//        try {
//            for (int i = 0; i < countButtons; i++) {
//                currentButtonNames.add(allNamesButtonList.get(indexCurrentButton));
//                callbackDataButtons.add(ids.get(indexCurrentButton));
//                indexCurrentButton++;
//            }
//        }
//        catch (Exception e) {}
//
//        if (countButtons >= allNamesButtonList.size() && !isAddNextButtons)
//            return getInlineKeyboard(currentButtonNames, callbackDataButtons);
//        return addButtonLeaf(getInlineKeyboard(currentButtonNames, callbackDataButtons));
//    }
//    public InlineKeyboardMarkup  getListButtonInlineMenu() {
//        indexCurrentButton                  = (page - 1) * countButtons;
//        List<String> currentButtonNames     = new ArrayList<>();
//        List<String> callbackDataButtons    = new ArrayList<>();
//        try {
//            for (int i = 0; i < countButtons; i++) {
//                currentButtonNames.add(allNamesButtonList.get(indexCurrentButton));
//                callbackDataButtons.add(ids.get(indexCurrentButton));
//                indexCurrentButton++;
//            }
//        }
//        catch (Exception e) {}
//
//        if (countButtons >= allNamesButtonList.size() && !isAddNextButtons)
//            return getInlineKeyboard(currentButtonNames, callbackDataButtons);
//        return addButtonLeafForNavigation(getInlineKeyboard(currentButtonNames, callbackDataButtons));
//    }
//
//    public ReplyKeyboard                        getListButtonWhereDataIsName() {
//        indexCurrentButton                  = (page - 1) * countButtons;
//        List<String> currentButtonNames     = new ArrayList<>();
//        List<String> callbackDataButtons    = new ArrayList<>();
//        try {
//            for (int i = 0; i < countButtons; i++) {
//                currentButtonNames.add(allNamesButtonList.get(indexCurrentButton));
//                callbackDataButtons.add(allNamesButtonList.get(indexCurrentButton));
//                indexCurrentButton++;
//            }
//        } catch (Exception e) {}
//        if (typeKeyboard == TypeKeyboard.REPLY) {
//            if (countButtons >= allNamesButtonList.size()) return getReplyKeyboard(currentButtonNames);
//            return addButtonLeaf(getReplyKeyboard(currentButtonNames));
//        }
//        if (countButtons >= allNamesButtonList.size() && !isAddNextButtons) return getInlineKeyboard(currentButtonNames, callbackDataButtons);
//        return addButtonLeaf(getInlineKeyboard(currentButtonNames, callbackDataButtons));
//    }
//
//    public ReplyKeyboard                        getListButtonUrl() {
//        indexCurrentButton                  = (page - 1) * countButtons;
//        List<String> currentButtonNames     = new ArrayList<>();
//        List<String> callbackDataButtons    = new ArrayList<>();
//        List<String> curUrls                = new ArrayList<>();
//        try {
//            for (int i = 0; i < countButtons; i++) {
//                currentButtonNames.add(allNamesButtonList.get(indexCurrentButton));
//                callbackDataButtons.add(String.valueOf(indexCurrentButton));
//                indexCurrentButton++;
//            }
//        } catch (Exception e) {}
//        if (typeKeyboard == TypeKeyboard.REPLY) {
//            if (countButtons >= allNamesButtonList.size()) return getReplyKeyboard(currentButtonNames);
//            return addButtonLeaf(getReplyKeyboard(currentButtonNames));
//        }
//        if (countButtons >= allNamesButtonList.size() && !isAddNextButtons) return getInlineKeyboard(currentButtonNames, callbackDataButtons);
//        return addButtonLeaf(getInlineKeyboard(currentButtonNames, callbackDataButtons));
//    }
//
//    public boolean isReveal(String updateMessageText){
//        return updateMessageText.equals(String.valueOf("$"));
//    }
////    public InlineKeyboardMarkup isReveal(String updateMessageText){
////        if(updateMessageText.equals(String.valueOf(page))){
////            FoodSubCategoryEntity foodSubCategory = currentFood.getFoodSubCategory();
////            List<Food> foodsInCategory = foodRepository.findFoodsByFoodSubCategory(foodSubCategory);
////            List<String> list = new ArrayList<>();
////            List<String> finalList = new ArrayList<>();
////            List<String> finalList2 = new ArrayList<>();
////            foodsInCategory.forEach((e) -> {
////                finalList.add(e.getFoodName());
////                finalList2.add(String.valueOf(e.getId()));
////            });
////
////            ButtonsLeaf buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10, 2);
////            return buttonsLeaf.getListButtonInline();
////
////        }
////        return addButtonLeafForNavigation();
////    }
//    public boolean isBack(String updateMessageText){
//        if(updateMessageText.equals(back)){
//
//            page=getCurrentPage();
//            return true;
//        }
//        return false;
//    }
//    public boolean                              isNext(String updateMessageText) {
//
//        if (updateMessageText.equals(left)) {
//            for (int i = 0; i < foods.size(); i++) {
//                if(foods.get(i).getId() == currentFood.getId()){
//                    if (i == 0) {
//                        currentFood = foods.get(foods.size() - 1);
//                        page = foods.size();
//                    }
//                    else {
//                        currentFood = foods.get(i - 1);
//                        page = i;
//                    }
//                    break;
//
//                }
//
//            }
//
//
////            page--;
////
////            if (page < 1) page = countPageFood();
//
//            return true;
//
//
////            if (page < 1) page = countPageFood();
////
////            currentFood = foods.get(page - 1);
////            return true;
//        }
//        else if (updateMessageText.equals(right)) {
//            for (int i = 0; i < foods.size(); i++) {
//                if(foods.get(i).getId() == currentFood.getId()){
//                    if (i == foods.size() -1) {
//                        currentFood = foods.get(0);
//                        page = 1;
//
//                    }
//                    else {
//                        currentFood = foods.get(i + 1);
//                        page = i+2;
//                    }
//                    break;
//                }
//
//            }
//            return true;
//        }
//        else if(updateMessageText.equals(add)){
//            if(user.getFoods().isEmpty()){
//                user.setFoods(new ArrayList<>());
//            }
//            //currentFood = foods.get(page - 1);
//            user.getFoods().add(currentFood);
//            userRepository.save(user);
//            page = getCurrentPage();
//            return true;
//        }
//        else if(updateMessageText.equals(minus)){
//            if(user.getFoods().isEmpty()){
//                user.setFoods(new ArrayList<>());
//            }
//            //currentFood = foods.get(page - 1);
//            user.removeFood(currentFood);
////            user.getFoods().remove(currentFood);
//            userRepository.save(user);
//            page = getCurrentPage();
//            return true;
//        }
//        else if(updateMessageText.equals(back)){
//            page = 1;
//            return false;
//        }
//        else if(updateMessageText.equals(destroy)){
//            //currentFood = foods.get(page - 1);
////            userFoods = user.getFoods();
////            removeAll(user.getFoods(), currentFood);
//            removeContainsId(user.getFoods(), currentFood.getId());
//            page = getCurrentPage();
//            userRepository.save(user);
//            return true;
//        }
//        else if(updateMessageText.equals(String.valueOf(page)) )return true;
//        return false;
//    }
//
//
//    public boolean                              isNextOriginal(String updateMessageText) {
//        if (updateMessageText.equals(left)) {
//            page--;
//            if (page < 1) page = countPage();
//            return true;
//        } else if (updateMessageText.equals(right)) {
//            page++;
//            if (page > countPage()) page = 1;
//            return true;
//        }
//
//        return false;
//    }
//
//    public int getCurrentPage() {
//        for (int i = 0; i < foods.size(); i++) {
//            if (currentFood.getId() == foods.get(i).getId())
//                return i+1;
//        }
//        return 0;
//    }
//
//    public int getCurrentPage(List<Food> foods) {
//        for (int i = 0; i < foods.size(); i++) {
//            if (currentFood.getId() == foods.get(i).getId())
//                return i+1;
//        }
//        return 0;
//    }
//    public boolean                              isNextCart(String updateMessageText) {
////        countOfFood = foodMap.get(currentFood);
////        if (updateMessageText.equals(left)) {
////            page--;
////            if (page < 1) page = countPageFoodMap();
////            currentFood = keys.get(page-1);
////            return true;
////        }
////        else if (updateMessageText.equals(right)) {
////            page++;
////            if (page > countPageFoodMap()) page = 1;
////            currentFood = keys.get(page-1);
////            return true;
////        }
//        foods = removeDuplicates(foods);
//        page = getCurrentPage();
//        if (updateMessageText.equals(left)) {
//            for (int i = 0; i < foods.size(); i++) {
//                if(foods.get(i).getId() == currentFood.getId()){
//                    if (i == 0) {
//                        currentFood = foods.get(foods.size() - 1);
//                        page = foods.size();
//                    }
//                    else {
//                        currentFood = foods.get(i - 1);
//                        page = i;
//                    }
//                    break;
//
//                }
//
//            }
//
//
//            return true;
//
//        }
//        else if (updateMessageText.equals(right)) {
//            for (int i = 0; i < foods.size(); i++) {
//                if(foods.get(i).getId() == currentFood.getId()){
//                    if (i == foods.size() -1) {
//                        currentFood = foods.get(0);
//                        page = 1;
//
//                    }
//                    else {
//                        currentFood = foods.get(i + 1);
//                        page = i+2;
//                    }
//                    break;
//                }
//
//            }
//            return true;
//        }
//        else if(updateMessageText.equals(add)){
//            currentFood = keys.get(page - 1);
//            countOfFood = foodMap.get(currentFood);
//            countOfFood++;
//            foodMap.put(currentFood, countOfFood);
//            page = getCurrentPage();
//            user.getFoods().add(currentFood);
////            userFoods.add(currentFood);
////            user.getFoods().add(currentFood);
//            userRepository.save(user);
//            return true;
//        }
//        else if(updateMessageText.equals(minus)){
//            currentFood = keys.get(page - 1);
//            countOfFood = foodMap.get(currentFood);
//            countOfFood--;
//            if (countOfFood < 0 || countOfFood==0) {
//                countOfFood = 0;
//                foodMap.remove(currentFood);
//
//            }
//
//            else{
//                foodMap.put(currentFood, countOfFood);
//            }
//
//            user.getFoods().remove(currentFood);
//            page = getCurrentPage();
//
//
//
//            if(user.getFoods().isEmpty()||user.getFoods()==null){
//                isCartEmpty = true;
//                userRepository.save(user);
//                return true;
//            }
//            keys = new ArrayList<>(foodMap.keySet());
//            currentFood = keys.get(page-1);
////            userFoods.remove(currentFood);
////            user.getFoods().remove(currentFood);
//            userRepository.save(user);
//            return true;
//        }
//        else if(updateMessageText.equals(destroy)){
//            currentFood = keys.get(page - 1);
//
//            foodMap.remove(currentFood);
//            removeContainsId(user.getFoods(), currentFood.getId());
////            removeAll(user.getFoods(), currentFood);
//
//            if(foodMap.isEmpty() || user.getFoods().isEmpty()){
//                isCartEmpty = true;
//                userRepository.save(user);
//                return true;
//            }
//            page = 1;
//            keys = new ArrayList<>(foodMap.keySet());
//            currentFood = keys.get(0);
//            userRepository.save(user);
//            return true;
//        }
//
//        else if(updateMessageText.equals(String.valueOf(page)) )return true;
//        return false;
//    }
//
//    void removeAll(List<Food> list, Food element) {
//        while (list.contains(element)) {
//            list.remove(element);
//        }
//    }
//    void removeContainsId(List<Food> list, long id){
//        list.removeIf(o -> o.getId() == id);
//    }
//    private ReplyKeyboardMarkup                 getReplyKeyboard(List<String> namesButton) {
//        ReplyKeyboardMarkup keyboard        = new ReplyKeyboardMarkup();
//        keyboard.setResizeKeyboard(true);
//        List<KeyboardRow> keyboardRowList   = new ArrayList<>();
//        String buttonIdsString;
//        for (int i = 0; i < namesButton.size(); i++) {
//            KeyboardRow keyboardRow         = new KeyboardRow();
//            for (int j = 0; j < countColumn; j++) {
//                buttonIdsString             = namesButton.get(i);
//                KeyboardButton button       = new KeyboardButton();
//                button.setText(buttonIdsString);
//                keyboardRow.add(button);
//                if (countColumn > 1 && j != countColumn - 1) i++;
//            }
//            keyboardRowList.add(keyboardRow);
//        }
//        keyboard.setKeyboard(keyboardRowList);
//        return keyboard;
//    }
//
//    private ReplyKeyboardMarkup                 addButtonLeaf(ReplyKeyboardMarkup keyboardMarkup) {
//        KeyboardRow keyboardRow     = new KeyboardRow();
//        KeyboardButton leftButton   = new KeyboardButton();
//        leftButton.setText(left);
//        KeyboardButton rightButton  = new KeyboardButton();
//        rightButton.setText(right);
//        keyboardRow.add(leftButton);
//        keyboardRow.add(rightButton);
//        keyboardMarkup.getKeyboard().add(keyboardRow);
//        return keyboardMarkup;
//    }
//    public InlineKeyboardMarkup addButtonLeafForNavigation(InlineKeyboardMarkup keyboardMarkup){
////        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        keyboardMarkup = new InlineKeyboardMarkup();
////        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<InlineKeyboardButton> keyboardRow    = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardRow2    = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardRow3    = new ArrayList<>();
//        List<InlineKeyboardButton> toCartRow    = new ArrayList<>();
//
//
//
//        InlineKeyboardButton addButton   = new InlineKeyboardButton();
//        addButton.setText(add);
//        //addButton.setCallbackData(add);
//        addButton.setCallbackData("40,"+currentFood.getId()+","+add);
//
//        InlineKeyboardButton minusButton  = new InlineKeyboardButton();
//        minusButton.setText(minus);
//        //minusButton.setCallbackData(minus);
//        minusButton.setCallbackData("40,"+currentFood.getId()+","+minus);
//        InlineKeyboardButton destroyButton   = new InlineKeyboardButton();
//        destroyButton.setText(destroy);
//        destroyButton.setCallbackData("40,"+currentFood.getId()+","+destroy);
//
////        currentFood = foods.get(page - 1);
//
//        InlineKeyboardButton leftButton   = new InlineKeyboardButton();
//        leftButton.setText(left);
//        leftButton.setCallbackData("40,"+currentFood.getId()+","+left);
//        InlineKeyboardButton rightButton  = new InlineKeyboardButton();
//        rightButton.setText(right);
//        rightButton.setCallbackData("40,"+currentFood.getId()+","+right);
//
//        InlineKeyboardButton foodCount = new InlineKeyboardButton();
//
//
//        String toCart =TelegramBotRepositoryProvider.getMessageRepository().findByIdAndLangId(131, LanguageService.getLanguage(user.getChatId()).getId()).getName();
//
//        InlineKeyboardButton toCartButton   = new InlineKeyboardButton();
//        toCartButton.setText(toCart);
//        toCartButton.setCallbackData("40,"+currentFood.getId()+","+add);
//
//
//
////        int countFood = Collections.frequency(user.getFoods(), currentFood);
//        int countFood = countContainsId(user.getFoods(), currentFood.getId());
//        foodCount.setText(String.valueOf(countFood)+"шт");
//        foodCount.setCallbackData("40,"+String.valueOf(page)+",-");
//
//        InlineKeyboardButton numberPage  = new InlineKeyboardButton();
//        numberPage.setText(String.valueOf(page));
//        numberPage.setCallbackData("40,"+String.valueOf(page)+",-"+currentFood.getFoodCategory().getId());
//
//
//        InlineKeyboardButton backButton  = new InlineKeyboardButton();
//        backButton.setText(getText(199)+back);
//        backButton.setCallbackData("38,"+currentFood.getFoodCategory().getFoodCategory().getId()+","+back);
//
//
//
//        keyboardRow.add(destroyButton);
//        keyboardRow.add(minusButton);
//        keyboardRow.add(foodCount);
//        keyboardRow.add(addButton);
//        keyboardRow2.add(leftButton);
//        keyboardRow2.add(getIButton(String.valueOf(page)+"/"+countPageFood(), "40,"+String.valueOf(page)+",-"+currentFood.getFoodCategory().getId()));
//        keyboardRow2.add(rightButton);
//        keyboardRow3.add(backButton);//
//        toCartRow.add(toCartButton);
//
//
//        if(user.getFoods()!=null && !user.getFoods().isEmpty()){
//            //int temp = countContainsIdBySubCategory(user.getFoods(), currentFood.getFoodSubCategory().getId());
//            int temp = countContainsId(user.getFoods(), currentFood.getId());
//            if(temp>0) keyboardMarkup.getKeyboard().add(keyboardRow);
//            else{
//                keyboardMarkup.getKeyboard().add(toCartRow);
//            }
//        }
//        else{
//            keyboardMarkup.getKeyboard().add(toCartRow);
//        }
//        keyboardMarkup.getKeyboard().add(keyboardRow2);
//        keyboardMarkup.getKeyboard().add(keyboardRow3);
//        return keyboardMarkup;
//    }
//    protected String  getText(int messageIdFromDb) { return TelegramBotRepositoryProvider.getMessageRepository().findByIdAndLangId(messageIdFromDb, LanguageService.getLanguage(user.getChatId()).getId()).getName(); }
//
//    public int countContainsId(final List<Food> list, final long id) {
//        //list.removeIf(o -> o.getId() == id);
//        return (int) list.stream().filter(o -> o.getId() == id).count();
//        //return list.stream().anyMatch(o -> o.getId() == id);
//    }
//    public int countContainsIdBySubCategory(final List<Food> list, final long category_id) {
//        //list.removeIf(o -> o.getId() == id);
//        return (int) list.stream().filter(o -> o.getFoodCategory().getId() == category_id).count();
//        //return list.stream().anyMatch(o -> o.getId() == id);
//    }
//    public InlineKeyboardMarkup addButtonLeafForNavigation(){
//        keyboardMarkup = new InlineKeyboardMarkup();
////        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<InlineKeyboardButton> keyboardRow    = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardRow2    = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardRow3    = new ArrayList<>();
//        List<InlineKeyboardButton> toCartRow    = new ArrayList<>();
//        List<InlineKeyboardButton> checkoutRow    = new ArrayList<>();
//
//        InlineKeyboardButton addButton   = new InlineKeyboardButton();
//        addButton.setText(add);
//        //addButton.setCallbackData(add);
//        addButton.setCallbackData("40,"+currentFood.getId()+","+add);
//
//        InlineKeyboardButton minusButton  = new InlineKeyboardButton();
//        minusButton.setText(minus);
//        //minusButton.setCallbackData(minus);
//        minusButton.setCallbackData("40,"+currentFood.getId()+","+minus);
//        InlineKeyboardButton destroyButton   = new InlineKeyboardButton();
//        destroyButton.setText(destroy);
//        destroyButton.setCallbackData("40,"+currentFood.getId()+","+destroy);
//
////        currentFood = foods.get(page - 1);
//
//        InlineKeyboardButton leftButton   = new InlineKeyboardButton();
//        leftButton.setText(left);
//        leftButton.setCallbackData("40,"+currentFood.getId()+","+left);
//        InlineKeyboardButton rightButton  = new InlineKeyboardButton();
//        rightButton.setText(right);
//        rightButton.setCallbackData("40,"+currentFood.getId()+","+right);
//
//        InlineKeyboardButton foodCount = new InlineKeyboardButton();
//
//
//        String toCart =TelegramBotRepositoryProvider.getMessageRepository().findByIdAndLangId(131, LanguageService.getLanguage(user.getChatId()).getId()).getName();
//
//        InlineKeyboardButton toCartButton   = new InlineKeyboardButton();
//        toCartButton.setText(toCart);
//        toCartButton.setCallbackData("40,"+currentFood.getId()+","+add);
//
//        String checkout = TelegramBotRepositoryProvider.getMessageRepository().findByIdAndLangId(132, LanguageService.getLanguage(user.getChatId()).getId()).getName();
//
//        InlineKeyboardButton checkoutButton   = new InlineKeyboardButton();
//        checkoutButton.setText(checkout);
//        checkoutButton.setCallbackData("40,"+currentFood.getId()+",finish");
//
////        int countFood = Collections.frequency(user.getFoods(), currentFood);
//        int countFood = countContainsId(user.getFoods(), currentFood.getId());
//        foodCount.setText(String.valueOf(countFood)+"шт");
//        foodCount.setCallbackData("40,"+String.valueOf(page)+",-");
//
//        InlineKeyboardButton numberPage  = new InlineKeyboardButton();
//        numberPage.setText(String.valueOf(page));
//        numberPage.setCallbackData("40,"+currentFood.getId()+",$"+currentFood.getFoodCategory().getId());
//
//
//        InlineKeyboardButton backButton  = new InlineKeyboardButton();
//        backButton.setText(getText(199)+back);
//        if(currentFood.getFoodCategory().getFoodCategory()!=null) {
//            backButton.setCallbackData("38,"+currentFood.getFoodCategory().getFoodCategory().getId()+","+back);
//            //todo
//        }
//        else{
//            backButton.setCallbackData("37,"+back);
//
//        }
//
//
//
//        keyboardRow.add(destroyButton);
//        keyboardRow.add(minusButton);
//        keyboardRow.add(foodCount);
//        keyboardRow.add(addButton);
//        keyboardRow2.add(leftButton);
//        keyboardRow2.add(getIButton(String.valueOf(page)+"/"+countPageFood(), "41,"+currentFood.getId()+","+currentFood.getFoodCategory().getId()));
//        keyboardRow2.add(rightButton);
//        keyboardRow3.add(backButton);//
//        toCartRow.add(toCartButton);
//        //checkoutRow.add(checkoutButton);
//
//        if(user.getFoods()!=null && !user.getFoods().isEmpty()){
//            //int temp = countContainsIdBySubCategory(user.getFoods(), currentFood.getFoodSubCategory().getId());
//
//            int temp = countContainsId(user.getFoods(), currentFood.getId());
//            if(temp>0) keyboardMarkup.getKeyboard().add(keyboardRow);
//            else{
//                keyboardMarkup.getKeyboard().add(toCartRow);
//            }
//            keyboardRow3.add(checkoutButton);
//        }
//        else{
//            keyboardMarkup.getKeyboard().add(toCartRow);
//        }
//        keyboardMarkup.getKeyboard().add(keyboardRow2);
//        keyboardMarkup.getKeyboard().add(keyboardRow3);
//        return keyboardMarkup;
//    }
//    public InlineKeyboardMarkup addButtonLeafForNavigationWithoutBack(){
//        keyboardMarkup = new InlineKeyboardMarkup();
////        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<InlineKeyboardButton> keyboardRow    = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardRow2    = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardRow3    = new ArrayList<>();
//        List<InlineKeyboardButton> toCartRow    = new ArrayList<>();
//        List<InlineKeyboardButton> checkoutRow    = new ArrayList<>();
//
//        InlineKeyboardButton addButton   = new InlineKeyboardButton();
//        addButton.setText(add);
//        //addButton.setCallbackData(add);
//        addButton.setCallbackData("40,"+currentFood.getId()+","+add);
//
//        InlineKeyboardButton minusButton  = new InlineKeyboardButton();
//        minusButton.setText(minus);
//        //minusButton.setCallbackData(minus);
//        minusButton.setCallbackData("40,"+currentFood.getId()+","+minus);
//        InlineKeyboardButton destroyButton   = new InlineKeyboardButton();
//        destroyButton.setText(destroy);
//        destroyButton.setCallbackData("40,"+currentFood.getId()+","+destroy);
//
////        currentFood = foods.get(page - 1);
//
//        InlineKeyboardButton leftButton   = new InlineKeyboardButton();
//        leftButton.setText(left);
//        leftButton.setCallbackData("40,"+currentFood.getId()+","+left);
//        InlineKeyboardButton rightButton  = new InlineKeyboardButton();
//        rightButton.setText(right);
//        rightButton.setCallbackData("40,"+currentFood.getId()+","+right);
//
//        InlineKeyboardButton foodCount = new InlineKeyboardButton();
//
//
//        String toCart =TelegramBotRepositoryProvider.getMessageRepository().findByIdAndLangId(131, LanguageService.getLanguage(user.getChatId()).getId()).getName();
//
//        InlineKeyboardButton toCartButton   = new InlineKeyboardButton();
//        toCartButton.setText(toCart);
//        toCartButton.setCallbackData("40,"+currentFood.getId()+","+add);
//
//        String checkout = TelegramBotRepositoryProvider.getMessageRepository().findByIdAndLangId(132, LanguageService.getLanguage(user.getChatId()).getId()).getName();
//
//        InlineKeyboardButton checkoutButton   = new InlineKeyboardButton();
//        checkoutButton.setText(checkout);
//        checkoutButton.setCallbackData("40,"+currentFood.getId()+",finish");
//
////        int countFood = Collections.frequency(user.getFoods(), currentFood);
//        int countFood = countContainsId(user.getFoods(), currentFood.getId());
//        foodCount.setText(String.valueOf(countFood)+"шт");
//        foodCount.setCallbackData("40,"+String.valueOf(page)+",-");
//
//        InlineKeyboardButton numberPage  = new InlineKeyboardButton();
//        numberPage.setText(String.valueOf(page));
//        numberPage.setCallbackData("40,"+currentFood.getId()+",$"+currentFood.getFoodCategory().getId());
//
//
//
//
//
//
//        keyboardRow.add(destroyButton);
//        keyboardRow.add(minusButton);
//        keyboardRow.add(foodCount);
//        keyboardRow.add(addButton);
//        keyboardRow2.add(leftButton);
//        keyboardRow2.add(getIButton(String.valueOf(page)+"/"+countPageFood(), "41,"+currentFood.getId()+","+currentFood.getFoodCategory().getId()));
//        keyboardRow2.add(rightButton);
//        toCartRow.add(toCartButton);
//        //checkoutRow.add(checkoutButton);
//
//        if(user.getFoods()!=null && !user.getFoods().isEmpty()){
//            //int temp = countContainsIdBySubCategory(user.getFoods(), currentFood.getFoodSubCategory().getId());
//
//            int temp = countContainsId(user.getFoods(), currentFood.getId());
//            if(temp>0) keyboardMarkup.getKeyboard().add(keyboardRow);
//            else{
//                keyboardMarkup.getKeyboard().add(toCartRow);
//            }
//            keyboardRow3.add(checkoutButton);
//        }
//        else{
//            keyboardMarkup.getKeyboard().add(toCartRow);
//        }
//        keyboardMarkup.getKeyboard().add(keyboardRow2);
//        keyboardMarkup.getKeyboard().add(keyboardRow3);
//        return keyboardMarkup;
//    }
//    public InlineKeyboardMarkup addButtonLeafForNavigationSpec(){
//        keyboardMarkup = new InlineKeyboardMarkup();
////        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<InlineKeyboardButton> keyboardRow    = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardRow2    = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardRow3    = new ArrayList<>();
//        List<InlineKeyboardButton> toCartRow    = new ArrayList<>();
//        List<InlineKeyboardButton> checkoutRow    = new ArrayList<>();
//
//        InlineKeyboardButton addButton   = new InlineKeyboardButton();
//        addButton.setText(add);
//        //addButton.setCallbackData(add);
//        addButton.setCallbackData("44,"+currentFood.getId()+","+add);
//
//        InlineKeyboardButton minusButton  = new InlineKeyboardButton();
//        minusButton.setText(minus);
//        //minusButton.setCallbackData(minus);
//        minusButton.setCallbackData("44,"+currentFood.getId()+","+minus);
//        InlineKeyboardButton destroyButton   = new InlineKeyboardButton();
//        destroyButton.setText(destroy);
//        destroyButton.setCallbackData("44,"+currentFood.getId()+","+destroy);
//
////        currentFood = foods.get(page - 1);
//
//        InlineKeyboardButton leftButton   = new InlineKeyboardButton();
//        leftButton.setText(left);
//        leftButton.setCallbackData("44,"+currentFood.getId()+","+left);
//        InlineKeyboardButton rightButton  = new InlineKeyboardButton();
//        rightButton.setText(right);
//        rightButton.setCallbackData("44,"+currentFood.getId()+","+right);
//
//        InlineKeyboardButton foodCount = new InlineKeyboardButton();
//
//
//        String toCart =TelegramBotRepositoryProvider.getMessageRepository().findByIdAndLangId(131, LanguageService.getLanguage(user.getChatId()).getId()).getName();
//
//        InlineKeyboardButton toCartButton   = new InlineKeyboardButton();
//        toCartButton.setText(toCart);
//        toCartButton.setCallbackData("44,"+currentFood.getId()+","+add);
//
//        String checkout = TelegramBotRepositoryProvider.getMessageRepository().findByIdAndLangId(132, LanguageService.getLanguage(user.getChatId()).getId()).getName();
//
//        InlineKeyboardButton checkoutButton   = new InlineKeyboardButton();
//        checkoutButton.setText(checkout);
//        checkoutButton.setCallbackData("44,"+currentFood.getId()+",finish");
//
////        int countFood = Collections.frequency(user.getFoods(), currentFood);
//        int countFood = countContainsId(user.getFoods(), currentFood.getId());
//        foodCount.setText(String.valueOf(countFood)+"шт");
//        foodCount.setCallbackData("44,"+String.valueOf(page)+",-");
//
//        InlineKeyboardButton numberPage  = new InlineKeyboardButton();
//        numberPage.setText(String.valueOf(page));
//        numberPage.setCallbackData("44,"+currentFood.getId()+",$"+currentFood.getFoodCategory().getId());
//
//
////        InlineKeyboardButton backButton  = new InlineKeyboardButton();
////        backButton.setText(getText(199)+back);
////        backButton.setCallbackData("44,"+currentFood.getFoodSubCategory().getFoodCategory().getId()+","+back);
//        InlineKeyboardButton backButton  = new InlineKeyboardButton();
//        backButton.setText(getText(199)+back);
//        if(currentFood.getFoodCategory().getFoodCategory()!=null) {
//            backButton.setCallbackData("44,"+currentFood.getFoodCategory().getFoodCategory().getId()+","+back);
//            //todo
//        }
//        else{
//            backButton.setCallbackData("43,"+back);
//
//        }
//
//
//        keyboardRow.add(destroyButton);
//        keyboardRow.add(minusButton);
//        keyboardRow.add(foodCount);
//        keyboardRow.add(addButton);
//        keyboardRow2.add(leftButton);
//        keyboardRow2.add(getIButton(String.valueOf(page)+"/"+countPageFood(), "41,"+currentFood.getId()+",*"));
//        keyboardRow2.add(rightButton);
//        keyboardRow3.add(backButton);//
//        toCartRow.add(toCartButton);
//        //checkoutRow.add(checkoutButton);
//
//        if(user.getFoods()!=null && !user.getFoods().isEmpty()){
//            //int temp = countContainsIdBySubCategory(user.getFoods(), currentFood.getFoodSubCategory().getId());
//
//            int temp = countContainsId(user.getFoods(), currentFood.getId());
//            if(temp>0) keyboardMarkup.getKeyboard().add(keyboardRow);
//            else{
//                keyboardMarkup.getKeyboard().add(toCartRow);
//            }
//            keyboardRow3.add(checkoutButton);
//        }
//        else{
//            keyboardMarkup.getKeyboard().add(toCartRow);
//        }
//        keyboardMarkup.getKeyboard().add(keyboardRow2);
//        keyboardMarkup.getKeyboard().add(keyboardRow3);
//        return keyboardMarkup;
//    }
//    public InlineKeyboardMarkup addButtonLeafForNavigationCart(){
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<InlineKeyboardButton> keyboardRow    = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardRow2    = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardRow3    = new ArrayList<>();
//
//        InlineKeyboardButton addButton   = new InlineKeyboardButton();
//        addButton.setText(add);
//        addButton.setCallbackData("82,"+currentFood.getId()+","+add);
//        InlineKeyboardButton minusButton  = new InlineKeyboardButton();
//        minusButton.setText(minus);
//        minusButton.setCallbackData("82,"+currentFood.getId()+","+minus);
//        InlineKeyboardButton destroyButton   = new InlineKeyboardButton();
//        destroyButton.setText(destroy);
//        destroyButton.setCallbackData("82,"+currentFood.getId()+","+destroy);
//
//
//        InlineKeyboardButton leftButton   = new InlineKeyboardButton();
//        leftButton.setText(left);
//        leftButton.setCallbackData("82,"+currentFood.getId()+","+left);
//        InlineKeyboardButton rightButton  = new InlineKeyboardButton();
//        rightButton.setText(right);
//        rightButton.setCallbackData("82,"+currentFood.getId()+","+right);
//
//        InlineKeyboardButton foodCountButton = new InlineKeyboardButton();
//
//        keys = new ArrayList<>(foodMap.keySet());
////        currentFood = keys.get(page - 1);
//
////        int countFood = Collections.frequency(user.getFoods(), currentFood);
//        countOfFood = countContainsId(user.getFoods(), currentFood.getId());
//
//        foodCountButton.setText(String.valueOf(countOfFood)+"шт");
//        foodCountButton.setCallbackData("82,"+currentFood.getId()+","+String.valueOf(page));
//
//        InlineKeyboardButton numberPage  = new InlineKeyboardButton();
//        numberPage.setText(String.valueOf(page));
//        numberPage.setCallbackData(String.valueOf(page));
//
//        InlineKeyboardButton buyButton = new InlineKeyboardButton();
//        buyButton.setText("✅ Оформить заказ за "+sumFoodPrice());
//        buyButton.setCallbackData("82,"+currentFood.getId()+",finish");
//        keyboardRow.add(destroyButton);
//        keyboardRow.add(minusButton);
//        keyboardRow.add(foodCountButton);
//        keyboardRow.add(addButton);
//        keyboardRow2.add(leftButton);
//        keyboardRow2.add(getIButton(String.valueOf(page)+"/"+countPageFoodMap(), "41,"+currentFood.getId()+","+"!"));
//        keyboardRow2.add(rightButton);
//        keyboardRow3.add(buyButton);
//
//
//        keyboardMarkup.getKeyboard().add(keyboardRow);
//        keyboardMarkup.getKeyboard().add(keyboardRow2);
//        keyboardMarkup.getKeyboard().add(keyboardRow3);
//        return keyboardMarkup;
//    }
//    public int sumFoodPrice(){
//        int sum = 0;
//        for(Food f:foods){
//            if(f.getSpecialOfferSum()==null) {
//                sum+=f.getFoodPrice(user.getCity());
//            }
//            else{
//                sum+=(f.getFoodPrice(user.getCity())-f.getSpecialOfferSum());
//            }
//        }
//        return sum;
//
//    }
//    public int sumFoodPrice(List<Food> foodList){
//        int sum = 0;
//        for(Food f:foodList){
//            if(f.getSpecialOfferSum()==null) {
//                sum+=f.getFoodPrice(user.getCity());
//            }
//            else{
//                sum+=(f.getFoodPrice(user.getCity())-f.getSpecialOfferSum());
//            }
//        }
//        return sum;
//
//    }
//    private InlineKeyboardMarkup                getInlineKeyboard(List<String> namesButton, List<String> callbackMessage) {
//        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsKeyboard;
//        if (!isHorizonSort) {
//            rowsKeyboard = getVerticalSortedLists(namesButton, callbackMessage);
//        } else {
//            rowsKeyboard = getHorizonSortedLists(namesButton, callbackMessage);
//        }
//        keyboard.setKeyboard(rowsKeyboard);
//        return keyboard;
//    }
//
//    private InlineKeyboardMarkup addButtonLeaf(InlineKeyboardMarkup inlineKeyboardMarkup) {
//
//        List<InlineKeyboardButton> rowButton    = new ArrayList<>();
//        InlineKeyboardButton leftBtn            = new InlineKeyboardButton();
//        leftBtn.setText(left);
//        leftBtn.setCallbackData(left);
//        InlineKeyboardButton rightBtn           = new InlineKeyboardButton();
//        rightBtn.setText(right);
//        rightBtn.setCallbackData(right);
//        rowButton.add(leftBtn);
//        rowButton.add(rightBtn);
//        inlineKeyboardMarkup.getKeyboard().add(rowButton);
//        return inlineKeyboardMarkup;
//    }
//
////    private List<List<InlineKeyboardButton>>    getVerticalSortedLists(List<String> namesButton, List<String> callbackMessage) {
////        List<List<InlineKeyboardButton>> rowsKeyboard   = new ArrayList<>();
////        String buttonIdsString;
////        int buttons                                     = namesButton.size();
////        int countButtonsInColumn                        = buttons / countColumn;
////        if (buttons % countColumn != 0) countButtonsInColumn++;
////        int counter                                     = 0;
////        for (int j = 0; j < countColumn; j++) {
////            for (int i = 0; i < countButtonsInColumn; i++) {
////                buttonIdsString                         = namesButton.get(counter);
////                if (j == 0) {
////                    List<InlineKeyboardButton> rowButton = new ArrayList<>();
////                    InlineKeyboardButton button         = getIButton(buttonIdsString, callbackMessage.get(counter));
////                    rowButton.add(button);
////                    rowsKeyboard.add(rowButton);
////                } else {
////                    InlineKeyboardButton button         = getIButton(buttonIdsString, callbackMessage.get(counter));
////                    rowsKeyboard.get(i).add(button);
////                }
////                counter++;
////                if (counter == buttons) break;
////            }
////            if (counter == buttons) break;
////        }
////        return rowsKeyboard;
////    }
//private List<List<InlineKeyboardButton>>    getVerticalSortedLists(List<String> namesButton, List<String> callbackMessage) {
//    List<List<InlineKeyboardButton>> rowsKeyboard   = new ArrayList<>();
//    String buttonIdsString;
//    int buttons                                     = namesButton.size();
//    int countButtonsInColumn                        = buttons / countColumn;
//    if (buttons % countColumn != 0) countButtonsInColumn++;
//    int counter = 0;
//    int currentRow = 0;
//    if (addKeyboardId != 0 && chatId != 0) {
//
//        List<List<InlineKeyboardButton>> rowsForAdd = keyboardMarkUpService.getRowsKeyboard(addKeyboardId, chatId);
//        currentRow = rowsForAdd.size();
//        rowsKeyboard.addAll(rowsForAdd);
//    }
//
//
//    for (int j = 0; j < countColumn; j++) {
//        for (int i = currentRow; i < countButtonsInColumn+currentRow; i++) {
//            buttonIdsString                         = namesButton.get(counter);
//            if (j == 0) {
//                List<InlineKeyboardButton> rowButton = new ArrayList<>();
//                InlineKeyboardButton button         = getIButton(buttonIdsString, callbackMessage.get(counter));
//                long id = -1;
//
//
//                    try{
//                        id = Long.parseLong(callbackMessage.get(counter).split(",")[1]);
//                    }
//                    catch (Exception e){
//
//                    }
//
//                FoodSubCategoryEntity foodSubCategoryEntity = TelegramBotRepositoryProvider.getFoodSubCategoryRepo().findFoodSubCategoryEntityById(id);
//                if(foodSubCategoryEntity!=null && foodSubCategoryEntity.getInline()){
//                    button.setSwitchInlineQueryCurrentChat(foodSubCategoryEntity.getName(1));
//                    button.setCallbackData(null);
//                }
//
//                rowButton.add(button);
//                rowsKeyboard.add(rowButton);
//            } else {
//
//                InlineKeyboardButton button         = getIButton(buttonIdsString, callbackMessage.get(counter));
//                long id = -1;
//
//
//                try{
//                    id = Long.parseLong(callbackMessage.get(counter).split(",")[1]);
//                }
//                catch (Exception e){
//
//                }
//
//                FoodSubCategoryEntity foodSubCategoryEntity = TelegramBotRepositoryProvider.getFoodSubCategoryRepo().findFoodSubCategoryEntityById(id);
//                if(foodSubCategoryEntity!=null && foodSubCategoryEntity.getInline()){
//                    button.setSwitchInlineQueryCurrentChat(foodSubCategoryEntity.getName(1));
//                    button.setCallbackData(null);
//                }
//                rowsKeyboard.get(i).add(button);
//            }
//            counter++;
//            if (counter == buttons) break;
//        }
//        if (counter == buttons) break;
//    }
//
//    return rowsKeyboard;
//}
//    private List<List<InlineKeyboardButton>>    getHorizonSortedLists(List<String> namesButton, List<String> callbackMessage) {
//        List<List<InlineKeyboardButton>> rowsKeyboard   = new ArrayList<>();
//        List<InlineKeyboardButton> rowButton            = new ArrayList<>();
//        for (int i = 0; i < namesButton.size(); i++) {
//            rowButton.add(getIButton(namesButton.get(i), callbackMessage.get(i)));
//            if (rowButton.size() >= countColumn || namesButton.size() == i + 1) {
//                rowsKeyboard.add(rowButton);
//                rowButton                               = new ArrayList<>();
//            }
//        }
//        return rowsKeyboard;
//    }
//
//    private InlineKeyboardButton                getIButton(String text, String callBack) { return new InlineKeyboardButton().setText(text).setCallbackData(callBack); }
//
//    public  int                                 countPage(){
//        int result = allNamesButtonList.size() / countButtons;
//        if (allNamesButtonList.size() % countButtons != 0) result++;
//        return result;
//    }
//    public  int                                 countPageFood(){
//        int result = foods.size();
//        //if (foods.size() % countButtons != 0) result++;
//        return result;
//    }
//    public int countPageFoodMap(){
//        return foodMap.size();
//    }
//    public  enum                                TypeKeyboard { INLINE, REPLY }
//}
