package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.createDTO.TaskAssignmentCreateDTO;

import javax.servlet.http.HttpServletRequest;

public interface TaskAssignmentService {

    void createTaskAssignment(TaskAssignmentCreateDTO dto, HttpServletRequest request);
}
