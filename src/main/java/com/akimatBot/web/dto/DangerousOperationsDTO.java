package com.akimatBot.web.dto;

import com.akimatBot.entity.custom.PrintPrecheck;
import com.akimatBot.web.websocets.entities.OrderItemDeleteEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DangerousOperationsDTO {

    List<OrderItemDeleteDTO> orderItems;
    List<PrintPrecheckDTO> prechecks;

}
