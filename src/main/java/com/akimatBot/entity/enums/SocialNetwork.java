package com.akimatBot.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum SocialNetwork {

    telegram(1),
    instagram(2),
    facebook(3);

    private final int id;


    public static SocialNetwork getById(int id) {
        for (SocialNetwork socialNetwork : values()) {
            if (socialNetwork.id == id)
                return socialNetwork;
        }
        return telegram;
    }
}
