package com.akimatBot.entity.custom;

import com.akimatBot.entity.standart.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "couriers")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private User user;

    private String tempKpi;

    private boolean isPhotoAllowed;

    private String photoUrl;


    public Courier(User user, Boolean isPhotoAllowed) {
        this.user = user;
        this.isPhotoAllowed = isPhotoAllowed;
    }

    public Courier() {

    }
}
