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

    @GetMapping("/getUser/{username}")
    public ResponseEntity<?> getUserInformation(@Valid @PathVariable String username) {

        return ResponseEntity.ok(userService.getUserInformation(username));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<CustomResponse> userSignUp(@RequestBody RequestUser requestUser){
        return getEntityResponseAnswer(userService.saveUser(requestUser));
    }
    // /api/auth/userLogin
    @PostMapping("/userLogin")
    public ResponseEntity<?> userLogin(@RequestBody LoginUser loginUser) {

        String jwt = tokenService.generateToken(loginUser);
        return new ResponseEntity<>(jwt,HttpStatus.OK);
    }

    @PostMapping("/UserLogout")
    public ResponseEntity<?> userLogout(@Valid @RequestBody String x) {// todo modify to User

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> test() {// todo delete
        return ResponseEntity.ok("OK it'text works");
    }

}
