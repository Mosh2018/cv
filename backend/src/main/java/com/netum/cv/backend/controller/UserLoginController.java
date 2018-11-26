package com.netum.cv.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserLoginController {

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
