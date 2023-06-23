package com.akimatBot.entity.custom;

import com.akimatBot.web.dto.PaymentTypeDTO;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long     id;
    private String name;
    private boolean active;

    @ManyToOne
    PaymentMethod paymentMethod;

    public PaymentTypeDTO getPaymentTypeDTO(){
        PaymentTypeDTO paymentTypeDTO = new PaymentTypeDTO();
        paymentTypeDTO.setId(this.getId());
        paymentTypeDTO.setName(this.getName());

        return paymentTypeDTO;
    }
}
