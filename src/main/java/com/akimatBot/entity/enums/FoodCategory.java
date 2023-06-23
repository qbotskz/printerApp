//package com.akimatBot.entity.custom;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.List;
//
////@Data
//@Entity
//@Getter
//@Setter
//public class FoodCategory {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long     id;
//    @Column(length = 4096)
//    private String      categoryNameRu;
//    @Column(length = 4096)
//    private String      categoryNameKaz;
//    @Column(length = 4096)
//    private String      categoryDescriptionRu;
//    @Column(length = 4096)
//    private String      categoryDescriptionKaz;
//
//
//    @OneToMany(mappedBy = "foodCategory")
//    private List<FoodSubCategoryEntity> foodSubCategories;
//
//    public String getName(int langId){
//        if(langId == 1){
//            return this.categoryNameRu;
//        }
//        return this.categoryNameKaz;
//    }
//}
