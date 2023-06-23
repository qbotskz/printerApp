package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.PrintPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrintPaymentRepo extends JpaRepository<PrintPayment, Long> {

    List<PrintPayment> findAllByOrderById();
}
