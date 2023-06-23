package com.akimatBot.web.security.jwt;


import com.akimatBot.entity.standart.Employee;
import com.akimatBot.entity.standart.Role;
import com.akimatBot.entity.standart.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(Employee user) {
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
                );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
