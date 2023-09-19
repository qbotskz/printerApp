package com.akimatBot.web.dto;

import com.akimatBot.entity.enums.OrderItemStatus;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class OrderItemDTO implements Serializable {

    private long id;

    private int quantity;

    private int price;

//    private Date createdDate;

    OrderItemStatus orderItemStatus;

    private FoodDTO food;

    private String comment;


}
