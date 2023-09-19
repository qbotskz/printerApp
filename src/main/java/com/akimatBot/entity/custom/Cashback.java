package com.akimatBot.entity.custom;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "cashback")
public class Cashback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Integer cashbackPercentage;
}
