package com.akimatBot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrintPaymentDTO {
    long id;
    String waiterName;
    Long deskNumber;
    String hallName;
    String precheckDate;
    FoodOrderDTO foodOrderDTO;
    String printerName;
}
