package com.akimatBot.web.dto;

import com.akimatBot.entity.custom.PaymentType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
public class PaymentDTO {

    private long     id;

    private double amount;

    PaymentTypeDTO paymentType;


}
