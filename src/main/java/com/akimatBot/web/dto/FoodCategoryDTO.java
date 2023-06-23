package com.akimatBot.web.dto;

import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.enums.Language;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Getter
@Setter
public class FoodCategoryDTO {

    private long     id;

    private String name;


    private String description;

    private List<FoodDTO> foods;


}
