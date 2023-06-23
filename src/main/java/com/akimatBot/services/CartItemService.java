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


    public List<CartItem> findAllCartItemsOfUser(long chatId) {
        return cartItemRepo.findAllByClientChatIdOrderById(chatId);
    }
    public List<CartItem> findAllCartItemsOfTable(long tableId) {
        return cartItemRepo.findAllByDeskIdOrderById(tableId);
    }

    public CartItem findByBookAndTable(long foodId, long tableId){

        return cartItemRepo.findByFoodIdAndDeskId(foodId, tableId);
    }
    public CartItem findByBookAndChatId(Food food, long chatId){

        return cartItemRepo.findByFoodAndClientChatId(food, chatId);
    }

    public CartItem findByIdAndChatId(long id, long chatId){
        return cartItemRepo.findByIdAndClientChatId(id, chatId);
    }


    @Transactional
    public CartItem save(CartItem newCartItem) {
        return cartItemRepo.save(newCartItem);
    }

    @Transactional
    public CartItem addToCart(Long bookId, long chatId){

        CartItem cartItem;
        cartItem = this.findByBookAndChatId(foodService.findById(bookId), chatId);

        if (cartItem == null){
            cartItem = new CartItem();
            cartItem.setQuantity(1);
            cartItem.setFood(foodService.findById(bookId));
            cartItem.setClientChatId(chatId);
            this.save(cartItem);
        }
        else {
            cartItem.quantityPlusOne();
            cartItem =  this.save(cartItem);
        }
        return cartItem;
    }
    @Transactional
    public CartItem addToCartFromWaiter(long bookId, long tableId){

        CartItem cartItem;
        cartItem = this.findByBookAndTable(bookId, tableId);

        if (cartItem == null){
            cartItem = new CartItem();
            cartItem.setDesk(deskRepo.getOne(tableId));
            cartItem.setQuantity(1);
            cartItem.setFood(foodService.findById(bookId));
//            cartItem.setClientChatId(chatId);
            this.save(cartItem);
        }
        else {
            cartItem.quantityPlusOne();
            cartItem =  this.save(cartItem);
        }
        return cartItem;
    }

    @Transactional
    public boolean decreaseCartItemQuantity(long bookId, long chatId){

        try {
            CartItem cartItem;
            cartItem = this.findByBookAndChatId(foodService.findById(bookId), chatId);

            int quantity = cartItem.getQuantity();

            quantity--;
            cartItem.setQuantity(quantity);

            if (quantity <= 0) {
                this.delete(cartItem);
            } else {
                this.save(cartItem);
            }

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    @Transactional
    public void delete(CartItem newCartItem) {
        cartItemRepo.delete(newCartItem);
    }

    @Transactional
    public void clearUserCart(long chatId) {
        cartItemRepo.deleteCartItemsByClientChatId(chatId);
    }
    @Transactional
    public void clearDeskCart(long deskId) {
        cartItemRepo.deleteCartItemsByDeskId(deskId);
    }
}
