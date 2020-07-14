package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.dto.TaskDTO;
import com.hvcg.api.crm.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long> {


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO task(created_by, description, name, start_date, customer_id, priority_id, status_id) " +
            "VALUES(:createdBy, :description, :name, :startDate, :customerId, :priorityId, :statusId)", nativeQuery = true)
    void createTask(@Param("createdBy") String createBy, @Param("description") String description, @Param("name") String name,
                    @Param("startDate") Date startDate, @Param("customerId") Long customerId,
                    @Param("priorityId") Long priorityId, @Param("statusId") Long statusId) ;

    @Transactional
    @Query(value = "SELECT LAST_INSERT_ID()",  nativeQuery = true)
    int getIdLastInsert();


    @Modifying
    @Transactional
    @Query(value = "UPDATE task t SET t.deleteFlag = :status  WHERE t.id = :taskId")
    void deleteTask(@Param("taskId") Long id, @Param("status") boolean status);

    @Query(value = "SELECT new com.hvcg.api.crm.dto.TaskDTO(" +
            "t.id, t.name, t.description, t.customer.id, t.customer.fullName,t.taskStatus.id, t.taskStatus.name, " +
            "t.taskPrioriry.id, t.taskPrioriry.name, t.startDate) " +
            "FROM task t " +
            "WHERE t.id = :taskId AND t.deleteFlag = :status")
    Optional<TaskDTO> getTaskById(@Param("taskId") Long taskId, @Param("status") boolean status);


    @Query(value = "SELECT new com.hvcg.api.crm.dto.TaskDTO(" +
            "t.id, t.name, t.description, t.customer.id, t.customer.fullName,t.taskStatus.id, t.taskStatus.name, " +
            "t.taskPrioriry.id, t.taskPrioriry.name, t.startDate) " +
            "FROM task t " +
            "WHERE t.deleteFlag = :status")
    List<TaskDTO> getAllTask(@Param("status") boolean status);



    @Modifying
    @Transactional
    @Query(value = "UPDATE task SET modified_by = :modifiedBy, modified_date = :modifiedDate, description = :description, " +
            "name = :name, start_date = :startDate , customer_id = :customerId, priority_id = :priorityId, status_id = :statusId " +
            "WHERE id = :taskId", nativeQuery = true)
    void updateTask(@Param("modifiedBy") String modifiedBy,
                    @Param("modifiedDate") Date modifiedDate,
                    @Param("description") String description,
                    @Param("name") String name,
                    @Param("startDate") Date startDate,
                    @Param("customerId") Long customerId,
                    @Param("priorityId") Long priorityId,
                    @Param("statusId") Long statusId,
                    @Param("taskId") Long taskId);

    boolean existsTaskByIdAndDeleteFlag(Long taskId, boolean status);


}
