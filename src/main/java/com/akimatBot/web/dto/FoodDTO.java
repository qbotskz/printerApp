package com.akimatBot.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

//@Data
@Getter
@Setter
@ToString
public class FoodDTO {

    private long id;
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
