package com.akimatBot.entity.standart;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @Column(name = "value_1")
    private String value1;

    //    private String text;
    private String latitude;
    private String longitude;
}
