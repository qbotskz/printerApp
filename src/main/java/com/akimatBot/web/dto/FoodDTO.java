package com.akimatBot.web.dto;

import com.akimatBot.entity.custom.FoodCategory;
import com.akimatBot.entity.custom.RestaurantBranch;
import com.akimatBot.entity.enums.Language;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//@Data
@Getter
@Setter
public class FoodDTO {

    private long     id;
//    private String nameRu;
    private String name;
//    private String descriptionRu;
//    private String nameKz;
//    private String descriptionKz;
    private String description;
    private Integer price;
    private Long remains;
    private String lastChanged;
    private RestaurantBranchDTO branch;

//    RestaurantBranch branch;

    private String photo_url;

    private Boolean activated;
    private Integer specialOfferSum;
    private Integer cashBackPercentage;
    private List<KitchenDTO> kitchens;
//    public String getFoodDescription(Language lang){
//        if(lang == Language.ru)
//            return descriptionRu;
//
//        return descriptionKz;
//    }
//    public String getFoodName(Language lang){
//        if(lang == Language.ru){
//            return nameRu;
//        }
//        else {
//            return nameKz;
//        }
//    }


}
