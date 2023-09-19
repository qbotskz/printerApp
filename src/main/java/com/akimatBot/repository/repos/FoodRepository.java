package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.custom.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findFoodsByFoodCategoryAndActivatedTrue(FoodCategory foodSubCategory);

    List<Food> findAllByOrderByIdAsc();

    List<Food> findAllByRemainsNotNullAndActivatedTrueOrderById();

    int countByNameRu(String name);

    Food findById(long id);

    List<Food> findAllBySpecialOfferSumNotNull();

    @Query("select f from  Food f where f.foodCategory = ?1 and (f.remains > 0 or f.remains is null ) and f.activated = true ")
    List<Food> findFoodsByFoodCategory(FoodCategory foodCategory);

    @Query("select f from  Food f where f.foodCategory.id = ?1 and f.activated = true")
    List<Food> getByCategoryId(long id);

    Food findFoodByNameRu(String name);

    @Query(value = "select * from (select * , row_number() over (order by bb.id) rankk from Food bb  where LOWER(bb.name_ru) like CONCAT('%',LOWER(:name),'%')) zz where zz.rankk between :sidx and :eidx and (remains > 0 or remains is null ) and activated = true", nativeQuery = true)
    public List<Food> searchRu(@Param("name") String name, @Param("sidx") int sidx, @Param("eidx") int eidx);


    @Query(value = "select * from (select * , row_number() over (order by bb.id) rankk from Food bb  where LOWER(bb.name_kz) like CONCAT('%',LOWER(:name),'%')) zz where zz.rankk between :sidx and :eidx and (remains > 0 or remains is null ) and activated = true", nativeQuery = true)
    public List<Food> searchKz(@Param("name") String name, @Param("sidx") int sidx, @Param("eidx") int eidx);

    List<Food> findAllByOrderById();

    @Modifying
    @Transactional
    @Query("update Food food set food.activated = false where food.id = ?1")
    void setDelete(long id);
}
