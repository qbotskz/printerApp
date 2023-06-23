package com.akimatBot.repository.repos;


import com.akimatBot.entity.custom.Desk;
import com.akimatBot.entity.custom.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DeskRepo extends JpaRepository<Desk, Long> {


    @Query("select count(fo) = 0 from FoodOrder fo where fo.orderStatus <> 2 and fo.desk.id = ?1")
    boolean isActiveDesk(long deskId);


    // todo переписать
    // todo order by d.id
//    @Query(value="select distinct d.id, d.number, d.available from desk d RIGHT JOIN food_order o ON d.id=o.desk_id where (select u.id from users u where u.chat_id  = ?1) =  o.waiter_id order by d.id ", nativeQuery = true)
//    List<Desk> getActiveDesks(long waiterChatId);


    @Query("select d from Desk d where d.currentOrder is not null and d.currentOrder.waiter.chatId = ?1 and d.currentOrder.orderStatus <> 3 order by d.id")
    List<Desk> getActiveDesks(long waiterChatId);

    @Query("select d from Desk d where d.currentOrder is not null and d.currentOrder.waiter.code = ?1 and d.currentOrder.orderStatus <> 3 order by d.id")
    List<Desk> getActiveDesksByCode(long code);


    Desk findById(long id);

    @Modifying
    @Query(value = "update desk set current_order_id = null where id = ?1", nativeQuery = true)
    void setCurrentOrderNull(long id);

    @Query("select case when d is not null and d.currentOrder is null then true else false end from Desk d where d.id = ?1")
    boolean existsAndFree(long toDeskId);

    @Modifying
    @Query(value = "update desk set current_order_id = ?1 where id = ?2", nativeQuery = true)
    void setCurrentOrder(long orderId, long deskId);

    @Query("select d from Desk d where d.currentOrder.id = ?1")
    Desk getDeskByOrderId(long orderId);

    Desk findByCurrentOrderId(long orderId);

    List<Desk> findAllByOrderByNumber();

}