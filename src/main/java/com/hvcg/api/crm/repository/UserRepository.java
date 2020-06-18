package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "FROM user u WHERE u.username = :username and u.deleteFlag = :status")
    Optional<User> findByUsername(@Param("username") String username, @Param("status") boolean status);


    Boolean existsByUsername(String username);


}
