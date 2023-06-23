package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.CartItem;
import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.custom.Payment;
import com.akimatBot.entity.custom.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTypeRepo extends JpaRepository<PaymentType, Long> {

    List<PaymentType> findAllByActiveIsTrueAndPaymentMethodId(long methodId);

    PaymentType findById(long id);

}
