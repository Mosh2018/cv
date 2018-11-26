package com.netum.cv.backend.repositories;

import com.netum.cv.backend.entity.Role;
import com.netum.cv.backend.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);
    Optional<Role> findByName(RoleName roleName);
}
