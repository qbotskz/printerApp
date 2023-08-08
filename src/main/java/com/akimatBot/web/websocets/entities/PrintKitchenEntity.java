package com.akimatBot.web.websocets.entities;

import com.akimatBot.entity.custom.OrderItem;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.web.dto.OrderItemDTO;
import com.akimatBot.web.dto.PrintKitchenDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PrintKitchenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    List<OrderItem> items;

    long orderId;
    Date createdDate;
    String waiterName;
    String hallName;
    long deskNumber;

    public PrintKitchenDTO getDTO(){
        PrintKitchenDTO printKitchenDTO = new PrintKitchenDTO();

        printKitchenDTO.setId(this.id);
        printKitchenDTO.setItems(getItemsStr(this.getItems()));
        printKitchenDTO.setOrderId(orderId);
        printKitchenDTO.setCreatedDate(TelegramBotRepositoryProvider.getOrderRepository().getCreatedDateByOrderId(orderId));
        printKitchenDTO.setDeskNumber(TelegramBotRepositoryProvider.getOrderRepository().getDeskNumberById(orderId));
        printKitchenDTO.setWaiterName(TelegramBotRepositoryProvider.getOrderRepository().getWaiterNameById(orderId));
        printKitchenDTO.setHallName(TelegramBotRepositoryProvider.getOrderRepository().getHallNameById(orderId));

        return printKitchenDTO;
    }

    public PrintKitchenEntity(long id) {
        this.id = id;
    }

    public static List<OrderItemDTO> getItemsStr(List<OrderItem> orderItems){
        List<OrderItemDTO> dtos = new ArrayList<>();
        for (OrderItem orderItem : orderItems){
            dtos.add(orderItem.getOrderItemDTO(Language.en));
        }
        return dtos;
//        return new Gson().toJson(dtos);
    }



}
