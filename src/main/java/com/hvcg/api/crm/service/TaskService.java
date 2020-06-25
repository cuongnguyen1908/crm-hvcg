package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Page<TaskDTO> finAll(Pageable pageable);


    void deleteTask(Long taskId, boolean status);
}
