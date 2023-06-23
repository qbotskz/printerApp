package com.akimatBot.services;

import com.akimatBot.entity.custom.CartItem;
import com.akimatBot.entity.custom.Desk;
import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.repository.repos.CartItemRepo;
import com.akimatBot.repository.repos.DeskRepo;
import com.akimatBot.web.dto.DeskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class DeskService {

    @Autowired
    FoodService foodService;

    @Autowired
    DeskRepo deskRepo;

    List<DeskDTO> getAllActive(long waiterChatId, Language language){
        List<DeskDTO> deskDTOList = new ArrayList<>();

        for (Desk desk : deskRepo.getActiveDesks(waiterChatId)){
            deskDTOList.add(desk.getDeskDTOFull(language, waiterChatId));
        }

        return deskDTOList;
    }

    List<DeskDTO> getAllActiveNotFull(long waiterChatId){
        List<DeskDTO> deskDTOList = new ArrayList<>();

        for (Desk desk : deskRepo.getActiveDesks(waiterChatId)){
            deskDTOList.add(desk.getDeskDTONotFull());
        }

        return deskDTOList;
    }



}
