package com.akimatBot.entity.custom;

import com.akimatBot.entity.enums.AppealType;
import com.akimatBot.entity.standart.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "suggestions_complaints")
public class SuggestionComplaint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int     id;
    private String text;

    @ManyToOne
    private User user;

    @ManyToOne
    private User responsible;

    private AppealType appealType;
    private String photoUrl;
    private String videoUrl;
    private String response;

    private boolean answered;

    private Date uploadedDate;
}
