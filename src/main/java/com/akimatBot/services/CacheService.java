package com.akimatBot.services;

import com.akimatBot.entity.custom.Cache;
import com.akimatBot.entity.custom.Desk;
import com.akimatBot.entity.enums.CacheTypes;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.repository.repos.CacheRepo;
import com.akimatBot.repository.repos.DeskRepo;
import com.akimatBot.web.dto.DeskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class CacheService {

    @Autowired
    FoodService foodService;

    @Autowired
    DeskRepo deskRepo;

    @Autowired
    CacheRepo cacheRepo;

    List<DeskDTO> getAllActive(long waiterChatId, Language language) {
        List<DeskDTO> deskDTOList = new ArrayList<>();

        for (Desk desk : deskRepo.getActiveDesks(waiterChatId)) {
            deskDTOList.add(desk.getDeskDTOFull(language, waiterChatId));
        }

        return deskDTOList;
    }

    List<DeskDTO> getAllActiveNotFull(long waiterChatId) {
        List<DeskDTO> deskDTOList = new ArrayList<>();

        for (Desk desk : deskRepo.getActiveDesks(waiterChatId)) {
            deskDTOList.add(desk.getDeskDTONotFull());
        }

        return deskDTOList;
    }

    public Cache createFoodCategoriesCache() {
        Cache cache = cacheRepo.findByCacheType(CacheTypes.FOOD_CATEGORIES);
        if (cache == null) {
            cache = new Cache();
            cache.setCacheType(CacheTypes.FOOD_CATEGORIES);
        }
        cache.setCacheTime(new Date());
        return cacheRepo.save(cache);


    }


    public Cache getFoodCategoriesCache() {
        return cacheRepo.findByCacheType(CacheTypes.FOOD_CATEGORIES);
    }
}
