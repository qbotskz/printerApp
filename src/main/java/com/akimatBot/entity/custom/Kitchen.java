package com.akimatBot.entity.custom;

import com.akimatBot.web.dto.KitchenDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@ToString
public class Kitchen {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String printerName;

    public KitchenDTO getDTO() {
        KitchenDTO kitchenDTO = new KitchenDTO();
        kitchenDTO.setId(this.id);
        kitchenDTO.setName(name);
        kitchenDTO.setPrinterName(this.printerName);
        return kitchenDTO;
    }
}
