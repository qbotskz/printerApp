package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.standart.User;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.utils.DateUtil;
import com.akimatBot.web.dto.GuestDTO;
import com.akimatBot.web.dto.OrderItemDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Setter
@Getter
public class Guest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "guest")
//    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderItem> orderItems;

    @ManyToOne
    private FoodOrder foodOrder;

    @ManyToOne
    private User client;
    private Date createdDate;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;


    public Guest() {

    }

    public List<OrderItem> getOrderItems() {
        try {

            List<OrderItem> orderItems1 = TelegramBotRepositoryProvider.getOrderItemRepository()
                    .findAllByGuestOrderByIdAscOrderItemStatusDesc(this.id);
            System.out.println(orderItems1.size());
            for (OrderItem orderItem : orderItems1) {
                System.out.println(orderItem.toString());
            }
            return orderItems1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addOrderItems(Set<OrderItem> orderItems) {
        if (this.orderItems == null)
            this.orderItems = new ArrayList<>();
        this.orderItems.addAll(orderItems);
    }

    public void addOrderItem(OrderItem orderItem) {
        if (this.orderItems == null)
            this.orderItems = new ArrayList<>();
        this.orderItems.add(orderItem);
    }


    public Map<Object, Object> getJson() {
        Map<Object, Object> map = new TreeMap<>();
        map.put("id", id);
        map.put("createdDate", DateUtil.getTimeDate2(this.createdDate));
        map.put("orderItems", getOrderItemsJson());

        return map;
    }


    private List<Map<Object, Object>> getOrderItemsJson() {

        List<Map<Object, Object>> items = new ArrayList<>();

        for (OrderItem item : getOrderItems()) {
            items.add(item.getJson());
        }

        return items;
    }

    public GuestDTO getGuestDTO(Language language) {
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(this.getId());
//        guestDTO.setCreatedDate(this.createdDate);
        guestDTO.setOrderItems(this.getOrderItemsDTO(language));

        return guestDTO;
    }

    private List<OrderItemDTO> getOrderItemsDTO(Language language) {
        List<OrderItemDTO> items = new ArrayList<>();

        for (OrderItem item : getOrderItems()) {
            items.add(item.getOrderItemDTO(language));
        }

        return items;
    }
}
