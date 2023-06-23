package com.akimatBot.entity.standart;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Button {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long     id;
    private String      name;
    private Integer     commandId;
    private String      url;
    private int         langId;
    private Boolean     requestContact;
    private Integer     messageId;
}
