package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.Cheque;
import com.akimatBot.entity.custom.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

    Payment findById(long id);

    @Query("select sum(p.amount) from Payment p where p.cheque.id = ?1")
    double getTotalOfCheque(long id);

    List<Payment> findAllByCheque(Cheque cheque);

    List<Payment> findAllByChequeAndPrepaymentFalse(Cheque cheque);

}
