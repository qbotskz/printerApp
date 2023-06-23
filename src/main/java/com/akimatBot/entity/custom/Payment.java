package com.akimatBot.entity.custom;

import com.akimatBot.web.dto.PaymentDTO;
import com.akimatBot.web.dto.PaymentTypeDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;

@Data
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long     id;

    private double amount;


    @ManyToOne
    PaymentType paymentType;

    @ManyToOne
    Cheque cheque;

    public Payment() {

    }


    public PaymentDTO getPaymentDTO() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(this.getId());
        paymentDTO.setAmount(this.amount);
        paymentDTO.setPaymentType(this.getPaymentType().getPaymentTypeDTO());

        return paymentDTO;
    }
}
