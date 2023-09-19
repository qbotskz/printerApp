package com.akimatBot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;

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
