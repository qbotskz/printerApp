package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodCategoryRepo extends JpaRepository<FoodCategory, Integer> {
    FoodCategory findFoodCategoryById(long id);

//    @Query("select f from  FoodCategory f where f.foods ")
    List<FoodCategory> findAllByOrderById();
}
