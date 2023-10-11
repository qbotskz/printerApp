package com.akimatBot.services;

import com.akimatBot.entity.custom.CartItem;
import com.akimatBot.entity.custom.Food;
import com.akimatBot.repository.repos.CartItemRepo;
import com.akimatBot.repository.repos.DeskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CartItemService {
    private final CartItemRepo cartItemRepo;

    @Autowired
    FoodService foodService;

    @Autowired
    DeskRepo deskRepo;

    @Autowired
    public CartItemService(CartItemRepo cartItemRepo) {
        this.cartItemRepo = cartItemRepo;
    }

    @Transactional
    public CartItem save(CartItem newCartItem) {
        return cartItemRepo.save(newCartItem);
    }

    @Transactional
    public void delete(CartItem newCartItem) {
        cartItemRepo.delete(newCartItem);
    }
}
