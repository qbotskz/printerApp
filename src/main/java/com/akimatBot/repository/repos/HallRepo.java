package com.akimatBot.repository.repos;


import com.akimatBot.entity.custom.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepo extends JpaRepository<Hall, Long> {

}
