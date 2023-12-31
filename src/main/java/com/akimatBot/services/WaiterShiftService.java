package com.akimatBot.services;

import com.akimatBot.entity.custom.WaiterShift;
import com.akimatBot.entity.standart.Employee;
import com.akimatBot.repository.repos.WaiterShiftRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@Component(value = "WaiterShiftService")
public class WaiterShiftService {

    @Autowired
    WaiterShiftRepo waiterShiftRepo;

    @Autowired
    EmployeeService employeeService;


    public boolean closeShift(long code) {

        WaiterShift waiterShift = getOpenedShift(code);
        if (waiterShift != null) {
            waiterShift.setClosingTime(new Date());
            waiterShiftRepo.save(waiterShift);

            employeeService.setNullToShift(code);

            return true;
        }
        return false;
    }

    public boolean closeShiftByChatId(long chatId) {

        WaiterShift waiterShift = getOpenedShiftByChatId(chatId);
        if (waiterShift != null) {
            waiterShift.setClosingTime(new Date());
            waiterShiftRepo.save(waiterShift);

            employeeService.setNullToShiftByChatId(chatId);

            return true;
        }
        return false;
    }

    public boolean hasOpenedShift(long code) {
        return waiterShiftRepo.hasOpenedShift(code);
    }

    public boolean hasOpenedShiftByChatId(long chatId) {
        return waiterShiftRepo.hasOpenedShiftByChatId(chatId);
    }

    public void closeAllShifts() {
        waiterShiftRepo.closeAllShifts();
    }

    public WaiterShift getOpenedShift(long code) {
        return waiterShiftRepo.getCurrentShift(code);
    }

    public WaiterShift getOpenedShiftByChatId(long chatId) {
        return waiterShiftRepo.getCurrentShiftByChatId(chatId);
    }
}
