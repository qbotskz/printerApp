package com.akimatBot.entity.standart;

import com.akimatBot.entity.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LanguageUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long chatId;
    private Language language;
}
