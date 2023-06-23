package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.utils.DateUtil;
import com.akimatBot.web.dto.FoodDTO;
import com.akimatBot.web.dto.KitchenDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

//@Data
@Entity
@Getter
@Setter
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long     id;
    @Column(length = 4096)
    private String nameRu;
    private String descriptionRu;

    private String nameKz;
    private String descriptionKz;
    private Integer price;

    private Long remains;

    private Date lastChanged;

    @ManyToOne
    RestaurantBranch branch;

    @ManyToOne
    private FoodCategory foodCategory;

    private String photo_url;

    private Boolean activated;
//    @Column(nullable = true)
//    private boolean hasSpecialOffer;
//    @Column(nullable = true)
    private Integer specialOfferSum;

    private Integer cashBackPercentage;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Kitchen> kitchens;

    public String getFoodDescription(Language lang){
        if(lang == Language.ru){
            return descriptionRu;
        }
        else {
            return descriptionKz;
        }
    }
    public String getFoodName(Language lang){
        if(lang == Language.ru){
            return nameRu;
        }
        else {
            return nameKz;
        }
    }
//    public Integer getFoodPrice(City city){
//        if(city == City.ALMATY){
//            return price;
//        }
//        else{
//            return foodPriceAstana;
//        }
//    }

    public Map<Object, Object> getJson(Language lang) {
        Map<Object, Object> json = new TreeMap<>();
        json.put("id", id);
        json.put("price", price);
        json.put("description", getFoodDescription(lang));
        json.put("photo_url", photo_url);
        json.put("cashBackPercentage", cashBackPercentage);
        json.put("specialOfferSum", specialOfferSum);
        json.put("name", getFoodName(lang));
        return json;
    }

    public FoodDTO getFoodDTO(Language lang) {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(this.id);
        foodDTO.setPrice(this.getPrice());
        foodDTO.setName(this.getFoodName(lang));
        foodDTO.setDescription(this.getFoodDescription(lang));
        foodDTO.setPhoto_url(this.photo_url);
        foodDTO.setCashBackPercentage(this.cashBackPercentage);
        foodDTO.setSpecialOfferSum(this.specialOfferSum);
        foodDTO.setActivated(this.activated);
        foodDTO.setRemains(this.getRemains());
        foodDTO.setBranch(this.getBranch().getDTO());
        foodDTO.setLastChanged(DateUtil.getDbMmYyyyHhMmSs(this.getLastChanged()));
        foodDTO.setKitchens(getKitchensDTO());

        return foodDTO;

    }

    private List<KitchenDTO> getKitchensDTO() {
        List<KitchenDTO> dtos = new ArrayList<>();
        for (Kitchen kitchen : this.kitchens){
            dtos.add(kitchen.getDTO());
        }
        return dtos;
    }

//    private Object getName(int id) {
//        if (id == 1)
//            return nameRu;
//        return nameKz;
//    }
//    @ManyToMany(mappedBy = "foods")
//    private List<User> users;
}
