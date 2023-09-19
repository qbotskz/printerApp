package com.akimatBot.web.dto;


import com.akimatBot.entity.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeDTO {

    private long id;

    private long chatId;
    private String phone;
    private String fullName;
    private String position;
    private Long code;

    Language language;


    private WaiterShiftDTO currentShift;

    List<RoleDTO> roles;
    private RestaurantBranchDTO restaurantBranch;

}
