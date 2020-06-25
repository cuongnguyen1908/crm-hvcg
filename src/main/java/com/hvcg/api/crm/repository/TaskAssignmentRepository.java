package com.hvcg.api.crm.repository;


import com.hvcg.api.crm.dto.EmployeeDTO;
import com.hvcg.api.crm.entity.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE task_assignment t SET t.deleteFlag = :status  WHERE t.task.id = :taskId")
    void deleteTaskAssignByTaskId(@Param("taskId") Long id, @Param("status") boolean status);

    @Query(value = "SELECT new com.hvcg.api.crm.dto.EmployeeDTO(a.employee.id, a.employee.firstName, a.employee" +
            ".lastName, a.employee.fullName, a.employee.email, a.employee.phone, a.employee.address, a.employee" +
            ".identityNumber, " +
            "a.employee.position, a.employee.bankName, a.employee.bankAccount, a.employee.dob, a.employee" +
            ".employeeAccount.username, a.employee.employeeAccount.accountType.name, a.employee.region.name, a" +
            ".employee.region.aliasName) " +
            "FROM task_assignment a WHERE a.deleteFlag = :status AND task.id = :taskId")
    List<EmployeeDTO> findAllEmployeeAssignmentByTaskId(Long taskId, boolean status);


    boolean existsTaskAssignmentByTaskIdAndEmployeeId(Long taskId, Long employeeId);

}
