package com.akimatBot.entity.standart;

import com.akimatBot.entity.enums.Gender;
import com.akimatBot.entity.enums.Language;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Data
@Entity(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long chatId;

    private String phone;

    private String fullName;

    private String userName;

    private Language language;

    private Gender gender;


    private double cashback;


}
