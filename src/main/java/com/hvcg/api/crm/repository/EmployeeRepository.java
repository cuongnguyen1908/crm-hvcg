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
    @Query(value = "SELECT new com.hvcg.api.crm.dto.EmployeeDTO(e.id, e.firstName, e.lastName, e.fullName, e.email, e.phone, e.address, e.identityNumber, " +
            "e.position, e.bankName, e.bankAccount, e.dob, e.employeeAccount.username, e.employeeAccount.accountType.name, e.region.name, e.region.aliasName) " +
            "FROM employee e WHERE e.deleteFlag = :status")
    Page<EmployeeDTO> findAllEmployee(Pageable pageable, @Param("status") boolean status);


    @Query(value = "SELECT new com.hvcg.api.crm.dto.EmployeeDTO(e.id, e.firstName, e.lastName, e.fullName, e.email, e.phone, e.address, e.identityNumber, " +
            "e.position, e.bankName, e.bankAccount, e.dob, e.employeeAccount.username, e.employeeAccount.accountType.name, e.region.name, e.region.aliasName) " +
            "FROM employee e WHERE e.deleteFlag = :status AND e.id = :employeeId")
    Optional<EmployeeDTO> findEmployeeById(@Param("employeeId") Long employeeId, @Param("status") boolean status);


    @Modifying
    @Transactional
    @Query(value = "UPDATE employee e SET e.deleteFlag = :status  WHERE e.id = :employeeId")
    void deleteCustomerByID(@Param("employeeId") Long id, @Param("status") boolean status );

    @Query(value = "SELECT e.employeeAccount.id FROM employee e WHERE e.id = :employeeId")
    Long findAccountIdByEmployeeId(@Param("employeeId") Long employeeId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE employee e SET " +
            "e.firstName = :firstName, " +
            "e.lastName = :lastName, " +
            "e.fullName = :fullName," +
            " e.dob = :dob, " +
            "e.email = :email, " +
            "e.address = :address, " +
            "e.phone = :phone, " +
            "e.identityNumber = :identityNumber, " +
            "e.position = :position, " +
            "e.bankName = :bankName, " +
            "e.bankAccount = :bankAccount, " +
            "e.region = :region " +
            "WHERE e.id = :employeeId")
    void updateEmployee(@Param("firstName") String firstName,
                        @Param("lastName") String lastName,
                        @Param("fullName") String fullName,
                        @Param("dob") Date dob,
                        @Param("email") String email,
                        @Param("address") String address,
                        @Param("phone") String phone,
                        @Param("identityNumber") String identityNumber,
                        @Param("position") String position,
                        @Param("bankName") String bankName,
                        @Param("bankAccount") String bankAccount,
                        @Param("region") Region region,
                        @Param("employeeId") Long id);
}
