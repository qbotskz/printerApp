package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.CacheTypes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
public class PDFFilesToPrint {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date date;
    private String fileName;

    @Column(columnDefinition = "boolean default false")
    private boolean printed;
}
