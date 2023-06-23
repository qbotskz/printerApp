package com.akimatBot.web.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
public class PaymentMethodDTO {

    private long     id;
    private String name;
//    private boolean active;

    List<PaymentTypeDTO> paymentTypes;

}
