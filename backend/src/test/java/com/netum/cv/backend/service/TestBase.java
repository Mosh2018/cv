package com.netum.cv.backend.service;

import com.netum.cv.backend.BackendApplication;
import com.netum.cv.backend.repositories.UserRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BackendApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestBase {

    @InjectMocks
    protected ApplicationUserDetailService userDetailService;

    @InjectMocks
    protected JwtTokenService jwtTokenService;

    @Mock
    protected UserRepository userRepository;

    protected String username = "Moha1234";


}
