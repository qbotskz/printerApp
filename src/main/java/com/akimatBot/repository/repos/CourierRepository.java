package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.Courier;
import com.akimatBot.entity.standart.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    Courier findCourierByUser(User user);
}
