package com.akimatBot.repository.repos;

import com.akimatBot.entity.standart.LanguageUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageUserRepository extends JpaRepository<LanguageUser, Long> {

    LanguageUser findByChatId(long chatId);
}
