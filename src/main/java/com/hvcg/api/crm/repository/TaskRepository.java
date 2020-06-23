package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.dto.TaskDTO;
import com.hvcg.api.crm.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;


public interface TaskRepository extends JpaRepository<Task, Long> {

//    Long id, String name, String description, String ownCustomer, String taskStatus,
//                   String taskPrioriry, Date startDate
    @Query(value = "SELECT new com.hvcg.api.crm.dto.TaskDTO(" +
        "t.id, t.name, t.description, t.customer.fullName, t.taskStatus.name, " +
        "t.taskPrioriry.name, t.startDate) " +
        "FROM task t " +
        "WHERE t.deleteFlag = :status")
    Page<TaskDTO> findAllTask(Pageable pageable, @Param("status") boolean deleteFlag);


}
