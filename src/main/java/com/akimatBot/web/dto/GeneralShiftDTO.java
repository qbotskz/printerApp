package com.akimatBot.web.dto;

import com.akimatBot.entity.standart.Employee;
import com.akimatBot.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class GeneralShiftDTO {

    long id;


    @ManyToOne
    EmployeeDTO openedBy;

    @ManyToOne
    EmployeeDTO closedBy;

    private String openingTime;
    private String closingTime;

}
