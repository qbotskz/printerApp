package com.akimatBot.services;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.standart.Employee;
import com.akimatBot.repository.repos.*;
import com.akimatBot.web.dto.WaiterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WaiterService {

    @Autowired
    WaiterShiftRepo waiterShiftRepo;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DeskRepo deskRepo;

    @Autowired
    DeskService deskService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    OrderRepository orderRepository;

    public static WaiterDTO getWaiterByUserSimple(Employee user) {
        if (user != null) {
            WaiterDTO waiterDTO = new WaiterDTO();
            waiterDTO.setName(user.getFullName());
            waiterDTO.setCode(user.getCode());
            waiterDTO.setChatId(user.getChatId());
            waiterDTO.setId(user.getId());
            return waiterDTO;
        }return null;
    }

    public List<WaiterDTO> getAllActiveWaiters(long chatId, Language language){
        List<Employee> users = waiterShiftRepo.getActiveWaiters();
        List<WaiterDTO> waiterDTOList = new ArrayList<>();

        for (Employee user : users){
            if (user.getChatId() != chatId) {
                waiterDTOList.add(getWaiterByUser(user));
            }
        }

        return waiterDTOList;
    }

    private WaiterDTO getWaiterByUser(Employee user){
        if (user != null) {
            WaiterDTO waiterDTO = new WaiterDTO();
            waiterDTO.setName(user.getFullName());
            waiterDTO.setChatId(user.getChatId());
            waiterDTO.setCode(user.getCode());
            waiterDTO.setDesks(deskService.getAllActiveNotFull(user.getChatId()));
            return waiterDTO;
        }return null;
    }

    private WaiterDTO getFullWaiterByUser(Employee user){
        if (user != null) {
            WaiterDTO waiterDTO = new WaiterDTO();
            waiterDTO.setName(user.getFullName());
            waiterDTO.setChatId(user.getChatId());
            waiterDTO.setCode(user.getCode());
            waiterDTO.setDesks(deskService.getAllActive(user.getChatId(), Language.ru));
            return waiterDTO;
        }return null;
    }


    public List<WaiterDTO> getAllActiveWaiters(){
        List<Employee> users = waiterShiftRepo.getActiveWaiters();
        List<WaiterDTO> waiterDTOList = new ArrayList<>();

        for (Employee user : users){
            WaiterDTO waiterDTO = new WaiterDTO();
            waiterDTO.setName(user.getFullName());
            waiterDTO.setId(user.getId());
            waiterDTO.setCode(user.getCode());
            waiterDTO.setClosedOrdersCash(orderRepository.getClosedOrdersCash(user.getCode()));
            waiterDTO.setDeskSize(orderRepository.getDesksSize(user.getId()));
            waiterDTOList.add(waiterDTO);
        }

        return waiterDTOList;
    }





    public WaiterDTO getOne(long chatId, Language language) {
        Employee user = employeeService.findByChatId(chatId);

        WaiterDTO waiterDTO = new WaiterDTO();
        waiterDTO.setName(user.getFullName());
        waiterDTO.setChatId(user.getChatId());
        waiterDTO.setDesks(deskService.getAllActive(user.getChatId(), language));

        return waiterDTO;
    }

    public boolean existByCode(long code) {
        return employeeRepository.existsByCodeAndDeletedFalse(code);
    }
    public WaiterDTO  getByCode(long code) {
        return this.getFullWaiterByUser(employeeRepository.findByCodeAndDeletedFalse(code));
    }

    public boolean isOpenShiftById(long toWaiterId) {
        return employeeRepository.isOpenShiftById(toWaiterId);
    }
}
