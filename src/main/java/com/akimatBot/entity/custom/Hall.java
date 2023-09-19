package com.akimatBot.entity.custom;

import com.akimatBot.web.dto.DeskDTO;
import com.akimatBot.web.dto.HallDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Data
@AllArgsConstructor
@Entity
@Getter
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(mappedBy = "hall")
    @OrderBy("id ASC")
    List<Desk> desks;

    public Hall() {
    }

    public HallDTO getDTO() {

        HallDTO hallDTO = new HallDTO();
        hallDTO.setId(this.id);
        hallDTO.setName(this.name);
        hallDTO.setDesks(getDesksDTO());

        return hallDTO;
    }

    public HallDTO getDTOSimple() {

        HallDTO hallDTO = new HallDTO();
        hallDTO.setId(this.id);
        hallDTO.setName(this.name);
        return hallDTO;
    }

    private List<DeskDTO> getDesksDTO() {
        List<DeskDTO> dtos = new ArrayList<>();
        for (Desk desk : this.desks) {
            dtos.add(desk.getDeskDTONotFull());
        }
        return dtos;
    }

//    private Object getDesks() {
//        List<Map<Object, Object>> desks = new ArrayList<>();
//        for (Desk desk : this.desks){
//            desks.add(desk.getJson());
//        }
//        return desks;
//    }
}
