package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.enums.OrderStatus;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.services.CartItemService;
import com.akimatBot.web.dto.DeskDTO;
import com.akimatBot.web.dto.FoodOrderDTO;
import com.akimatBot.web.dto.HallDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@Entity
@Cacheable(value = false)
public class Desk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long     id;

    private long number;

    @ManyToOne
    private FoodOrder currentOrder;

//    private boolean available;


    @ManyToOne
    private Hall hall;

    public Desk() {
    }

    private FoodOrderDTO getOrderDTO(Language language,Long code) {
        if (this.currentOrder != null){
            return currentOrder.getFoodOrderDTO(language, code);
        }
        return null;
    }
    private FoodOrderDTO getOrderDTOByChatId(Language language,Long chatId) {
        if (this.currentOrder != null){
            return currentOrder.getFoodOrderDTOByChatId(language, chatId);
        }
        return null;
    }

    public DeskDTO getDeskDTONotFull() {
        DeskDTO deskDTO = new DeskDTO();

        deskDTO.setId(this.getId());
        deskDTO.setNumber(this.getNumber());
        deskDTO.setFoodOrderStatus(getFoodOrderStatus(this));
        return deskDTO;
    }

    private OrderStatus getFoodOrderStatus(Desk desk) {
        if (desk != null && desk.getCurrentOrder() != null){
            return desk.getCurrentOrder().getOrderStatus();
        }
//        return TelegramBotRepositoryProvider.getOrderRepository().getStatusOfOrder(desk.getId());
        return null;
    }

    public DeskDTO getDeskDTOFull(Language language,Long code) {
        DeskDTO deskDTO = new DeskDTO();
        deskDTO.setId(this.getId());
        deskDTO.setNumber(this.getNumber());
        deskDTO.setOrder(getOrderDTO(language,code));
        deskDTO.setHall(this.getHall().getDTOSimple());


        return deskDTO;
    }
    public DeskDTO getDeskDTOFullByChatId(Language language,Long chatId) {
        DeskDTO deskDTO = new DeskDTO();
        deskDTO.setId(this.getId());
        deskDTO.setNumber(this.getNumber());
        deskDTO.setOrder(getOrderDTOByChatId(language,chatId));
        deskDTO.setHall(this.getHall().getDTOSimple());

        return deskDTO;
    }

    private String getWaiterName() {
        if (currentOrder != null){
           return currentOrder.getWaiter().getFullName();
        }
        return null;
    }

}
