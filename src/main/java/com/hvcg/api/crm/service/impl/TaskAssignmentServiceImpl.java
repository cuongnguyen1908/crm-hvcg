package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.Utilities.CommonUltils;
import com.hvcg.api.crm.dto.createDTO.TaskAssignmentCreateDTO;
import com.hvcg.api.crm.repository.TaskAssignmentRepository;
import com.hvcg.api.crm.service.TaskAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TaskAssignmentServiceImpl implements TaskAssignmentService {

    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;

    @Override
    public void createTaskAssignment(TaskAssignmentCreateDTO dto, HttpServletRequest request) {
        String username = CommonUltils.getUsernameByRequestHeader(request);

        dto.getEmployeeId().forEach(employeeId -> {
            this.taskAssignmentRepository.assignTaskAssignment(dto.getTaskId(), username, employeeId);
        });
    }
}
