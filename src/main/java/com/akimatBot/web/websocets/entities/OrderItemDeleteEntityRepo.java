package com.akimatBot.web.websocets.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface OrderItemDeleteEntityRepo extends JpaRepository<OrderItemDeleteEntity, Long> {


    List<OrderItemDeleteEntity> findAllByPrintedFalseOrderById();

    @Query("delete from OrderItemDeleteEntity p where p.id = ?1")
    @Modifying
    void deleteByOrderItem(long id);

    @Transactional
    @Modifying
    @Query("update OrderItemDeleteEntity del set del.printed = true where del.id = ?1")
    void setPrinted(long id);
}