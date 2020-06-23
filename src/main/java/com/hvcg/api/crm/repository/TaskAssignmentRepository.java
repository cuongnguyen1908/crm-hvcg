package com.hvcg.api.crm.repository;


import com.hvcg.api.crm.entity.Employee;
import com.hvcg.api.crm.entity.Task;
import com.hvcg.api.crm.entity.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {


   // @Query(value = "INSERT INTO task_assignment(employee_id, task_id) VALUES(:employeeId, :taskId)", nativeQuery = true)
   // void createAssignment(@Param("employeeId") Long customerId, @Param("taskId") Long taskId);

    @Query(value = "INSERT INTO task_assignment(employee_id, task_id) VALUES(:employeeId, :taskId)", nativeQuery = true)
    void createAssignment(@Param("employeeId") Employee employee, @Param("taskId") Task task);
}
