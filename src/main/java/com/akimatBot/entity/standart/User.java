package com.akimatBot.entity.standart;

import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.custom.FoodOrder;
import com.akimatBot.entity.custom.RestaurantBranch;
import com.akimatBot.entity.custom.WaiterShift;
import com.akimatBot.entity.enums.Gender;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.enums.OrderType;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//@Data
@Entity(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long     id;

    private long        chatId;

    private String      phone;

    private String      fullName;

    private String      userName;

    private Language language;

    private Gender gender;


    private double cashback;


}
