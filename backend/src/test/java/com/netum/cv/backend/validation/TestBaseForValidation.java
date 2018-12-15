package com.netum.cv.backend.validation;

import com.netum.cv.backend.BackendApplication;
import com.netum.cv.backend.modal.RequestUser;
import com.netum.cv.backend.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BackendApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestBaseForValidation {

    @InjectMocks
    protected SignUpUserValidation signUpUserValidation;

    @Mock
    protected UserRepository userRepository;

    String[] strPasswords = {
            "password",
            "password1a",
            "password01",
            "Password01",
            "P@ssword01",
            "abc",
            "mypassword",
            "00000000",
            "AlphaRomeo4c",
            "fiatlinea2014",
            "F@rd1co",
            "F@rd1coSports",
            "Suzuki@lpha2016",
            "!vwvento2015",
            "!@#$%^&*Aa1",
            "myDream1@$$",
            "HelloWorld@001!"
    };

    String[] emails = {
            "m@m.com",
            "R R@gamil.com",
            "Shaker @suomi.fi",
            "Shaker@suomi.fi",
            "Shaker@suomi",
            "Shaker@suomi.",

    };


    protected RequestUser getRequestUser(String username, String firstname, String lastname, String email, String password) {

        RequestUser requestUser = new RequestUser();
        requestUser.setUsername(username == null ? "TEDFORTECH" : username);
        requestUser.setFirstName(firstname == null ? "Moha" : firstname);
        requestUser.setLastName(lastname == null ? "someone" : lastname);
        requestUser.setEmail(email == null ? "testemail@appliction.com" : email);
        requestUser.setPassword(password == null ? "Ted123##" : password);
        return requestUser;
    }
    @Test
    public void initTest() {

    }
}
