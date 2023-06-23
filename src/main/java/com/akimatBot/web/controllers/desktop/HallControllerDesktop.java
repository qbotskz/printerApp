package com.akimatBot.web.controllers.desktop;


import com.akimatBot.entity.custom.Hall;
import com.akimatBot.repository.repos.HallRepo;
import com.akimatBot.repository.repos.PropertiesRepo;
import com.akimatBot.web.dto.HallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api/desktop/hall")

public class HallControllerDesktop {

    @Autowired
    HallRepo hallRepo;

    @Autowired
    PropertiesRepo propertiesRepo;


    @GetMapping("/getAll")
    public List<HallDTO> getAllHalls(){
        List<HallDTO> halls = new ArrayList<>();
        for (Hall hall : hallRepo.findAll()){
            halls.add(hall.getDTO());
        }

        return halls ;
    }

}
