package com.akimatBot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriesCacheDTO {
    List<FoodCategoryDTO> foodCategories;
    Date lastChanged;
}
