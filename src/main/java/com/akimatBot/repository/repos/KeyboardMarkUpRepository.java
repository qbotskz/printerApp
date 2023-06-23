package com.akimatBot.repository.repos;

import com.akimatBot.entity.standart.Keyboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyboardMarkUpRepository extends JpaRepository<Keyboard, Long> {
    Keyboard findById(long keyboardId);

    boolean findByIdAndInlineIsTrue(int keyboardId);
}
