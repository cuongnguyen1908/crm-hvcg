package com.hvcg.api.crm.repository;


import com.hvcg.api.crm.entity.Employee;
import com.hvcg.api.crm.entity.Task;
import com.hvcg.api.crm.entity.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {

}
