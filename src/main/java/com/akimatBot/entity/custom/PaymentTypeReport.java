package com.akimatBot.entity.custom;

import com.akimatBot.web.dto.PaymentTypeReportDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypeReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    PaymentType paymentType;
    Double total;

    Long quantity;

    public PaymentTypeReport(PaymentType paymentType, Double total, Long quantity) {
        this.paymentType = paymentType;
        this.total = total;
        this.quantity = quantity;
    }

    public PaymentTypeReportDTO getDTO() {
        PaymentTypeReportDTO paymentTypeReportDTO = new PaymentTypeReportDTO();
        paymentTypeReportDTO.setPaymentType(this.paymentType.getPaymentTypeDTO());
        paymentTypeReportDTO.setTotal(total);
        paymentTypeReportDTO.setQuantity(quantity);
        return paymentTypeReportDTO;
    }

    public PaymentTypeReport(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
