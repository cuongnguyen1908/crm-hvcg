package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.constant.ERole;
import com.hvcg.api.crm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
