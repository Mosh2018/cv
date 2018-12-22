package com.netum.cv.backend.service;

import com.netum.cv.backend.BackendApplication;
import com.netum.cv.backend.entity.Role;
import com.netum.cv.backend.entity.User;
import com.netum.cv.backend.modal.AppUser;
import com.netum.cv.backend.modal.LoginUser;
import com.netum.cv.backend.modal.RoleName;
import com.netum.cv.backend.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BackendApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestBaseForService {

    @InjectMocks
    protected ApplicationUserDetailService userDetailService;

    @InjectMocks
    protected JwtTokenService jwtTokenService;

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected AuthenticationManager authenticationManager;

    @Mock
    protected PasswordEncoder passwordEncoder;

    @Mock
    protected DateTimeService dateTimeService;

    protected String username = "Moha1234";
    protected String password = "password";

    @Test
    public void intTest(){

    }

    protected void mockAuthentication() {
        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn(createApplicationUserForTest());
    }

    protected void mockAddDateTimeService() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        when(dateTimeService.addMillisToDateTime(any(Long.class))).thenReturn(calendar.getTime());
    }

    protected void mockDateTimeService() {
        when(dateTimeService.getDate()).thenReturn(new Date());
    }

    protected void mockpassWordEccoder() {
        when(passwordEncoder.encode(anyString())).thenReturn(jwtTokenService.generateRandomSecretKey(10));
    }

    private AppUser createApplicationUserForTest() {
        User user = new User();
        user.setUsername(username);
        user.setFirstName("Mohamid");
        user.setLastName("anything");
        user.setEmail("mohamid@email.fi");
        user.setPassword(password);
        Set<Role> roles = new HashSet<>();
        Role role = new Role(RoleName.ROLE_USER);
        roles.add(role);
        user.setRoles(roles);
        AppUser appUser = AppUser.create(user);
        return appUser;

    }

    protected LoginUser getLoginUser() {

        return new LoginUser(username, password);
    }

    protected String generateJwtForTest() {
        mockAuthentication();
        mockAddDateTimeService();
        mockDateTimeService();
        mockpassWordEccoder();

        return jwtTokenService.generateToken(getLoginUser());
    }


}
