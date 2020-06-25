package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.entity.EmployeeAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, Long> {

    @Query(value = "FROM employee_account e WHERE e.deleteFlag = :status")
    Optional<EmployeeAccount> findByIdExist(@Param("status") boolean status);

    Boolean existsByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE employee_account e SET e.deleteFlag = :status  WHERE e.id = :employeeAccountId")
    void deleteAccountById(@Param("employeeAccountId") Long id, @Param("status") boolean status);
}
