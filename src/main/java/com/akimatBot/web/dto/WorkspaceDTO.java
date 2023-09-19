package com.akimatBot.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WorkspaceDTO {

    List<WaiterDTO> waiters;

    List<DeskDTO> desks;


}
