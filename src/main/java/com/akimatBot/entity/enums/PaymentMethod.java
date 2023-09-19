package com.akimatBot.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum PaymentMethod {
    KASPI_TRANSFER(1, "\uD83D\uDCB3 Kaspi перевод", "\uD83D\uDCB3 Kaspi перевод"),
    BY_CASH(2, "\uD83D\uDCB5 Наличные", "\uD83D\uDCB5 Акшалай"),
    DEFAULT(3, "-", "-");

    private int id;
    private String nameRu;
    private String nameKz;

    public String getName(int langId) {
        if (langId == 1) {
            return nameRu;
        }
        return nameKz;
    }

    public static PaymentMethod getById(int id) {
        for (PaymentMethod paymentMethod : values()) {
            if (paymentMethod.id == (id)) return paymentMethod;
        }
        return DEFAULT;
    }
}
