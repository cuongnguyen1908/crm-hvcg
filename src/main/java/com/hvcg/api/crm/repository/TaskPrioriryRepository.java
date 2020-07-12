package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.entity.TaskPrioriry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskPrioriryRepository extends JpaRepository<TaskPrioriry, Long> {

    boolean existsTaskPrioriryById(Long taskPriorityId);
}
