package com.akimatBot.web.websocets.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface KitchenPrintEntityRepo extends JpaRepository<PrintKitchenEntity, Long> {

    List<PrintKitchenEntity> findAllByOrderById();


    @Query("delete from PrintKitchenEntity p where p.id = ?1")
    @Modifying
    void deleteByOrderItem(long id);
}
