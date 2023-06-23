package com.akimatBot.repository.repos;


import com.akimatBot.entity.custom.FoodOrder;
import com.akimatBot.entity.custom.Guest;
import com.akimatBot.entity.custom.OrderItem;
import com.akimatBot.entity.enums.OrderItemStatus;
import com.akimatBot.entity.enums.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    OrderItem findById(long id);

    OrderItem getById(long id);
//    List<OrderItem> findAllByGuestOrderByIdAscOrderItemStatusDesc(Guest guest);

    @Query(value = "select * from order_item item where item.guest_id =?1 and item.order_item_status != 3 order by item.id asc, item.order_item_status desc", nativeQuery = true)
    List<OrderItem> findAllByGuestOrderByIdAscOrderItemStatusDesc(long guest);
    @Query("select item from OrderItem item where item.guest.foodOrder.id = ?1 and item.orderItemStatus = 0")
    List<OrderItem> getAllByOrderInCart(long orderId);

    @Query("select item.guest.foodOrder.orderStatus from OrderItem item where item.id = ?1")
    OrderStatus getOrderStatusOfOrderItem(long orderItemId);

    OrderItem findByFoodIdAndGuestIdAndOrderItemStatus(long id, long guestId, OrderItemStatus orderItemStatus);

    @Modifying
    @Transactional
    @Query("delete from OrderItem item where item.guest.foodOrder.id =?1 and item.orderItemStatus = ?2")
    void deleteByStatusOfOrder(long orderId, OrderItemStatus orderItemStatus);

    @Modifying
    @Transactional
    default void deleteInCartsOfOrder(long orderId){
//        this.deleteByStatusOfOrder(orderId, OrderItemStatus.IN_CART);
        this.deleteAllByGuestFoodOrderIdAndOrderItemStatus(orderId, OrderItemStatus.IN_CART);
    }

    void deleteAllByGuestFoodOrderIdAndOrderItemStatus(long orderId, OrderItemStatus orderItemStatus);

    @Modifying
    @Transactional
    @Query(value = "update order_item item set order_item_status = ?1 " +
            "where item.guest_id in (select g.id from guest g where g.food_order_id = ?2) and item.order_item_status = ?3", nativeQuery = true)
//    void cookItems(long orderId,long statusId, );
    void cookItems(long forSet, long orderId,long equal);

    @Modifying
    @Transactional
    @Query("update OrderItem set orderItemStatus = 2 where guest.foodOrder.id= 412")
    void test();

//    void
//    @Transactional
//    @Modifying
//    default void cookOrderItems(long orderId){
//
//    }

    @Query("select item.price * item.quantity from OrderItem item where item.guest.foodOrder.id = ?1")
    double getTotal(long orderId);

    @Transactional
    @Modifying
    @Query(value = "update food f set remains = (case" +
            "                                 when f.remains is not null then" +
            "                                     (f.remains - (select sum( item.quantity) from order_item item" +
            "                                                   where item.guest_id in (select g.id from guest g where g.food_order_id = ?1)" +
            "                                                     and item.food_id = f.id" +
            "                                                     and item.order_item_status = 0))" +
            "                                 else null" +
            "    END ) where f.id in" +
            "                            (select item.food_id from order_item item where item.order_item_status = 0" +
            "                                                                        and item.guest_id in" +
            "                                                                            (select g.id from guest g where g.food_order_id = ?1))", nativeQuery = true)
    void editQuantity(long orderId);


    @Query(value = "SELECT sum(f.remains) >= sum(item.quantity) " +
            "FROM order_item item" +
            "         RIGHT JOIN  food f ON item.food_id = f.id and f.remains is not null " +
            "WHERE item.guest_id IN (SELECT g.id FROM guest g WHERE g.food_order_id = ?1)" +
            "  AND item.order_item_status = 0", nativeQuery = true)
    Boolean isAvailableRemains(long orderId);

    @Query("select sum (item.price * item.quantity) from OrderItem item where item.guest.foodOrder.id = ?1 and item.orderItemStatus = 0")
    Double getTotalInCartsOfOrder(long orderId);

    @Query("update OrderItem item set item.orderItemStatus = 3  where item = ?1")
    @Modifying
    void setDelete(OrderItem orderItem);


}
