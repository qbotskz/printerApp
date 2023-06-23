package com.akimatBot.services;

import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.standart.Employee;
import com.akimatBot.entity.standart.Role;
import com.akimatBot.repository.repos.EmployeeRepository;

import com.akimatBot.web.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getByChatId(long chatId){
        return employeeRepository.getByChatIdAndDeletedFalse(chatId);
    }
//    public Integer     countUserByChatId(long chatId){
//        return employeeRepository.countUserByChatId(chatId);
//    }
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }
//    public Employee findFirstByChatId(long chatId){
//        return employeeRepository.findFirstByChatId(chatId);
//
//    }
    public Employee findByPhone(String phone){
        return employeeRepository.findByPhoneAndDeletedFalse(phone);

    }
    public Employee findByCode(Long code){
        return employeeRepository.findByCodeAndDeletedFalse(code);

    }
    public Employee findByChatId(long chatId){
        return employeeRepository.findByChatIdAndDeletedFalse(chatId);

    }
    @Transactional
    public Employee save(Employee user){
        return employeeRepository.save(user);
    }



    public Language getLanguageByChatId(long chatId){
        return employeeRepository.getLanguageByChatId(chatId);
    }

    @Transactional
    public void checkUser(long chatId) {
        Employee user = this.findByChatId(chatId);
        if (user == null){
            user = new Employee();
            user.setChatId(chatId);
            this.save(user);
        }
    }

//    @Transactional
//    public User findByCode(String code) {
//        return userRepository.findByCode(code);
//    }

    public Employee findByUsername(String username) {
        return employeeRepository.findByLoginAndDeletedFalse(username);
    }

    public List<Role> getRoles(String username) {
        return employeeRepository.getRoles(username);
    }

    public boolean hasRoleByCode(long code, String roleName) {
        return employeeRepository.hasRoleByCode(code, roleName);
    }

    @Transactional
    public void closeAllShifts() {
        employeeRepository.closeAllShifts();
    }

    @Transactional
    public void setNullToShift(long code) {
        employeeRepository.setNullToShift(code);
    }
    @Transactional
    public void setNullToShiftByChatId(long chatId) {
        employeeRepository.setNullToShiftByChatId(chatId);
    }

    public String getNameByChatId(long chatId) {
        return employeeRepository.getNameByChatId(chatId);
    }
    public String getNameByCode(long code) {
        return employeeRepository.getNameByCode(code);
    }

    public List<RoleDTO> getRolesDTOByCode(long code) {
        List<RoleDTO> dtos = new ArrayList<>();
        for (Role role : employeeRepository.getRolesByCode(code)){
            dtos.add(role.getDTO());
        }
        return dtos;
    }

    public boolean hasRoleByChatId(Long chatId, String roleName) {
        return employeeRepository.hasRoleByChatId(chatId, roleName);

    }

    public List<RoleDTO> getRolesDTOByChatId(long chatId) {
        List<RoleDTO> dtos = new ArrayList<>();
        for (Role role : employeeRepository.getRolesByChatId(chatId)){
            dtos.add(role.getDTO());
        }
        return dtos;
    }

    public boolean isExistsCode(long code) {
       return employeeRepository.existsByCodeAndDeletedFalse(code);
    }

    public boolean hasCodeByChatId(Long chatId) {
        if (chatId != null) {
            return employeeRepository.hasCodeByChatId(chatId);
        }
        return false;
    }
}
