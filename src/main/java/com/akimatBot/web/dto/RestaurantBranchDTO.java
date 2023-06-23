package com.akimatBot.web.dto;

import com.akimatBot.entity.custom.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantBranchDTO {


    private int     id;

    private String branchName;

    private CityDTO city;

}
