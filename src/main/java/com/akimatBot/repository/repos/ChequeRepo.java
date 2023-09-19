package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.Cheque;
import com.akimatBot.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChequeRepo extends JpaRepository<Cheque, Long> {

    Cheque findById(long id);

//    @Modifying
//    @Transactional
//    @Query("update Cheque c set c.total = (select item.price * item.quantity from OrderItem item where item.id = (select g.id from Guest g where g.foodOrder.id = ?1))" +
//            "where c.order.id = ?1")
//    void setTotal(long orderId);


//    @Modifying
//    @Transactional
//    @Query("update Cheque c set c.total = " +
//            "(select sum( (item.price * item.quantity)) from OrderItem item where item.guest.id in (select g.id from Guest g where g.foodOrder.id = ?1))" +
//            "where c.id = (select f.cheque.id from FoodOrder f where f.id = ?1)")
//    void setTotal(long orderId);

    Cheque findByOrderId(long id);

    @Query("select c.order.orderStatus from Cheque c where c.id = ?1")
    OrderStatus getOrderStatus(long chequeId);

    @Query("select g.foodOrder.cheque from Guest g where g.id = ?1")
    Cheque getByGuest(long guestId);

//    @Modifying
//    @Transactional
////    @Query("update Cheque c set c.total = " +
////            "(select sum( (item.price * item.quantity)) from OrderItem item where item.guest.id in (select g.id from Guest g where g.foodOrder.id = ?1))" +
////            "where c.id = (select f.cheque.id from FoodOrder f where f.id = ?1)")
//
//
////    @Query("update Cheque c set c.total = " +
////            " (select (item.price * item.quantity) from OrderItem item where item.id ?1) " +
////            "where c.id = (select)")
//
//    @Query("update Cheque c set c.total = (cast(  c.total  as double ) ) + " +
//            "cast( (select (item.price) from OrderItem item where item.id = ?1) as double ) " +
//            "where c.id = (select item.guest.foodOrder.cheque.id from  OrderItem item where item.id = ?1) ")
//
//
//    void addTotal(long orderItemId);

    @Query("select sum(item.price * item.quantity)  from OrderItem item where item.guest.foodOrder.cheque.id = ?1 " +
            " and (item.orderItemStatus = 0 or item.orderItemStatus = 1 or item.orderItemStatus = 2)")
    Double getTotal(long chequeId);

}
