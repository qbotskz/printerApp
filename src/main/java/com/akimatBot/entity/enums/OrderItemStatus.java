package com.akimatBot.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum OrderItemStatus {

    IN_CART(0), // в корзине, не готовится
    COOK(1), // уже готовится
    EAT(2),// кушают

    DELETED(3),

    IN_CART_CLIENT(4),

    ORDERED_CLIENT(5);
    final long id;
}
