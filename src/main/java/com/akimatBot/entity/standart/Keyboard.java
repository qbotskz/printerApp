package com.akimatBot.entity.standart;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Keyboard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long     id;
    private String      buttonIds;
    private boolean     inline;
    private String      comment;
}
