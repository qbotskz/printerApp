package com.akimatBot.entity.custom;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "kaspi_accounts")

public class KaspiAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long     id;
    private String name;
    private String phone;


}
