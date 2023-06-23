package com.akimatBot.entity.custom;

import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.utils.DateUtil;
import com.akimatBot.web.dto.ChequeDTO;
import com.akimatBot.web.dto.PaymentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
public class Cheque {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @OneToOne(mappedBy = "cheque")
    FoodOrder order;

    @Setter
    private double total;

    @Setter
    @Getter
    private double prepayment;

    @Setter
    private double discount;

    @Setter
    private double service;

    @Setter
    private int deliveryPrice;

    @Setter
    @Getter
    private Boolean useCashback;
    @Setter
    @Getter
    private double usedCashback;

    @Setter
    private double cashbackPercentage;
    @Setter
    private double addedCashback;

    @Setter
    @Getter
    private double calculatedTotal;

    @Setter
    @Getter
    private Double forPayment;

    @Getter
    private Double change;




    //    @Getter
    @OneToMany(mappedBy = "cheque", fetch = FetchType.LAZY)
    private List<Payment> payments;

    public void setPrepayment(double prepayment) {
        this.prepayment = prepayment;
        if (this.id != 0) {
            calculate();
        }
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        if (this.id != 0) {
            calculate();
        }
    }

    public void setService(double service) {
        this.service = service;
        if (this.id != 0) {
            calculate();
        }
    }

    //    public double getChange() {
//        if (getCalculatedTotal() < 0){
//            this.change = getCalculatedTotal();
//            return change;
//        }
//        return 0;
//    }
//    public double getCalculatedTotal() {
//        return calculatedTotal;
//    }

    public List<Payment> getPayments() {
        return TelegramBotRepositoryProvider.getPaymentRepo().findAllByCheque(this);
    }


    public void addTotal(double total){
        this.total += total;
    }

    public Cheque() {}

//    public Map<Object, Object> getJson(){
//        Map<Object, Object> map = new TreeMap<>();
//        map.put("id", id);
//        map.put("total", this.total);
//        map.put("discount", this.discount);
//        map.put("service", this.service);
//        map.put("deliveryPrice", this.deliveryPrice);
//        map.put("usedCashback", this.usedCashback);
//        map.put("cashbackPercentage", this.cashbackPercentage);
//        map.put("addedCashback", this.addedCashback);
//        map.put("prepayment", this.prepayment);
//        map.put("calculatedTotal", this.getCalculatedTotal());
//
//        return map;
//    }


    public ChequeDTO getChequeDTO() {
        this.calculate();

        ChequeDTO chequeDTO = new ChequeDTO();

        chequeDTO.setId(this.id);
        chequeDTO.setTotal(this.total);
        chequeDTO.setDiscount(this.discount);
        chequeDTO.setService(this.service);
        chequeDTO.setDeliveryPrice(this.deliveryPrice);
        chequeDTO.setUsedCashback(this.usedCashback);
        chequeDTO.setCashbackPercentage(this.cashbackPercentage);
        chequeDTO.setAddedCashback(this.addedCashback);
        chequeDTO.setPrepayment(this.prepayment);
        chequeDTO.setCalculatedTotal(this.getCalculatedTotal());
        chequeDTO.setPayments(getPaymentsDTO());
        chequeDTO.setChange(this.getChange());
        chequeDTO.setForPayment(getForPayment());

        return chequeDTO;

    }

//    private double getChange() {
//        if (this.getPaymentsTotal() + getPrepayment() > calculatedTotal){
//            return getPaymentsTotal() + getPrepayment()  - calculatedTotal;
//        }
//        return 0.0;
//    }

//    public double getForPayment() {
//        if (getPaymentsTotal() + getPrepayment() >  getCalculatedTotal()){
//            return 0;
//        }
//        return this.getCalculatedTotal() - getPaymentsTotal() - getPrepayment();
//    }

//    private double getPaymentsTotal() {
//        return TelegramBotRepositoryProvider.getPaymentRepo().getTotalOfCheque(this.id);
//    }

    private double getPaymentsTotal() {
        double total = 0.0;
            for (Payment payment : getPayments()) {
                total += payment.getAmount();
            }
        return total;
    }

    private List<PaymentDTO> getPaymentsDTO() {
        List<PaymentDTO> dtos = new ArrayList<>();
            for (Payment payment : getPayments()) {
                dtos.add(payment.getPaymentDTO());
            }
        return dtos;
    }

    public void calculate() {
        calculateTotal();
        calculateForPayment();
        calculateChange();
    }

    private void calculateChange() {
        if (this.getPaymentsTotal() + getPrepayment() > calculatedTotal){
            change =  getPaymentsTotal() + getPrepayment()  - calculatedTotal;
        }
        else change = 0.0;
    }

    private void calculateForPayment() {
        if (getPaymentsTotal() + getPrepayment() >  getCalculatedTotal()){
            forPayment = 0.0;
        }
        else forPayment =  this.getCalculatedTotal() - getPaymentsTotal() - getPrepayment();
    }

    private void calculateTotal() {
        calculatedTotal  = total - total*discount/100;
        calculatedTotal += total * service/100;
        calculatedTotal += deliveryPrice;
        calculatedTotal -= usedCashback;

        addedCashback = calculatedTotal * cashbackPercentage/100;

    }

    public void minusTotal(double value) {
        total -= value;
    }
}
