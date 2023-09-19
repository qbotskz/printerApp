package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.StarGrade;
import com.akimatBot.entity.standart.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private StarGrade reviewGrade;
    private String reviewText;
    @ManyToOne
    private User user;

    private Date uploadedDate;
}
