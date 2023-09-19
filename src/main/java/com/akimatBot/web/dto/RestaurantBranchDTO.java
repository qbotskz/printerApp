package com.akimatBot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantBranchDTO {


    private int id;

    private String branchName;

    private CityDTO city;

}
