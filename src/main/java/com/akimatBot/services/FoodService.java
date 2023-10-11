package com.akimatBot.services;


import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.repository.repos.FoodRepository;
import com.akimatBot.web.dto.FoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public Food findById(Long foodId) {
        return foodRepository.findById(foodId);
    }


    public Food save(Food food) {
        return foodRepository.save(food);
    }
}
