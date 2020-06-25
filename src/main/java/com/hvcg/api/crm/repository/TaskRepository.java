package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.dto.TaskDTO;
import com.hvcg.api.crm.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface TaskRepository extends JpaRepository<Task, Long> {


    @Modifying
    @Transactional
    @Query(value = "UPDATE task t SET t.deleteFlag = :status  WHERE t.id = :taskId")
    void deleteTask(@Param("taskId") Long id, @Param("status") boolean status);

    @Query(value = "SELECT new com.hvcg.api.crm.dto.TaskDTO(" +
            "t.id, t.name, t.description, t.customer.id, t.customer.fullName,t.taskStatus.id, t.taskStatus.name, " +
            "t.taskPrioriry.id, t.taskPrioriry.name, t.startDate) " +
            "FROM task t " +
            "WHERE t.id = :taskId AND t.deleteFlag = :status")
    TaskDTO findTaskAndAssignById(@Param("taskId") Long taskId, @Param("status") boolean status);


    @Query(value = "SELECT new com.hvcg.api.crm.dto.TaskDTO(" +
            "t.id, t.name, t.description, t.customer.id, t.customer.fullName,t.taskStatus.id, t.taskStatus.name, " +
            "t.taskPrioriry.id, t.taskPrioriry.name, t.startDate) " +
            "FROM task t " +
            "WHERE t.deleteFlag = :status")
    Page<TaskDTO> findAllTask(Pageable pageable, @Param("status") boolean status);

    boolean existsTaskByIdAndDeleteFlag(Long taskId, boolean status);


}
