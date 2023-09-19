package com.akimatBot.web.controllers.mobile;


import com.akimatBot.entity.custom.Desk;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.repository.repos.DeskRepo;
import com.akimatBot.repository.repos.PropertiesRepo;
import com.akimatBot.services.EmployeeService;
import com.akimatBot.web.dto.DeskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api/desk")

public class DeskController {

    @Autowired
    DeskRepo deskRepo;

    @Autowired
    PropertiesRepo propertiesRepo;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/getAll")
    public Object getAllTable() {
        List<DeskDTO> all = new ArrayList<>();
        for (Desk desk : deskRepo.findAll()) {
            all.add(desk.getDeskDTONotFull());
        }
        return all;
    }


    @GetMapping("/getAll/active")
    public List<DeskDTO> getActiveDesksMy(@RequestParam("chatId") long chatId) {

        int a = 5;
        System.out.println("ASDADSaSDADASdasD");

        List<Desk> desks = deskRepo.getActiveDesks(chatId);

        List<DeskDTO> ls = new ArrayList<>();
        for (Desk desk : desks) {
            if (desk != null) {
                ls.add(desk.getDeskDTOFullByChatId(Language.ru, chatId));
            }
        }
        return ls;
    }


    @GetMapping("/getOne")
    public DeskDTO getOne(@RequestParam("deskId") long deskId,
                          @RequestHeader(value = "chatId", defaultValue = "1") long chatId) {
        Desk desk = deskRepo.findById(deskId);
        return desk.getDeskDTOFullByChatId(Language.ru, chatId);
    }
}
