package com.akimatBot.entity.custom;

import com.akimatBot.entity.standart.Employee;
import com.akimatBot.entity.standart.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class WaiterShift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int     id;

    @ManyToOne
    Employee waiter;

    private Date openingTime;
    private Date closingTime;

}
