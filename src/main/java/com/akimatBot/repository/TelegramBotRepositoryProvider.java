package com.akimatBot.repository;


import com.akimatBot.repository.repos.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TelegramBotRepositoryProvider {

    @Getter
    @Setter
    private static MessageRepository messageRepository;

    @Getter
    @Setter
    private static KeyboardMarkUpRepository keyboardMarkUpRepository;


    @Getter
    @Setter
    private static UserRepository userRepository;

    @Getter
    @Setter
    private static CartItemRepo cartItemRepo;

    @Getter
    @Setter
    private static ButtonRepository buttonRepository;

    @Getter
    @Setter
    private static LanguageUserRepository languageUserRepository;


    @Getter
    @Setter
    private static PropertiesRepo propertiesRepo;


    @Getter
    @Setter
    private static RoleRepository operatorRepository;


    @Getter
    @Setter
    private static FoodRepository foodRepository;


    @Getter
    @Setter
    private static FoodCategoryRepo foodCategoryRepo;


    @Getter
    @Setter
    private static OrderRepository orderRepository;


    @Getter
    @Setter
    private static ReviewRepository reviewRepository;

    @Getter
    @Setter
    private static CourierRepository courierRepository;

    @Getter
    @Setter
    private static SuggestionComplaintRepo suggestionComplaintRepo;


    @Getter
    @Setter
    private static CashbackRepository cashbackRepository;
    @Getter
    @Setter
    private static RestaurantBranchRepo restaurantBranchRepo;

    @Getter
    @Setter
    private static KaspiAccountsRepository kaspiAccountsRepository;

    @Getter
    @Setter
    private static DeskRepo deskRepo;

    @Getter
    @Setter
    private static OrderItemRepository orderItemRepository;


    @Getter
    @Setter
    private static GuestRepo guestRepo;


    @Getter
    @Setter
    private static ChequeRepo chequeRepo;


    @Getter
    @Setter
    private static WaiterShiftRepo waiterShiftRepo;

    @Getter
    @Setter
    private static PaymentTypeRepo paymentTypeRepo;

    @Getter
    @Setter
    private static PaymentRepo paymentRepo;

    @Getter
    @Setter
    private static HallRepo hallRepo;


    @Getter
    @Setter
    private static EmployeeRepository employeeRepository;


    @Autowired
    public TelegramBotRepositoryProvider(MessageRepository messageRepository, KeyboardMarkUpRepository keyboardMarkUpRepository,
                                         UserRepository userRepository, ButtonRepository buttonRepository,
                                         LanguageUserRepository languageUserRepository, RoleRepository operatorRepository,
                                         PropertiesRepo propertiesRepo, FoodRepository foodRepository, FoodCategoryRepo foodCategoryRepo,
                                         OrderRepository orderRepository,
                                         ReviewRepository reviewRepository, CourierRepository courierRepository,
                                         SuggestionComplaintRepo suggestionComplaintRepo, CashbackRepository cashbackRepository,
                                         RestaurantBranchRepo restaurantBranchRepo, KaspiAccountsRepository kaspiAccountsRepository,
                                         DeskRepo deskRepo, CartItemRepo cartItemRepo, OrderItemRepository orderItemRepository,
                                         GuestRepo guestRepo, ChequeRepo chequeRepo, WaiterShiftRepo waiterShiftRepo,
                                         PaymentTypeRepo paymentTypeRepo, PaymentRepo paymentRepo,
                                         HallRepo hallRepo, EmployeeRepository employeeRepository) {

        setMessageRepository(messageRepository);
        setKeyboardMarkUpRepository(keyboardMarkUpRepository);
        setUserRepository(userRepository);
        setButtonRepository(buttonRepository);
        setLanguageUserRepository(languageUserRepository);
        setPropertiesRepo(propertiesRepo);
        setOperatorRepository(operatorRepository);
        setFoodRepository(foodRepository);
        setFoodCategoryRepo(foodCategoryRepo);
        setOrderRepository(orderRepository);
        setReviewRepository(reviewRepository);
        setCourierRepository(courierRepository);
        setSuggestionComplaintRepo(suggestionComplaintRepo);
        setCashbackRepository(cashbackRepository);
        setRestaurantBranchRepo(restaurantBranchRepo);
        setKaspiAccountsRepository(kaspiAccountsRepository);
        setDeskRepo(deskRepo);
        setCartItemRepo(cartItemRepo);
        setOrderItemRepository(orderItemRepository);
        setGuestRepo(guestRepo);
        setChequeRepo(chequeRepo);
        setWaiterShiftRepo(waiterShiftRepo);
        setPaymentTypeRepo(paymentTypeRepo);
        setPaymentRepo(paymentRepo);
        setHallRepo(hallRepo);
        setEmployeeRepository(employeeRepository);
    }

}
