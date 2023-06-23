package com.akimatBot.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum OrderType {


    takeout,
    courier,
    in_the_restaurant;

}
