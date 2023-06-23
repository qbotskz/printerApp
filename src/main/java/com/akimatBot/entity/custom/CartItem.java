package com.akimatBot.entity.custom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
public class CartItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long clientChatId;

    @ManyToOne
    private Desk desk;

    @ManyToOne
    private Food food;

    private int quantity;


    public CartItem() {

    }

    public CartItem(long clientChatId, Food food, int quantity) {
        this.clientChatId = clientChatId;
        this.food = food;
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id == cartItem.id && Objects.equals(clientChatId, cartItem.clientChatId) && Objects.equals(food, cartItem.food);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientChatId, food);
    }

    public void quantityPlusOne() {
        this.quantity++;
    }
}
