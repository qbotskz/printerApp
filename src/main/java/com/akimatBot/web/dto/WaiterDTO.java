package com.akimatBot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WaiterDTO {

    private String name;
    private long chatId;
    private long id;
    private List<DeskDTO> desks;
    private Long code;
    private Double closedOrdersCash;
    private long deskSize;

}
