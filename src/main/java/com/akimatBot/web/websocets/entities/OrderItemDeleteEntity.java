package com.akimatBot.web.websocets.entities;

import com.akimatBot.entity.custom.OrderItem;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.web.dto.OrderItemDTO;
import com.akimatBot.web.dto.OrderItemDeleteDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItemDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private OrderItem orderItem;

    private String reason;

    long orderId;
    String waiterName;
    String hallName;
    long deskNumber;

    Date date;
    boolean printed;

    public OrderItemDeleteDTO getDTO(){
        OrderItemDeleteDTO orderItemDeleteDTO = new OrderItemDeleteDTO();

        orderItemDeleteDTO.setId(this.id);
        orderItemDeleteDTO.setReason(this.getReason());
        orderItemDeleteDTO.setOrderItem(orderItem.getOrderItemDTO(Language.ru));
        orderItemDeleteDTO.setOrderId(orderItem.getGuest().getFoodOrder().getId());
        orderItemDeleteDTO.setWaiterName(orderItem.getGuest().getFoodOrder().getWaiter().getFullName());
        orderItemDeleteDTO.setHallName(orderItem.getGuest().getFoodOrder().getDesk().getHall().getName());
        orderItemDeleteDTO.setDeskNumber(orderItem.getGuest().getFoodOrder().getDesk().getNumber());

        return orderItemDeleteDTO;
    }

    public OrderItemDeleteEntity(long id) {
        this.id = id;
    }
}
