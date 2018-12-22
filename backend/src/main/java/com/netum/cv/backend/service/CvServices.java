package com.netum.cv.backend.service;

import com.netum.cv.backend.entity.Profile;
import com.netum.cv.backend.entity.User;
import com.netum.cv.backend.exceptions.UseJPAException;
import com.netum.cv.backend.modal.CustomResponse;
import com.netum.cv.backend.modal.CustomStatus;
import com.netum.cv.backend.modal.CvProfile;
import com.netum.cv.backend.repositories.ProfileRepository;
import com.netum.cv.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CvServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileRepository profileRepository;

    public ResponseEntity<CustomResponse> AddOrUpdateProfile(CvProfile cvProfile) throws Exception {

        User user = userService.getUserEntity();
        if(user.getProfile() == null) {
            try {
                Profile profileEntity = initProfile(cvProfile);
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

        return ResponseEntity.ok(CustomResponse.build(CustomStatus.IT_IS_OK));
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

    private Profile initProfile(CvProfile cvProfile) throws Exception {
        // validate profile
        Profile profile = new Profile();
        return profile.initProfile(cvProfile);

    }

    public ResponseEntity<Profile> getProfile() {
        // get username
        // get profile id
        // get profile from by

        return ResponseEntity.ok(new Profile());
    }
    // retrive profile

}
