package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.CacheTypes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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
