package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.dto.EmployeeDTO;
import com.hvcg.api.crm.entity.Employee;
import com.hvcg.api.crm.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT new com.hvcg.api.crm.dto.EmployeeDTO(e.id, e.firstName, e.lastName, e.fullName, e.gender, e.email, e" +
            ".phone, e.address, e.identityNumber, " +
            "e.position, e.bankName, e.bankAccount, e.dob, e.employeeAccount.username, e.employeeAccount.accountType" +
            ".name, e.region.name, e.region.aliasName) " +
            "FROM employee e WHERE e.deleteFlag = :status")
    EmployeeDTO getAllEmployee(@Param("status") boolean status);

    @Query("SELECT new com.hvcg.api.crm.dto.EmployeeDTO(e.id, e.firstName, e.lastName, e.fullName, e.gender, e.email, e.phone, " +
            "e.address, e.identityNumber, " +
            "e.position, e.bankName, e.bankAccount, e.dob, e.employeeAccount.username, e.employeeAccount.accountType" +
            ".name, e.region.name, e.region.aliasName) " +
            "FROM employee e WHERE lower(e.fullName) like concat('%', lower(:searchValue), '%') and e.deleteFlag " +
            "=:status")
    Page<EmployeeDTO> searchAllEmployeeByFullname(Pageable pageable, @Param("searchValue") String searchValue,
                                        @Param("status") boolean status);

    @Query(value = "SELECT new com.hvcg.api.crm.dto.EmployeeDTO(e.id, e.firstName, e.lastName, e.fullName, e.gender, e.email, e" +
            ".phone, e.address, e.identityNumber, " +
            "e.position, e.bankName, e.bankAccount, e.dob, e.employeeAccount.username, e.employeeAccount.accountType" +
            ".name, e.region.name, e.region.aliasName) " +
            "FROM employee e WHERE e.deleteFlag = :status AND e.id = :employeeId")
    Optional<EmployeeDTO> getEmployeeById(@Param("employeeId") Long employeeId, @Param("status") boolean status);



    boolean existsEmployeeByIdAndDeleteFlag(Long employeeId, boolean status);



    @Modifying
    @Transactional
    @Query(value = "UPDATE employee e SET e.deleteFlag = :status  WHERE e.id = :employeeId")
    void deleteCustomerById(@Param("employeeId") Long id, @Param("status") boolean status);

    @Query(value = "SELECT e.employeeAccount.id FROM employee e WHERE e.id = :employeeId")
    Long getEmployeeAccountIdByEmployeeId(@Param("employeeId") Long employeeId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE employee SET " +
            "modified_by = :modifiedBy, " +
            "modified_date = :modifiedDate, " +
            "first_name = :firstName, " +
            "last_name = :lastName, " +
            "full_name = :fullName," +
            "gender = :gender," +
            "dob = :dob, " +
            "email = :email, " +
            "address = :address, " +
            "phone = :phone, " +
            "identity_number = :identityNumber, " +
            "position = :position, " +
            "bank_name = :bankName, " +
            "bank_account = :bankAccount, " +
            "region_id = :regionId " +
            "WHERE id = :employeeId", nativeQuery = true)
    void updateEmployee(@Param("modifiedBy") String modifiedBy,
                        @Param("modifiedDate") Date modifiedDate,
                        @Param("firstName") String firstName,
                        @Param("lastName") String lastName,
                        @Param("fullName") String fullName,
                        @Param("gender") int gender,
                        @Param("dob") Date dob,
                        @Param("email") String email,
                        @Param("address") String address,
                        @Param("phone") String phone,
                        @Param("identityNumber") String identityNumber,
                        @Param("position") String position,
                        @Param("bankName") String bankName,
                        @Param("bankAccount") String bankAccount,
                        @Param("regionId") Long regionId,
                        @Param("employeeId") Long id);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO employee(created_by,delete_flg,address,bank_account,bank_name,dob,email,first_name,full_name,gender,identity_number,last_name,phone, position, employee_account_id,region_id)" +
            "VALUES(:createdBy, :status, :address, :bankAccount, :bankName, :dob, :email, :firstName, :fullName, :gender, :identityNumber, :lastName, :phone, :position, :accountId, :regionId)", nativeQuery = true)
    void createEmployee(@Param("createdBy") String username,
                        @Param("status") boolean status,
                        @Param("firstName") String firstName,
                        @Param("lastName") String lastName,
                        @Param("fullName") String fullName,
                        @Param("gender") int gender,
                        @Param("dob") Date dob,
                        @Param("email") String email,
                        @Param("address") String address,
                        @Param("phone") String phone,
                        @Param("identityNumber") String identityNumber,
                        @Param("position") String position,
                        @Param("bankName") String bankName,
                        @Param("bankAccount") String bankAccount,
                        @Param("regionId") Long regionId,
                        @Param("accountId") Long accountId );
}
