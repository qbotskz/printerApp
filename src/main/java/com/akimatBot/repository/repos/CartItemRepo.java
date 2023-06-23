package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.CartItem;
import com.akimatBot.entity.custom.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByClientChatIdOrderById(long chatId);
//
    List<CartItem> findAllByDeskIdOrderById(long tableId);

    CartItem findByFoodAndClientChatId(Food food, long chatId);

    CartItem findByFoodIdAndDeskId(long foodId, long tableId);

    CartItem findByIdAndClientChatId(long id, long chatId);

    List<CartItem> deleteCartItemsByClientChatId(long chatId);

    List<CartItem> deleteCartItemsByDeskId(long deskId);
}
