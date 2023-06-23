package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.CacheTypes;
import com.akimatBot.web.dto.KitchenDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Setter
@Getter
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
