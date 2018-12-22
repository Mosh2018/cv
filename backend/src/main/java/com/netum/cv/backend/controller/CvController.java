package com.netum.cv.backend.controller;

import com.netum.cv.backend.modal.CvProfile;
import com.netum.cv.backend.service.CvServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cv")
public class CvController extends AppController {

    @Autowired
    private CvServices cvServices;

    @PostMapping("/add_profile")
    public ResponseEntity<?> addProfile(@Valid @RequestBody CvProfile cvProfile) throws Exception{
       return cvServices.AddOrUpdateProfile(cvProfile);
    }

    @GetMapping("/get_profile")
    public ResponseEntity<?> getProfile() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PutMapping("/update_profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody String x) {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
