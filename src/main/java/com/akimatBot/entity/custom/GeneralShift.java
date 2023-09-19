package com.akimatBot.entity.custom;

import com.akimatBot.entity.standart.Employee;
import com.akimatBot.utils.DateUtil;
import com.akimatBot.web.dto.GeneralShiftDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
public class GeneralShift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    Employee openedBy;

    @ManyToOne
    Employee closedBy;

    private Date openingTime;
    private Date closingTime;


    public GeneralShiftDTO getDTO() {
        GeneralShiftDTO generalShiftDTO = new GeneralShiftDTO();
        generalShiftDTO.setId(this.getId());
        generalShiftDTO.setOpenedBy(this.openedBy.getDTO());
//        generalShiftDTO.setClosedBy(this.openedBy.getDTO());
        generalShiftDTO.setOpeningTime(DateUtil.getDateAndTime(this.openingTime));
        generalShiftDTO.setClosingTime(DateUtil.getDateAndTime(Objects.requireNonNullElseGet(closingTime, Date::new)));

        return generalShiftDTO;
    }

}
