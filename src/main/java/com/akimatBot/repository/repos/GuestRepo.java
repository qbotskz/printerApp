package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.CartItem;
import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.custom.FoodOrder;
import com.akimatBot.entity.custom.Guest;
import com.akimatBot.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GuestRepo extends JpaRepository<Guest, Long> {

    List<Guest> findAllByFoodOrderAndDeletedFalseOrderByIdAsc(FoodOrder foodOrder);

    @Query("select g.foodOrder.orderStatus from Guest  g where g.id = ?1")
    OrderStatus getOrderStatusOfGuest(long guestId);


//    @Query("select g.orderItems.size from Guest  g where g.id = ?1")
//    long getOrderItemsSize(long guestId);

    @Query("select count(item) from OrderItem item where item.guest.id = ?1 and item.orderItemStatus <> 3")
    long getOrderItemsSize(long guestId);

    @Query("select count (g) from Guest  g where g.foodOrder.id = ?1 and g.deleted = false")
    long getGuestsSizeOfOrder(long orderId);

    @Query("update Guest g set g.deleted = true where g.id = ?1")
    @Modifying
    void delete(long id);
}
