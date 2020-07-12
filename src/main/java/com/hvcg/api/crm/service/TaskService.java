package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.TaskDTO;
import com.hvcg.api.crm.dto.createDTO.TaskCreateDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TaskService {

    List<TaskDTO> getAllTask(boolean status);

    void createTask(TaskCreateDTO dto, boolean isNull, HttpServletRequest request);


    void deleteTask(Long taskId, boolean status);
}
