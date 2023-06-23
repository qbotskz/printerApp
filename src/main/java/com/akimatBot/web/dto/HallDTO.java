package com.akimatBot.web.dto;

import com.akimatBot.entity.custom.Desk;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class HallDTO {

    private long     id;

    private String name;

    List<DeskDTO> desks;

}
