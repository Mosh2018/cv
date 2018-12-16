package com.netum.cv.backend.service;

import com.netum.cv.backend.entity.Role;
import com.netum.cv.backend.entity.RoleName;
import com.netum.cv.backend.entity.User;
import com.netum.cv.backend.facade.IAuthenticationFacade;
import com.netum.cv.backend.modal.AppUser;
import com.netum.cv.backend.modal.CustomResponse;
import com.netum.cv.backend.modal.RequestUser;
import com.netum.cv.backend.repositories.RoleRepository;
import com.netum.cv.backend.repositories.UserRepository;
import com.netum.cv.backend.validation.SignUpUserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.netum.cv.backend.modal.CustomStatus.PASS_VALIDATION;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SignUpUserValidation signUpUserValidation;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public AppUser getUserInformation() {
        User userEntity = userRepository.findByUsername(authenticationFacade.getUsername()).get();
        return AppUser.create(userEntity);
    }

    public CustomResponse saveUser(RequestUser requestUser) {

        CustomResponse result = signUpUserValidation.validateSignUp(requestUser);

        if (result.getCustomStatus() == PASS_VALIDATION) {
            User user = populateUser(requestUser);
            userRepository.save(user);
        }

        return result;
    }

    private User populateUser(RequestUser requestUser) {
        String passwordDecoded = passwordEncoder.encode(requestUser.getPassword());
        User user = new User();
        user.setUsername(requestUser.getUsername());
        user.setFirstName(requestUser.getFirstName());
        user.setLastName(requestUser.getLastName());
        user.setEmail(requestUser.getEmail());
        user.setPassword(passwordDecoded);
        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singleton(userRole));
        return user;
    }

}
