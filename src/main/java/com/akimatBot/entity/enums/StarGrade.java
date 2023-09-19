package com.akimatBot.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum StarGrade {

    ONE_STAR(1, "⭐"), TWO_STAR(2, "⭐⭐"), THREE_STAR(3, "⭐⭐⭐"), FOUR_STAR(4, "⭐⭐⭐⭐"), FIVE_STAR(5, "⭐⭐⭐⭐⭐");

    private int id;
    private String star;

    public static StarGrade getById(int id) {
        for (StarGrade starGrade : values()) {
            if (starGrade.id == (id)) return starGrade;
        }
        return ONE_STAR;
    }
}
