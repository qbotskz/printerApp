package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.Cashback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashbackRepository extends JpaRepository<Cashback, Integer> {
    Cashback findTopByOrderByIdDesc();
}
