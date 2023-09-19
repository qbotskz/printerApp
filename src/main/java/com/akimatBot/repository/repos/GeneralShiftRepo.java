package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.GeneralShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralShiftRepo extends JpaRepository<GeneralShift, Long> {


    boolean existsByClosingTimeIsNull();

    GeneralShift findFirstByClosingTimeIsNullOrderByIdDesc();

    boolean existsByOpeningTimeIsNotNullAndClosingTimeIsNull();
}
