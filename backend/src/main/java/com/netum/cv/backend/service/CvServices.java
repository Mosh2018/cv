package com.netum.cv.backend.service;

import com.netum.cv.backend.entity.Profile;
import com.netum.cv.backend.entity.User;
import com.netum.cv.backend.exceptions.UseJPAException;
import com.netum.cv.backend.modal.CustomResponse;
import com.netum.cv.backend.modal.CustomStatus;
import com.netum.cv.backend.modal.CvProfile;
import com.netum.cv.backend.repositories.ProfileRepository;
import com.netum.cv.backend.repositories.UserRepository;
import com.netum.cv.backend.validation.CvValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public ResponseEntity<CustomResponse> AddOrUpdateProfile(CvProfile cvProfile) throws Exception {

        if (CustomStatus.PASS_VALIDATION.equals(cvValidation.validateProfile(cvProfile))) {
            saveProfile(cvProfile);
        }
        return ResponseEntity.ok(CustomResponse.build(CustomStatus.IT_IS_OK));
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
                CustomStatus.USER_NOT_SAVED.setCustomStatusAMessage(exc.getMessage());
                throw new UseJPAException(CustomStatus.USER_NOT_SAVED);
            }
        } else {
            try {
                updateProfile(user.getProfile() , cvProfile);
            } catch (Exception exc) {
                throw new UseJPAException(CustomStatus.PROFILE_NOT_SAVED);
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
            if (user == null) throw new UseJPAException(CustomStatus.JWT_INVALID);
            else throw new UseJPAException(CustomStatus.PROFILE_NOT_SAVED);
        }

    }

}
