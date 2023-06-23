//package com.akimatBot.entity.custom;
//
//import com.akimatBot.entity.standart.User;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.*;
//
//@Entity
//@Getter
//@Setter
//public class OrderOfBooks {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    private Date createdDate;
//
//    @ManyToOne
//    private User user;
//
////    private Boolean ticketReceived = false;
////
////    private String ticket;
////
////    private String ticketType;
//
//    private Integer total;
//
//    private Boolean useCashback = false;
//
//    private Integer usedCashback = 0;
//
//    private Integer addedCashback = 0;
//
//    @OneToMany(fetch = FetchType.EAGER)
//    private Set<OrderItem> orderItems;
//
//    private Boolean deliverNeed;
//
//    private String address;
//
//    private int deliveryPrice;
//
//    private Long managerChatId;
//
//    private boolean done = false;
//
//    private Date completionDate;
//
////    private Boolean rejected;
//
//    private Long paymTechOrderId;
////    private Integer paymentPageId;
//    private Boolean paid;
//
//
//    public OrderOfBooks() {
//
//    }
//
//    public Map<Object, Object> getJson(){
//        Map<Object, Object> map = new HashMap<>();
//        map.put("id", id);
//        map.put("createdDate", this.createdDate);
//        map.put("total", this.total);
//        map.put("useCashback", this.useCashback);
//        map.put("addedCashback", this.addedCashback);
//        map.put("orderItems", getOrderItemsJson());
//        map.put("deliverNeed", this.deliverNeed);
//        map.put("deliveryPrice", this.deliveryPrice);
//        map.put("address", this.address);
//        map.put("managerChatId", this.managerChatId);
//        map.put("done", this.done);
//        map.put("completionDate", this.completionDate);
//        map.put("paymTechOrderId", String.valueOf(this.paymTechOrderId));
//        System.out.println("paymTechOrderId " + paymTechOrderId);
//        map.put("paid", this.paid);
//
//        return map;
//    }
//
//    private Object getOrderItemsJson() {
//        List<Map<Object, Object>> items = new ArrayList<>();
//        for (OrderItem item : this.orderItems){
//            items.add(item.getJson());
//        }
//        return items;
//    }
//
//    @Override
//    public String toString() {
//        return "OrderOfBooks{" +
//                "id=" + id +
//                ", createdDate=" + createdDate +
//                ", user=" + user +
//                ", total=" + total +
//                ", useCashback=" + useCashback +
//                ", usedCashback=" + usedCashback +
//                ", addedCashback=" + addedCashback +
//                ", orderItems=" + orderItems +
//                ", deliverNeed=" + deliverNeed +
//                ", address='" + address + '\'' +
//                ", deliveryPrice=" + deliveryPrice +
//                ", managerChatId=" + managerChatId +
//                ", done=" + done +
//                ", completionDate=" + completionDate +
//                ", paymTechOrderId=" + paymTechOrderId +
//                ", paid=" + paid +
//                '}';
//    }
//}
