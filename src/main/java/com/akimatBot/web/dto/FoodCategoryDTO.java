package com.akimatBot.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class FoodCategoryDTO {

    private long id;

    private String name;


    private String description;

    private List<FoodDTO> foods;


}
