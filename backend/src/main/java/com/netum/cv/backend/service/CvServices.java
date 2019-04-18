package com.netum.cv.backend.service;

import com.netum.cv.backend.entity.Profile;
import com.netum.cv.backend.entity.User;
import com.netum.cv.backend.exceptions.UseJPAException;
import com.netum.cv.backend.modal.CustomResponse;
import com.netum.cv.backend.modal.CvProfile;
import com.netum.cv.backend.modal.ValidationResult;
import com.netum.cv.backend.repositories.ProfileRepository;
import com.netum.cv.backend.repositories.UserRepository;
import com.netum.cv.backend.validation.CvValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CvServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CvValidation cvValidation;

    public CustomResponse AddOrUpdateProfile(CvProfile cvProfile) throws Exception {
        List<ValidationResult> results = cvValidation.validateProfile(cvProfile);
        if (results.isEmpty()) {
            saveProfile(cvProfile);
            return CustomResponse.build(HttpStatus.OK, results);
        }
        return CustomResponse.build(HttpStatus.NO_CONTENT, results);
    }

    private void saveProfile(CvProfile cvProfile) {
        User user = userService.getUserEntity();
        if(user.getProfile() == null) {
            try {
                Profile profileEntity = new Profile();
                profileEntity.initProfile(cvProfile);
                Profile savedProfile = profileRepository.save(profileEntity);
                user.setProfile(savedProfile);
                userRepository.save(user);
            } catch (Exception exc) {
                throw new UseJPAException(HttpStatus.EXPECTATION_FAILED, "the user did't saved");
            }
        } else {
            try {
                updateProfile(user.getProfile() , cvProfile);
            } catch (Exception exc) {
                throw new UseJPAException(HttpStatus.EXPECTATION_FAILED, "the profile did't saved");
            }
        }
    }

    private Profile updateProfile(Profile profile, CvProfile cvProfile) {
        profile.setPhoneNumber(cvProfile.getPhoneNumber());
        profile.setBirthday(cvProfile.getBirthday());
        profile.setNationality(cvProfile.getNationality());
        profile.setAddress(cvProfile.getAddress());
        profile.setCountry(cvProfile.getCountry());
        profile.setCity(cvProfile.getCity());
        profile.setZipCode(cvProfile.getZipCode());
        return profileRepository.save(profile);
    }
    public ResponseEntity<CvProfile> getProfile() {
        User user = userService.getUserEntity();
        if (user != null && user.getProfile() != null) {
            return new ResponseEntity(CvProfile.createCvProfile(user.getProfile()), HttpStatus.OK);
        } else {
            if (user == null) throw new UseJPAException(HttpStatus.FORBIDDEN, "the token is not valid");
            else throw new UseJPAException(HttpStatus.EXPECTATION_FAILED, "the user can't get from the server");
        }

    }

}
