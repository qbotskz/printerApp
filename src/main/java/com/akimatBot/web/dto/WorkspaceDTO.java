package com.akimatBot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Setter
@Getter
public class WorkspaceDTO {

    List<WaiterDTO> waiters;

    List<DeskDTO> desks;



}
