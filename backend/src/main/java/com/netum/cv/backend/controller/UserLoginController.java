package com.netum.cv.backend.controller;

import com.netum.cv.backend.modal.CustomResponse;
import com.netum.cv.backend.modal.LoginUser;
import com.netum.cv.backend.modal.RequestUser;
import com.netum.cv.backend.service.JwtTokenService;
import com.netum.cv.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserLoginController extends AppController{

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<CustomResponse> userSignUp(@Valid @RequestBody RequestUser requestUser){
        return getEntityResponseAnswer(userService.saveUser(requestUser));
    }

    @PostMapping("/userLogin")
    public ResponseEntity<?> userLogin(@Valid @RequestBody LoginUser loginUser) {

        String jwt = tokenService.generateToken(loginUser);
        return new ResponseEntity<>(jwt,HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUserInformation() {
        return ResponseEntity.ok(userService.getAppUser());
    }

    @GetMapping("/userLogout")
    public ResponseEntity<?> userLogout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test")
    @PreAuthorize("hasAnyRole('ROLE_USER' )")
    public ResponseEntity<?> test() {// todo delete
        return ResponseEntity.ok("OK it'text works");
    }

}
