package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.web.dto.PrintPaymentDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PrintPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String waiterName;
    Long deskNumber;
    String hallName;
    String precheckDate;

    @ManyToOne
    FoodOrder foodOrder;

    String printerName;

    public PrintPaymentDTO getDTO(){
        PrintPaymentDTO printPaymentDTO = new PrintPaymentDTO();
        printPaymentDTO.setId(this.getId());
        printPaymentDTO.setPrecheckDate(this.precheckDate);
        printPaymentDTO.setFoodOrderDTO(this.foodOrder.getFoodOrderDTO(Language.ru));
        printPaymentDTO.setPrinterName(this.printerName);
        printPaymentDTO.setWaiterName(this.getWaiterName());
        printPaymentDTO.setHallName(this.getHallName());
        printPaymentDTO.setDeskNumber(this.getDeskNumber());
        return printPaymentDTO;
    }

    public PrintPayment(long id) {
        this.id = id;
    }
}
