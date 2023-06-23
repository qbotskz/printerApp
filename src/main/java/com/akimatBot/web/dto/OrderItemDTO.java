package com.akimatBot.web.dto;

import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.custom.FoodOrder;
import com.akimatBot.entity.enums.OrderItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class OrderItemDTO  implements Serializable {

    private long id;

    private int quantity;

    private int price;

//    private Date createdDate;

    OrderItemStatus orderItemStatus;

    private FoodDTO food;

    private String comment;


}
