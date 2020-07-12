package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {

    boolean existsTaskStatusById(Long taskStatusId);
}
