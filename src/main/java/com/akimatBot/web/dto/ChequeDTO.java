package com.akimatBot.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ChequeDTO {


    private long id;
    private double total;

    private PaymentDTO prepayment;

    private double discount;
    private double service;

    private int deliveryPrice;

    private Boolean useCashback;

    private double usedCashback;

    private double cashbackPercentage;

    private double addedCashback;

    private double calculatedTotal;

    private Double forPayment;

    @Getter
    private Double change;


    private List<PaymentDTO> payments;

//    public double getCalculatedTotal() {
//        calculatedTotal  = total - total*discount/100;
//        calculatedTotal += calculatedTotal * service/100;
//        calculatedTotal += deliveryPrice;
//        calculatedTotal -= usedCashback;
//
//        addedCashback = calculatedTotal * cashbackPercentage/100;
//
//        calculatedTotal -= prepayment;
//
//        return calculatedTotal;
//    }
//
//    public void addTotal(double total){
//        this.total += total;
//    }

    public ChequeDTO() {

    }

}
