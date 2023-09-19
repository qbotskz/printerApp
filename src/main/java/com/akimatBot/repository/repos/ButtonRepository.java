package com.akimatBot.repository.repos;

import com.akimatBot.entity.standart.Button;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ButtonRepository extends JpaRepository<Button, Long> {
    Button findByIdAndLangId(long buttonId, int langId);

    Button findByNameAndLangId(String name, int langId);

    List<Button> findAllByNameContainingAndLangIdOrderById(String text, int id);

    @Transactional
    @Modifying
    @Query("update Button set name = ?1 where id = ?2 and langId = ?3")
    void update(String name, long id, int langId);

    long countByNameAndLangId(String name, int langId);
}
