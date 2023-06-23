package com.akimatBot.repository.repos;

import com.akimatBot.entity.standart.Property;
import com.akimatBot.utils.components.DataRec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertiesRepo extends JpaRepository<Property, Long> {
    Property findById(int id);

    Property findFirstById(int i);
}
