package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.enums.OrderItemStatus;
import com.akimatBot.entity.enums.OrderStatus;
import com.akimatBot.entity.enums.OrderType;
import com.akimatBot.entity.standart.Employee;
import com.akimatBot.entity.standart.User;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.services.WaiterService;
import com.akimatBot.utils.DateUtil;
import com.akimatBot.web.dto.FoodOrderDTO;
import com.akimatBot.web.dto.GuestDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

//@Entity(name = "orders")
@Getter
@Setter
@Entity
public class FoodOrder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date createdDate;
    @ManyToOne
    private Employee waiter;

    @ManyToOne
    Desk desk;

    @OneToMany(mappedBy="foodOrder")
    private List<Guest> guests;

    private Boolean deliverNeed = false;

    private String address;


    private Date completionDate;

    private OrderType orderType;

    @OneToOne
    Cheque cheque;

    @Enumerated
    OrderStatus orderStatus;


    public FoodOrder() {

    }
    public List<Guest> getGuests() {
        guests = TelegramBotRepositoryProvider.getGuestRepo()
                .findAllByFoodOrderAndDeletedFalseOrderByIdAsc(this);
        return guests;
    }
    public long getGuestsSize() {
        return  TelegramBotRepositoryProvider.getGuestRepo()
                .getGuestsSizeOfOrder(this.id);
    }



    public void addGuest(Guest guest){
        if (guests == null)
            guests = new ArrayList<>();

        guests.add(guest);
    }


    public User getClientByChatId(long chatId) {
        if (guests!= null){
            for (Guest guest : guests){
                if (guest.getClient().getChatId() == chatId){
                    return guest.getClient();
                }
            }
        }
        return null;
    }

    public void minusItem(OrderItem orderItem, int quantity) {

        if (orderItem.getQuantity() == quantity){
            TelegramBotRepositoryProvider.getOrderItemRepository().delete(orderItem);
        }
        else {
            orderItem.setQuantity(orderItem.getQuantity() - quantity);
            TelegramBotRepositoryProvider.getOrderItemRepository().save(orderItem);
        }
    }

    public FoodOrderDTO getFoodOrderDTO(Language language, Long code) {

        FoodOrderDTO foodOrderDTO = new FoodOrderDTO();
        foodOrderDTO.setId(this.id);
        foodOrderDTO.setOrderStatus(this.orderStatus);
        foodOrderDTO.setDeliverNeed(this.deliverNeed);
        foodOrderDTO.setAddress(this.address);
        foodOrderDTO.setOrderType(this.orderType);
        foodOrderDTO.setCreatedDate(DateUtil.getOnlyOClock(this.createdDate));
//        foodOrderDTO.setCompletionDate(this.completionDate);
        foodOrderDTO.setCheque(this.cheque.getChequeDTO());
        foodOrderDTO.setGuests(getGuestsDTO(language));
        foodOrderDTO.setPersonalCanChange(getOpportunity(code));
        foodOrderDTO.setWaiter(WaiterService.getWaiterByUserSimple(this.getWaiter()));

        return foodOrderDTO;
    }
    public FoodOrderDTO getFoodOrderDTO(Language language) {

        FoodOrderDTO foodOrderDTO = new FoodOrderDTO();
        foodOrderDTO.setId(this.id);
        foodOrderDTO.setOrderStatus(this.orderStatus);
        foodOrderDTO.setDeliverNeed(this.deliverNeed);
        foodOrderDTO.setAddress(this.address);
        foodOrderDTO.setOrderType(this.orderType);
        foodOrderDTO.setCreatedDate(DateUtil.getOnlyOClock(this.createdDate));
        foodOrderDTO.setCreatedDateInDate(DateUtil.getDbMmYyyyHhMmSs(this.createdDate));
//        foodOrderDTO.setCompletionDate(this.completionDate);
        foodOrderDTO.setCheque(this.cheque.getChequeDTO());
        foodOrderDTO.setGuests(getGuestsDTO(language));
//        foodOrderDTO.setPersonalCanChange(getOpportunity(code));
        foodOrderDTO.setWaiter(WaiterService.getWaiterByUserSimple(this.getWaiter()));

        return foodOrderDTO;
    }
    public FoodOrderDTO getFoodOrderDTOByChatId(Language language, Long chatId) {

        FoodOrderDTO foodOrderDTO = new FoodOrderDTO();
        foodOrderDTO.setId(this.id);
        foodOrderDTO.setOrderStatus(this.orderStatus);
        foodOrderDTO.setDeliverNeed(this.deliverNeed);
        foodOrderDTO.setAddress(this.address);
        foodOrderDTO.setOrderType(this.orderType);
        foodOrderDTO.setCreatedDate(DateUtil.getOnlyOClock(this.createdDate));
//        foodOrderDTO.setCompletionDate(this.completionDate);
        foodOrderDTO.setCheque(this.cheque.getChequeDTO());
        foodOrderDTO.setGuests(getGuestsDTO(language));
        foodOrderDTO.setPersonalCanChange(getOpportunityByChatId(chatId));
        foodOrderDTO.setWaiter(WaiterService.getWaiterByUserSimple(this.getWaiter()));

        return foodOrderDTO;
    }

    private Boolean getOpportunityByChatId(Long chatId) {
        if (chatId != null) {
            return this.waiter.getChatId() == chatId &&
                    this.orderStatus != OrderStatus.DONE;
        }
        return null;
    }
//    public FoodOrderDTO getFoodOrderDTOMy(Language language) {
//
//        FoodOrderDTO foodOrderDTO = new FoodOrderDTO();
//        foodOrderDTO.setId(this.id);
//        foodOrderDTO.setOrderStatus(this.orderStatus);
//        foodOrderDTO.setDeliverNeed(this.deliverNeed);
//        foodOrderDTO.setAddress(this.address);
//        foodOrderDTO.setOrderType(this.orderType);
//        foodOrderDTO.setCreatedDate(this.createdDate);
//        foodOrderDTO.setCompletionDate(this.completionDate);
//        foodOrderDTO.setCheque(this.cheque.getChequeDTO());
//        foodOrderDTO.setGuests(getGuestsDTO(language));
//        foodOrderDTO.setPersonalCanChange(true);
//
//        return foodOrderDTO;
//    }

    private Boolean getOpportunity(Long code) {
        if (code != null) {
            return this.waiter.getCode().equals(code) &&
                    this.orderStatus != OrderStatus.DONE;
        }
        return null;
    }

    private List<GuestDTO> getGuestsDTO(Language language) {

        List<GuestDTO> items = new ArrayList<>();

        for (Guest guest :  getGuests()) {
            items.add(guest.getGuestDTO(language));
        }

        return items;
    }

//    @Override
//    public String toString() {
//        return "FoodOrder{" +
//                "id=" + id +
//                ", createdDate=" + createdDate +
//                ", client=" + client +
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



//    public void addTotal(int totalPrice) {
//        this.total += totalPrice;
//    }
}
