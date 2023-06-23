package com.akimatBot.repository.repos;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.standart.Button;
import com.akimatBot.entity.standart.Employee;
import com.akimatBot.entity.standart.Role;
import com.akimatBot.entity.standart.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByCodeAndDeletedFalse(long code);
    Employee findByIdAndDeletedFalse(long id);
    boolean existsByCodeAndDeletedFalse(long code);

    Employee findByLoginAndDeletedFalse(String login);

    @Query("select user.roles from Employee user where user.login = ?1 and user.deleted = false")
    List<Role> getRoles(String login);

    @Query( value = "select  count(ro) != 0 from employee_roles ro where ro.roles_id = (select r.id from role r where r.name = ?2)" +
            "and ro.employee_id = (select u.id from employee u where u.code = ?1)", nativeQuery = true)
    boolean hasRoleByCode(long code, String roleName);


    @Query("update Employee u set u.currentShift = null")
    @Modifying
    void closeAllShifts();

    @Query("update Employee u set u.currentShift = null where u.code = ?1")
    @Modifying
    void setNullToShift(long code);

    @Query("select case when u.currentShift is not null then true else false end from Employee u where u.id = ?1 and u.deleted = false")
    boolean isOpenShiftById(long toWaiterId);

    @Query("update Employee u set u.currentShift = null where u.chatId = ?1")
    @Modifying
    void setNullToShiftByChatId(long chatId);

    @Query("select user.fullName from Employee user where user.chatId = ?1")
    String getNameByChatId(long chatId);

    @Query("select user.roles from Employee user where user.code = ?1")
    List<Role> getRolesByCode(long code);

    @Query("SELECT COUNT(r) FROM Employee u JOIN u.roles r WHERE u.chatId = ?1 AND r.name = ?2")
    boolean hasRoleByChatId(Long chatId, String roleName);

    @Query("select user.roles from Employee user where user.chatId = ?1")
    List<Role> getRolesByChatId(long chatId);


    @Query("select case when us.code is not null then true else false end from Employee us where us.chatId = ?1")
    boolean hasCodeByChatId(Long chatId);

    Employee getByChatIdAndDeletedFalse(long chatId);

    Employee findByPhoneAndDeletedFalse(String phone);

    Employee findByChatIdAndDeletedFalse(long chatId);

    Language getLanguageByChatId(long chatId);

    @Query(value = "select case when (em.code = ?2 or (select case when count(emp) = 0 then true else false end from employee emp where emp.code = ?2) ) " +
            "and (em.phone = ?3 or (select case when count(emp) = 0 then true else false end from employee emp where emp.phone = ?3)) " +
            "then true else  false  end from employee em where em.id = ?1", nativeQuery = true)
    boolean validateCodeAndPhone(long id, Long code, String phone);


    @Query(value = "select case when (select case when count(em) = 0 then true else false end from employee em where em.code = ?1) " +
            "       and " +
            "       (select case when count(em) = 0 then true else false end from employee em where em.phone = ?2)" +
            "        then true else false end;", nativeQuery = true)
    boolean validateCodeAndPhone(long code, String phone);


    //    @Query("select case when (em.code = ?2 or emp.code is null) and (em.phone = ?3 or emp.phone is null) then true else false end from Employee em left join em.employee emp where em.id = ?1")
//    boolean validateCodeAndPhone(long id, Long code, String phone);
    @Modifying
    @Query("update Employee em set em.deleted = true where em.id = ?1")
    void delete(long id);

    List<Employee> findAllByDeletedFalseOrderById();

    @Query("update Employee em set em.chatId = ?2, em.user = ?3 where em.phone = ?1")
    @Modifying
    @Transactional
    void setUser(String phone, long chatId, User user);


    @Query("select emp.fullName from Employee emp where emp.code = ?1")
    String getNameByCode(long code);
}
