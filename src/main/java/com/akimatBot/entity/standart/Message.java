package com.akimatBot.entity.standart;

import com.akimatBot.entity.enums.FileType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long     id;
    private String   name;
    private String   photo;
    private Long keyboardId;
    private String   file;
    private FileType fileType;
    private int langId;

    public void setFile(String file, FileType fileType) {
        this.file       = file;
        this.fileType   = fileType;
    }
}
