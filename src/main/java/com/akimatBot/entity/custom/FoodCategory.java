package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.web.dto.FoodCategoryDTO;
import com.akimatBot.web.dto.FoodDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Data
@Entity
@Getter
@Setter
public class FoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 4096)
    private String nameRu;
    @Column(length = 4096)
    private String nameKz;
    @Column(length = 4096)
    private String descriptionRu;
    @Column(length = 4096)
    private String descriptionKz;


//    @ManyToOne
//    private FoodCategory foodCategory;
//    private Boolean inline;

    @OneToMany(mappedBy = "foodCategory", fetch = FetchType.LAZY)
    private List<Food> foods;


    public String getName(int langId) {
        if (langId == 1) {
            return this.nameRu;
        }
        return this.nameKz;
    }

    public String getDescription(Language language) {
        if (language.equals(Language.ru)) {
            return this.nameRu;
        }
        return this.nameKz;
    }

//    public Map<Object, Object> getJson(Language lang) {
//        Map<Object, Object> json = new TreeMap<>();
//        json.put("id", id);
//        json.put("name", getName(lang.getId()));
//
//        return json;
//    }

//    public Map<Object, Object> getJsonWithFoods(Language lang) {
//        Map<Object, Object> json = new TreeMap<>();
//        json.put("id", id);
//        json.put("name", getName(lang.getId()));
//        json.put("foods",getFoodsJson(lang));
//        return json;
//    }

//    private Object getFoodsJson(Language lang) {
//        List< Map<Object, Object>> foodsJson = new ArrayList<>();
//        for (Food food : this.foods){
//            foodsJson.add(food.getJson(lang));
//        }
//        return foodsJson;
//    }

    public FoodCategoryDTO getDTO(Language language) {
        FoodCategoryDTO foodCategoryDTO = new FoodCategoryDTO();
        foodCategoryDTO.setId(this.getId());
        foodCategoryDTO.setName(this.getName(language.getId()));
        foodCategoryDTO.setDescription(this.getDescription(language));
        foodCategoryDTO.setFoods(getFoodsDTO(language));
        return foodCategoryDTO;
    }

    private List<FoodDTO> getFoodsDTO(Language language) {
        List<FoodDTO> dtos = new ArrayList<>();
        for (Food food : this.getFoods()) {
            dtos.add(food.getFoodDTO(language));
        }
        return dtos;
    }

    public List<Food> getFoods() {
        return TelegramBotRepositoryProvider.getFoodRepository().findFoodsByFoodCategory(this);
    }
}
