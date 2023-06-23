package com.akimatBot.web.dto;

import com.akimatBot.entity.custom.FoodOrder;
import com.akimatBot.entity.custom.PaymentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Getter
@Setter
public class ChequeDTO {


    private long id;
    private double total;

    private double prepayment;

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
