package com.akimatBot.entity.standart;

import com.akimatBot.entity.custom.RestaurantBranch;
import com.akimatBot.entity.custom.WaiterShift;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.web.dto.EmployeeDTO;
import com.akimatBot.web.dto.RoleDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Data
@Getter
@Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long chatId;


    private String phone;

    private String fullName;
    private String login;
    private String password;
    private String position;
    private Long code;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Enumerated
    Language language;

    @OneToOne
    User user;

    @OneToOne
    private WaiterShift currentShift;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Role> roles;

    @ManyToOne
    private RestaurantBranch restaurantBranch;


    public EmployeeDTO getDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(id);
        employeeDTO.setChatId(chatId);
        employeeDTO.setPhone(phone);
        employeeDTO.setFullName(fullName);
        employeeDTO.setPosition(position);
        employeeDTO.setCode(code);
        employeeDTO.setRoles(getRolesDTO());

        return employeeDTO;
    }

    public List<RoleDTO> getRolesDTO() {
        List<RoleDTO> dtos = new ArrayList<>();
        for (Role role : this.roles) {
            dtos.add(role.getDTO());
        }
        return dtos;
    }
}
