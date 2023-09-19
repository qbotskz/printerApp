package com.akimatBot.repository.repos;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.standart.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByChatId(long chatId);

    User findById(long id);

    User findByPhone(String phone);

    User getByChatId(long chatId);

    User findFirstByChatId(long chatId);

    Integer countUserByChatId(long chatId);

    @Query("select us.cashback from  users us where us.chatId = ?1")
    Integer getCashback(long chatId);

    @Query("select user.language from users user WHERE user.chatId =?1")
    Language getLanguageByChatId(long chatId);

    boolean existsByChatIdAndPhoneNotNull(long chatId);

}
