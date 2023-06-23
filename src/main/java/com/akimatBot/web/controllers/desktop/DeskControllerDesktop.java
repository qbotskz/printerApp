package com.akimatBot.web.controllers.desktop;


import com.akimatBot.entity.custom.Desk;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.repository.repos.PropertiesRepo;
import com.akimatBot.repository.repos.DeskRepo;
import com.akimatBot.services.EmployeeService;
import com.akimatBot.services.WaiterService;
import com.akimatBot.web.dto.DeskDTO;
import com.akimatBot.web.dto.WorkspaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/desktop/desk")
public class DeskControllerDesktop {

    @Autowired
    DeskRepo deskRepo;

    @Autowired
    PropertiesRepo propertiesRepo;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    WaiterService waiterService;


    @GetMapping("/getAll")
    public Object getAllTable(){
        List<DeskDTO> all = new ArrayList<>();
        for (Desk desk : deskRepo.findAllByOrderByNumber()){
            all.add(desk.getDeskDTONotFull());
        }
        return all;
    }



//    @GetMapping("/getAll/active")
//    public List<DeskDTO> getActiveDesksMy(@RequestHeader("code") long code){
//
//        List<Desk> desks = deskRepo.getActiveDesksByCode(code);
//
//        List<DeskDTO> ls = new ArrayList<>();
//        for (Desk desk : desks){
//            if (desk != null) {
//                ls.add(desk.getDeskDTOFull(Language.ru));
//            }
//        }
//        return ls;
//    }




    @GetMapping("/getWorkspace")
    public WorkspaceDTO getActiveDesksMy(
            @RequestHeader("code") long code
    ){



        List<Desk> desks = deskRepo.getActiveDesksByCode(code);
        List<DeskDTO> ls = new ArrayList<>();
        for (Desk desk : desks){
            if (desk != null) {
                ls.add(desk.getDeskDTOFull(Language.ru, null));
            }
        }

        WorkspaceDTO workspaceDTO = new WorkspaceDTO();
        workspaceDTO.setWaiters(waiterService.getAllActiveWaiters());
        workspaceDTO.setDesks(ls);

        return workspaceDTO;
    }



    @PreAuthorize("@permissionEvaluator.isOpenShift()")
    @GetMapping("/{id}")
    public DeskDTO getOne(@PathVariable(name = "id") long id,
                          @RequestHeader(value="code") long code){
        Desk desk = deskRepo.findById(id);
       return desk.getDeskDTOFull(Language.ru, code);
    }
}
