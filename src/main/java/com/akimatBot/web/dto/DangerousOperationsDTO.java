package com.akimatBot.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DangerousOperationsDTO {

    List<OrderItemDeleteDTO> orderItems;
    List<PrintPrecheckDTO> prechecks;

}
