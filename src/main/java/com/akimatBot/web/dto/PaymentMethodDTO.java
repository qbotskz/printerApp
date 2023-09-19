package com.akimatBot.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentMethodDTO {

    private long id;
    private String name;
//    private boolean active;

    List<PaymentTypeDTO> paymentTypes;

}
