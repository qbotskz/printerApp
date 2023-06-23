package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.FoodCategory;
import com.akimatBot.entity.standart.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findByIdAndLangId(long messageId, int language);
    @Transactional
    @Modifying
    @Query("update Message set name = ?1 where id = ?2 and langId = ?3")
    void update(String name, long id, int langId);
//    List<Message> findAllByNameContainingAndLangIdOrderById(String name, int langId);
}
