package com.akimatBot.web.dto;

import com.akimatBot.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeskDTO {

    long id;
    long number;

    boolean available;

    OrderStatus foodOrderStatus;

    FoodOrderDTO order;

//    String currentWaiterFullName;

    HallDTO hall;


}
