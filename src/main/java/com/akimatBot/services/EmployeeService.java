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

    public Employee findByCode(Long code) {
        return employeeRepository.findByCodeAndDeletedFalse(code);

    }

    public Employee findByChatId(long chatId) {
        return employeeRepository.findByChatIdAndDeletedFalse(chatId);

    }

    @Transactional
    public Employee save(Employee user) {
        return employeeRepository.save(user);
    }


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
    public void setNullToShift(long code) {
        employeeRepository.setNullToShift(code);
    }

    @Transactional
    public void setNullToShiftByChatId(long chatId) {
        employeeRepository.setNullToShiftByChatId(chatId);
    }


    public boolean hasRoleByChatId(Long chatId, String roleName) {
        return employeeRepository.hasRoleByChatId(chatId, roleName);

    }

    public boolean hasCodeByChatId(Long chatId) {
        if (chatId != null) {
            return employeeRepository.hasCodeByChatId(chatId);
        }
        return false;
    }
}
