package com.akimatBot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrintPrecheckDTO {

    long id;
    String waiterName;
    Long deskNumber;
    String hallName;
    String precheckDate;
    FoodOrderDTO foodOrder;
    String printerName;
    Date cancelPrecheckDate;
}
