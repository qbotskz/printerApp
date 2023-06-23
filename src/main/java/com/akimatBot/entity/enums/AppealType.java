package com.akimatBot.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum AppealType {

    SUGGESTION(1, "Предложение"), COMPLAINT(2, "Жалоба");
    private int id;
    private String name;

}
