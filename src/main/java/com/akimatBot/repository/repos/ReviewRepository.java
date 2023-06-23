package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByOrderByUploadedDateDesc();
}
