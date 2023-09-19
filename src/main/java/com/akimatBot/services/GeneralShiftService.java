package com.akimatBot.services;

import com.akimatBot.entity.custom.GeneralShift;
import com.akimatBot.repository.repos.GeneralShiftRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class GeneralShiftService {

    @Autowired
    GeneralShiftRepo generalShiftRepo;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    WaiterShiftService waiterShiftService;

    public boolean openShift(Long code) {

        if (!generalShiftRepo.existsByClosingTimeIsNull()) {
            GeneralShift generalShift = new GeneralShift();
            generalShift.setOpenedBy(employeeService.findByCode(code));
            generalShift.setOpeningTime(new Date());
            generalShiftRepo.save(generalShift);
            return true;
        }
        return false;
    }

    public boolean closeShift(Long code) {

        GeneralShift generalShift = generalShiftRepo.findFirstByClosingTimeIsNullOrderByIdDesc();
        if (generalShift != null) {
            generalShift.setClosingTime(new Date());
            generalShift.setClosedBy(employeeService.findByCode(code));
            generalShiftRepo.save(generalShift);

            waiterShiftService.closeAllShifts();

            return true;
        }
        return false;
    }

    public boolean hasOpenedShift() {
        return generalShiftRepo.existsByOpeningTimeIsNotNullAndClosingTimeIsNull();
    }

    public GeneralShift getOpenedShift() {
        return generalShiftRepo.findFirstByClosingTimeIsNullOrderByIdDesc();
    }
}
