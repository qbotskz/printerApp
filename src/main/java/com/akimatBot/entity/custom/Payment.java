package com.akimatBot.entity.custom;

import com.akimatBot.web.dto.PaymentDTO;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double amount;

    @Getter
    @Column(columnDefinition = "double precision default 0.0")
    private double change;


    @ManyToOne
    PaymentType paymentType;

    @ManyToOne
    Cheque cheque;

    @Column(columnDefinition = "boolean default false")
    boolean prepayment;

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
