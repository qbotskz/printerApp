package com.akimatBot.entity.custom;

import com.akimatBot.web.dto.CityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    public City() {
    }

    public CityDTO getDTO() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(this.getId());
        cityDTO.setName(this.getName());
        return cityDTO;
    }
}
