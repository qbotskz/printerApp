package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.enums.OrderItemStatus;
import com.akimatBot.web.dto.OrderItemDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Entity
@Setter
@Getter
public class OrderItem {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    @OneToOne
    @JoinColumn(updatable = false)
    private Food food;


    @Setter
    @Getter
    private int quantity;

    @Setter
    @Getter
    private int price;

    @Setter
    @Getter
    private String comment;

//    @Setter
//    @Getter
//    @ManyToOne
//    @JoinColumn(updatable = false)
//    private FoodOrder foodOrder;


    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(updatable = false)
    private Guest guest;

    private Date createdDate;

    @Enumerated
    OrderItemStatus orderItemStatus;


    public OrderItem() {
    }

    public OrderItem(Food food, int quantity, int price) {
        this.food = food;
        this.quantity = quantity;
        this.price = price;
    }

    public Map<Object, Object> getJson() {
        Map<Object, Object> map = new TreeMap<>();
        map.put("id", id);
        map.put("food", this.food.getJson(Language.en));
        map.put("quantity", this.quantity);
        map.put("price", this.price);
//        map.put("itemStatus", !this.orderItemStatus.equals(OrderItemStatus.IN_CART));
        map.put("orderItemStatus", this.orderItemStatus);

        return map;
    }

    public void addQuantity() {
        this.quantity += 1;
    }

    public double getTotal() {
//        System.out.println(getJson());
        return this.quantity * this.price;
    }

    public OrderItemDTO getOrderItemDTO(Language language) {

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(this.id);
//        orderItemDTO.setCreatedDate(this.createdDate);
        orderItemDTO.setPrice(this.price);
        orderItemDTO.setQuantity(this.quantity);
        orderItemDTO.setFood(this.food.getFoodDTO(language));
        orderItemDTO.setOrderItemStatus(this.orderItemStatus);
        orderItemDTO.setComment(this.getComment());

        return orderItemDTO;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", food=" + food +
                ", quantity=" + quantity +
                ", price=" + price +
                ", comment='" + comment + '\'' +
                ", guest=" + guest +
                ", createdDate=" + createdDate +
                ", orderItemStatus=" + orderItemStatus +
                '}';
    }
}
