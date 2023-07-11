package com.akimatBot.web.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class PrintKitchenDTO {

    long id;
    List<OrderItemDTO> items;
    long orderId;
    Date createdDate;
    String waiterName;
    String hallName;
    long deskNumber;


}
