package com.akimatBot.web.dto;

import com.akimatBot.entity.custom.FoodOrder;
import com.akimatBot.entity.custom.OrderItem;
import com.akimatBot.entity.standart.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class GuestDTO {

    private long id;

    private List<OrderItemDTO> orderItems;

//    private Date createdDate;

}
