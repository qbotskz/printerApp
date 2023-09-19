package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.WaiterShift;
import com.akimatBot.entity.standart.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaiterShiftRepo extends JpaRepository<WaiterShift, Long> {

    boolean existsByWaiterCodeAndClosingTimeIsNull(long chatId);

//    WaiterShift findFirstByWaiterCodeAndClosingTimeIsNullOrderByIdDesc(long chatId);

//    boolean existsByWaiterCodeAndOpeningTimeIsNotNullAndClosingTimeIsNull(long chatId);

    @Query("select case when (u.currentShift is not null) then true else false end from Employee u where u.code = ?1")
    boolean hasOpenedShift(long code);

    @Query("select case when (u.currentShift is not null) then true else false end from Employee u where u.chatId = ?1")
    boolean hasOpenedShiftByChatId(long chatId);

    @Query("select u.currentShift from Employee u where u.code = ?1")
    WaiterShift getCurrentShift(long code);

    @Query("select u.currentShift from Employee u where u.chatId = ?1")
    WaiterShift getCurrentShiftByChatId(long chatId);

    @Query("select shift.waiter from WaiterShift shift where shift.closingTime is null and shift.waiter.deleted = false")
    List<Employee> getActiveWaiters();

    @Modifying
    @Query("update WaiterShift shift set shift.closingTime = current_timestamp where shift.closingTime is null")
    void closeAllShifts();

    boolean existsByWaiterChatIdAndClosingTimeIsNull(long waiterChatId);
}
