package com.akimatBot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
public class CityDTO {


    private int     id;

    private String name;

    public CityDTO() {
    }

}
