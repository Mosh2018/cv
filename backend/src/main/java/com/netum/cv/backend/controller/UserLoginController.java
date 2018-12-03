package com.netum.cv.backend.controller;

import com.netum.cv.backend.modal.CustomResponse;
import com.netum.cv.backend.modal.RequestUser;
import com.netum.cv.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserLoginController extends AppController{


    @Autowired
    private UserService userService;

    @GetMapping("/getUser/{username}")
    public ResponseEntity<?> getUserInformations(@Valid @PathVariable String username) {

        return ResponseEntity.ok(userService.getUserInformations(username));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<CustomResponse> userSignUp(@RequestBody RequestUser requestUser){
        return getEntityResponseAnswer(userService.saveUser(requestUser));
    }

    @PostMapping("/userLogin")
    public ResponseEntity<?> userLogin(@Valid @RequestBody String x) {// todo modify to LoginRequest

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/UserLogout")
    public ResponseEntity<?> userLogout(@Valid @RequestBody String x) {// todo modify to User

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {// todo delete
        return ResponseEntity.ok("OK it's works");
    }

}
