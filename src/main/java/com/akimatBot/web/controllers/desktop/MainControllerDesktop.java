package com.akimatBot.web.controllers.desktop;


import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.custom.FoodCategory;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.repository.repos.FoodCategoryRepo;
import com.akimatBot.repository.repos.MessageRepository;
import com.akimatBot.repository.repos.PropertiesRepo;
import com.akimatBot.services.CacheService;
import com.akimatBot.services.FoodService;
import com.akimatBot.services.KeyboardMarkUpService;
import com.akimatBot.services.LanguageService;
import com.akimatBot.web.dto.CategoriesCacheDTO;
import com.akimatBot.web.dto.FoodCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/desktop/categories")
public class MainControllerDesktop {


    @Autowired
    MessageRepository messageRepo;

//    @Autowired
//    FoodSubCategoryRepo subCategoryRepo;

    @Autowired
    FoodCategoryRepo foodCategoryRepo;
    @Autowired
    FoodService foodService;


    @Autowired
    PropertiesRepo propertiesRepo;

    @Autowired
    CacheService cacheService;


    @GetMapping("/openImage")
    public ResponseEntity<byte[]> getImage(@RequestParam("id") Long id) throws IOException {
        File img = new File("src/main/resources/images/123.jpg");
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
    }


    @GetMapping("/searchFood")
    public List<Map<Object, Object>> searchBook(@RequestParam("foodName") String foodName,
                                                @RequestParam("page") int page,
                                                @RequestParam("chatId") int chatId) {
        Language language = LanguageService.getLanguage(chatId);
        List<Map<Object, Object>> maps = new ArrayList<>();
        List<Food> foods = foodService.searchFood(foodName, page, language);
        for (Food food : foods) {
            maps.add(food.getJson(language));
        }
        return maps;
    }


//    @GetMapping("/getCategories")
//    public List<Map<Object, Object>> getGenres( @RequestHeader("lang") Language lang){
//        List<Map<Object, Object>> cats = new ArrayList<>();
//        for (FoodCategory sub : subCategoryRepo.findAllByOrderById()){
//            cats.add(sub.getJson(lang));
//        }
//
//        return cats;
//    }

    @GetMapping("/getAll")
    public CategoriesCacheDTO getCategoriesWithFoods() {
        Language language = Language.ru;
        CategoriesCacheDTO dto = new CategoriesCacheDTO();

        List<FoodCategoryDTO> foodCategoryDTOS = new ArrayList<>();

        for (FoodCategory sub : foodCategoryRepo.findAllByOrderById()) {
            foodCategoryDTOS.add((sub.getDTO(language)));
        }
        dto.setFoodCategories(foodCategoryDTOS);
        dto.setLastChanged(cacheService.createFoodCategoriesCache().getCacheTime());

        return dto;
    }

    @PostMapping("/checkCache")
    public ResponseEntity<CategoriesCacheDTO> getCategoriesWithFoods(
            @RequestBody CategoriesCacheDTO categoriesCacheDTO
    ) {
        if (cacheService.getFoodCategoriesCache().getCacheTime().after(categoriesCacheDTO.getLastChanged())) {
            return new ResponseEntity<>(getCategoriesWithFoods(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CategoriesCacheDTO(), HttpStatus.OK);

    }


    private int sendMessage(String text, String chatId) {
//        try {
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(chatId);
//            sendMessage.setText(text);
//            sendMessage.setParseMode("html");
//            return RestoranApplication.bot.execute(sendMessage).getMessageId();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return 0;
    }

    private long sendMessageWithKeyboard(String text, long kid, long chatId) {
        KeyboardMarkUpService keyboardMarkUpService = new KeyboardMarkUpService();
        return sendMessageWithKeyboard(text, keyboardMarkUpService.select(kid, chatId), chatId);
    }

    private int sendMessageWithKeyboard(String text, ReplyKeyboard replyKeyboard, long chatId) {
//        try {
//
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(chatId);
//            sendMessage.setText(text);
//            sendMessage.setParseMode("html");
//            sendMessage.setReplyMarkup(replyKeyboard);
//            return RestoranApplication.bot.execute(sendMessage).getMessageId();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return 0;
    }

    public void deleteMessage(String chatId, int messageId) {
//        try {
//            RestoranApplication.bot.execute(new DeleteMessage(chatId, messageId));
//        } catch (TelegramApiException ignored) {}
    }

    private String getText(long messId, int langId) {
        return messageRepo.findByIdAndLangId(messId, langId).getName();
    }


}
