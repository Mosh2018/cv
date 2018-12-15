package com.netum.cv.backend.service;

import com.netum.cv.backend.entity.Role;
import com.netum.cv.backend.entity.RoleName;
import com.netum.cv.backend.entity.User;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ApplicationUserDetailServiceTest extends TestBaseForService {

    @Test
    public void loadUserByUsername() {
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(getUser());
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        assertEquals(username, userDetails.getUsername());

        List roles = userDetails.getAuthorities()
                .stream()
                .map(role -> {
                   return new Role(RoleName.valueOf(((GrantedAuthority) role).getAuthority())).getRoleName().name();
                }).collect(Collectors.toList());

        assertTrue(roles.contains(RoleName.ROLE_ADMIN.name()));
        assertTrue(roles.contains(RoleName.ROLE_USER.name()));

    }

    private Optional<User> getUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setEmail("moha@suomi.fi");
        Role role1 = new Role();
        role1.setId(1L);
        role1.setRoleName(RoleName.ROLE_USER);
        Role role2 = new Role();
        role2.setId(1L);
        role2.setRoleName(RoleName.ROLE_ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);
        user.setRoles(roles);

        return Optional.of(user);
    }
}