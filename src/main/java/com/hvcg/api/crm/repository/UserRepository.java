package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.dto.UserDTO;
import com.hvcg.api.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "FROM user u WHERE u.username = :username AND u.deleteFlag = :status")
    Optional<User> findByUsername(@Param("username") String username, @Param("status") boolean status);


    Boolean existsByUsername(String username);
//Long id, String username, String firstName, String lastName, String fullName

    @Query(value = "SELECT new com.hvcg.api.crm.dto.UserDTO(u.id, u.username, u.firstName, " +
            "u.lastName, u.fullName) " +
            "FROM user u WHERE u.id = :userId " +
            "AND deleteFlag = " +
            ":status")
    Optional<UserDTO> selectUserById(@Param("userId") Long userId, @Param("status") boolean status);


}
