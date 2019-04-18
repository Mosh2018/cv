package com.netum.cv.backend.service;

import com.netum.cv.backend.exceptions.UserNotValidException;
import com.netum.cv.backend.modal.AppUser;
import com.netum.cv.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UserNotValidException {
        return getApplicationUser(username);
    }

    private AppUser getApplicationUser(String username) {
        boolean isExistUser = userRepository.existsByUsername(username);
        if(!isExistUser) throw new UserNotValidException(HttpStatus.FORBIDDEN, " the user is not exist");
        return AppUser.create(userRepository.findByUsername(username).get());
    }
}
