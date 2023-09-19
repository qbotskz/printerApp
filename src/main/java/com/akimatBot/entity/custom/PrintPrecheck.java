package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.web.dto.PrintPrecheckDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PrintPrecheck {

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
    boolean printed;
    boolean cancelled;
    Date date;


    public PrintPrecheckDTO getDTO() {
        PrintPrecheckDTO printPrecheckDTO = new PrintPrecheckDTO();
        printPrecheckDTO.setId(this.getId());
        printPrecheckDTO.setPrecheckDate(this.precheckDate);
        printPrecheckDTO.setFoodOrder(this.foodOrder.getFoodOrderDTO(Language.ru));
        printPrecheckDTO.setPrinterName(this.printerName);
        printPrecheckDTO.setWaiterName(this.getWaiterName());
        printPrecheckDTO.setHallName(this.getHallName());
        printPrecheckDTO.setDeskNumber(this.getDeskNumber());
        return printPrecheckDTO;
    }

    public PrintPrecheck(long id) {
        this.id = id;
    }
}
