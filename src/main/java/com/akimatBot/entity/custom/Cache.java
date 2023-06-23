package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.CacheTypes;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Setter
@Getter
public class Cache {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private CacheTypes cacheType;
    private Date cacheTime;
}
