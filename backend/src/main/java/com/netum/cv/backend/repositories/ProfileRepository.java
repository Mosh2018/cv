package com.netum.cv.backend.repositories;

import com.netum.cv.backend.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> getById(Long id);

    @Modifying
    @Query(value = "UPDATE cv_profile prof SET " +
            "prof.phone_number = phone_number, " +
            "prof.birthday = birthday," +
            "prof.nationality = nationality, " +
            "prof.address = address," +
            "prof.country = country," +
            "prof.city = city," +
            "prof.zip_code = zip_code " +
            "WHERE prof.id = id",
            nativeQuery = true)
    @Transactional
    void updateProfile(String phone_number, String birthday, String nationality, String address, String country,
                       String city, String zip_code, long id );
}
