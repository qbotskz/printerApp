package com.akimatBot.web.dto;

import com.akimatBot.entity.enums.OrderStatus;
import com.akimatBot.entity.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@NoArgsConstructor
@Getter
public class FoodOrderDTO {


    private long id;

    private String createdDate;
    private String createdDateInDate;

    private List<GuestDTO> guests;

    private Boolean deliverNeed = false;

    private String address;

//    private boolean done = false;

//    private Date completionDate;

    private OrderType orderType;

    private ChequeDTO cheque;

    private OrderStatus orderStatus;

    private Boolean personalCanChange;

    private WaiterDTO waiter;


}
