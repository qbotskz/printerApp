package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.PaymentMethod;
import com.akimatBot.entity.custom.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, Long> {

    List<PaymentMethod> findAllByActiveIsTrueOrderById();

}
