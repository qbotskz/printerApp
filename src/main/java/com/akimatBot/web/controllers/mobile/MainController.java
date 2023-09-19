package com.akimatBot.web.controllers.mobile;


import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.custom.FoodCategory;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.repository.repos.FoodCategoryRepo;
import com.akimatBot.repository.repos.MessageRepository;
import com.akimatBot.repository.repos.PropertiesRepo;
import com.akimatBot.services.FoodService;
import com.akimatBot.services.LanguageService;
import com.akimatBot.web.dto.FoodCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/main")

public class MainController {


    @Autowired
    MessageRepository messageRepo;

    @Autowired
    FoodService foodService;


    @Autowired
    PropertiesRepo propertiesRepo;

    @Autowired
    FoodCategoryRepo foodCategoryRepo;


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


    @GetMapping("/getCategories")
    public List<FoodCategoryDTO> getGenres(@RequestHeader("lang") Language lang) {
        List<FoodCategoryDTO> cats = new ArrayList<>();
        for (FoodCategory sub : foodCategoryRepo.findAllByOrderById()) {
            cats.add(sub.getDTO(lang));
        }

        return cats;
    }

    @GetMapping("/getCategoriesWithFoods")
    public List<FoodCategoryDTO> getCategoriesWithFoods(@RequestParam("chatId") long chatId) {
        Language language = LanguageService.getLanguage(chatId);
        List<FoodCategoryDTO> cats = new ArrayList<>();
        for (FoodCategory sub : foodCategoryRepo.findAllByOrderById()) {
            cats.add(sub.getDTO(language));
        }

        return cats;
    }


}
