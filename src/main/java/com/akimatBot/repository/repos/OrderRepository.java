package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.FoodOrder;
import com.akimatBot.entity.custom.PaymentTypeReport;
import com.akimatBot.entity.enums.OrderStatus;
import com.akimatBot.entity.enums.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
@Transactional
public interface OrderRepository extends JpaRepository<FoodOrder, Integer> {

    FoodOrder findOrderById(long id);

    @Query(value = "select fo.order_status from food_order fo where fo.order_status != 2 and fo.desk_id = ?1  ORDER BY ID DESC LIMIT 1", nativeQuery = true)
    OrderStatus getStatusOfOrder(long deskId);

    @Query(value = "select fo.orderStatus from FoodOrder fo where fo.id = ?1")
    OrderStatus getStatusOfOrderByOrderId(long orderId);

//    List<Order> findOrdersByCourierIsNullAndFinishedFalse();
//    List<Order> findOrdersByOperatorIsNotNullAndFinishedIsFalse();

    //    FoodOrder findByIdAndDoneIsFalse(long id);
    FoodOrder findById(long id);
//    List<Order> findAllByUser_ChatId(String chatId);

//    @Query("select o from Orders o where (o.createdDate > ?2 or o.paid = true) and o.client.chatId = ?1")
//    List<Orders> getActuallyOrders(long chatId, Date date);

//    default List<Orders> getActuallyOrders(long chatId, int minute){
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, -minute);
//        return getActuallyOrders(chatId, calendar.getTime());
//    }

    @Query("select new PaymentTypeReport(pt, sum(p.amount), count(p) ) from PaymentType pt left join Payment p on pt.id = p.paymentType.id " +
            " where p.cheque.id in " +
            "(select fo.cheque.id from FoodOrder fo where fo.createdDate between ?1 and ?2 and fo.orderStatus = 2) group by pt")
    List<PaymentTypeReport> getTotalForPaymentTypesJPQL(Date openingTime, Date closingTime);


//    @Query("select o from OrderOfBooks o where DATEDIFF(minute,0,current_timestamp)+15 ")
//    @Query("select current_timestamp + '10 minutes'::interval")

    List<FoodOrder> findAllByCreatedDateBetweenOrderById(Date start, Date end);

    //    List<FoodOrder> findAllByDoneIsFalseOrderByCreatedDateDesc();
    //    List<OrderOfBooks> findAllByDoneFalseAndTicketReceivedTrueOrderByCreatedDateDesc();
//    List<FoodOrder> findAllByDoneFalseOrderByCreatedDateDesc();
//    List<FoodOrder> findAllByDoneFalseAndPaidTrueOrderByCreatedDateDesc();
//    List<FoodOrder> findAllByDoneFalseAndPaidTrueOrderByCreatedDate();
//    List<FoodOrder> findAllByDoneFalseAndWaiterIsNullOrderByCreatedDate();
    List<FoodOrder> findAllByOrderStatusAndWaiterIsNullOrderByCreatedDate(OrderStatus orderStatus);


    //    List<FoodOrder> findAllByPaidIsNullOrderByCreatedDateDesc();
    @Query(value = "select g.food_order_id from Guest g where g.client_id = (select u.id from users u where u.chat_id  = ?1)" +
            "            and (select fo.order_status = 2 from food_order fo where fo.id = g.food_order_id and fo.order_type = ?2 )" +
            "            ORDER BY ID DESC LIMIT 1", nativeQuery = true)
    FoodOrder getLastClientOrder(long chatId, OrderType in_the_restaurant);


//    FoodOrder findLastByClientChatIdAndOrderTypeAndDoneIsFalse(long chatId, OrderType in_the_restaurant);

    //todo asdasdhuasiufhsdeoighawgjhaerkh;
//    default FoodOrder findLastByClientChatIdAndOrderTypeAndDoneIsFalse(long chatId, OrderType in_the_restaurant){
//        return findLimited( chatId, in_the_restaurant, PageRequest.of(0, 10));
//    }

    //    List<FoodOrder> findAllByWaiterChatIdAndDoneIsFalse(long chatId);
    List<FoodOrder> findAllByWaiterChatIdAndOrderStatusNot(long chatId, OrderStatus orderStatus);

    List<FoodOrder> findAllByWaiterCodeAndOrderStatusNot(long code, OrderStatus orderStatus);

    ///////////////////////////////////////////////////
    @Query("select fo from FoodOrder fo where fo.waiter.chatId = ?1 and fo.orderStatus = 2 and fo.createdDate >= fo.waiter.currentShift.openingTime")
    List<FoodOrder> getDoneOrdersOfWaiterByChatId(long chatId);

    ///////////////////////////////////////////////////
    List<FoodOrder> findAllByWaiterChatIdAndOrderStatus(long chatId, OrderStatus orderStatus);

    List<FoodOrder> findAllByDeskIdOrderById(long deskId);

    //    List<FoodOrder> findAllByDeskIdAndDoneIsFalse(long deskId);
    List<FoodOrder> findAllByDeskIdAndOrderStatus(long deskId, OrderStatus orderStatus);

    FoodOrder findFirstByDeskIdAndOrderStatusNotOrderByIdDesc(long deskId, OrderStatus orderStatus);

    FoodOrder findLastByDeskIdAndOrderTypeAndOrderStatusNot(long deskId, OrderType in_the_restaurant, OrderStatus orderStatus);

//    boolean existsByWaiterChatIdAndDoneIsFalse(long chatId);


    @Query("select count (ord) FROM FoodOrder ord where ord.waiter.code = ?1 and ord.orderStatus <> 3 and ord.orderStatus <> 2")
    long countActiveOrders(long code);

    //    boolean existsByWaiterCodeAndOrderStatusNot(long code, OrderStatus orderStatus);
    boolean existsByOrderStatusNot(OrderStatus orderStatus);

    @Query("select sum(item.price * item.quantity " +
            " - (item.price * item.quantity * item.guest.foodOrder.cheque.discount/100) " +
            " + (item.price * item.quantity * item.guest.foodOrder.cheque.service/100) )  " +
            "from OrderItem item where item.guest.foodOrder.waiter.code = ?1 " +
            " and (item.orderItemStatus = 0 or item.orderItemStatus = 1 or item.orderItemStatus = 2) " +
            "and item.guest.foodOrder.createdDate > item.guest.foodOrder.waiter.currentShift.openingTime and item.guest.foodOrder.orderStatus = 2")
    Double getClosedOrdersCash(Long code);

    @Query("select count (fo.desk.id) from FoodOrder fo where fo.waiter.id = ?1 and fo.orderStatus <> 2 and fo.orderStatus <> 3")
    long getDesksSize(long userId);

    @Query(value = "update food_order set desk_id = ?2 where id = ?1", nativeQuery = true)
    @Modifying
    void changeDesk(long orderId, long toDeskId);

    @Query("select fo.desk.id from FoodOrder fo where fo.id = ?1")
    long getDeskIdOfOrder(long orderId);

    @Query(value = "update food_order set waiter_id = ?2 where id = ?1", nativeQuery = true)
    @Modifying
    void changeWaiter(long orderId, long toWaiterId);

    @Query("select fo.createdDate from FoodOrder fo where fo.id = ?1")
    Date getCreatedDateByOrderId(long orderId);

    @Query("select fo.desk.id from FoodOrder fo where fo.id = ?1")
    long getDeskNumberById(long orderId);

    @Query("select fo.waiter.fullName from FoodOrder fo where fo.id = ?1")
    String getWaiterNameById(long orderId);

    @Query("select fo.desk.hall.name from FoodOrder fo where fo.id = ?1")
    String getHallNameById(long orderId);

    @Query("select count (ord) FROM FoodOrder ord where ord.waiter.chatId = ?1 and ord.orderStatus <> 3 and ord.orderStatus <> 2")
    long countActiveOrdersByChatId(long chatId);

    boolean existsByWaiterChatIdAndOrderStatusNot(long chatId, OrderStatus done);


    @Query("select case when  fo.waiter.code = ?1 then true else false end from  FoodOrder fo where fo.id = ?2")
    boolean hisOrder(Long code, long orderId);

    @Query("select case when  fo.waiter.code = ?1 then true else false end from  FoodOrder fo where fo.cheque.id = ?2")
    boolean hisOrderByChequeId(Long code, long chequeId);

    @Query("select case when  guest.foodOrder.waiter.code = ?1 then true else false end from  Guest guest where guest.id = ?2")
    boolean hisOrderByGuestId(Long code, long guestId);

    @Query("select case when  item.guest.foodOrder.waiter.code = ?1 then true else false end from  OrderItem item where item.id = ?2")
    boolean hisOrderByOrderItemId(Long code, long orderItemId);

    @Query("select case when  count(item.id) = 0 then true else false end from  OrderItem item where item.guest.foodOrder.id = ?1")
    boolean isEmpty(long orderId);


    @Query("select sum (payment.amount - payment.change) from Payment payment  where payment.cheque.order.createdDate between ?1 and ?2 and payment.paymentType.id = 1")
    Double getTotalBetween(Date openingTime, Date date);


    @Query("select count(fo) from FoodOrder fo where fo.createdDate between ?1 and ?2 and fo.orderStatus = 2")
    long getQuantityBetween(Date openingTime, Date closingTime);

    @Query("update FoodOrder g set g.orderStatus = 3 where g.id = ?1")
    @Modifying
    void delete(long id);
}
