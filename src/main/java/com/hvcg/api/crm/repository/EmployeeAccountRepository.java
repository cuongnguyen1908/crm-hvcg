package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.entity.EmployeeAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, Long> {

    Boolean existsEmployeeAccountByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE employee_account e SET e.deleteFlag = :status  WHERE e.id = :employeeAccountId")
    void deleteAccountById(@Param("employeeAccountId") Long id, @Param("status") boolean status);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO employee_account(created_by, delete_flg, username, password, account_type_id) " +
            "VALUES(:createdBy, :status, :username, :password, :accountTypeId)", nativeQuery = true)
    void createEmployeeAccount(@Param("createdBy") String createBy, @Param("status") boolean status,
                               @Param("username") String username, @Param("password") String password,
                               @Param("accountTypeId") Long accountTypeId);

    @Transactional
    @Query(value = "SELECT LAST_INSERT_ID()",  nativeQuery = true)
    int getIdLastInsert();
}
