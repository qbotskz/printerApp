package com.akimatBot.repository.repos;

import com.akimatBot.entity.standart.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(long id);
}
