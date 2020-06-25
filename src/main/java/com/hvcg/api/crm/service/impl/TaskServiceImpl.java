package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.EmployeeDTO;
import com.hvcg.api.crm.dto.TaskDTO;
import com.hvcg.api.crm.repository.TaskAssignmentRepository;
import com.hvcg.api.crm.repository.TaskRepository;
import com.hvcg.api.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;

    @Override
    public Page<TaskDTO> finAll(Pageable pageable) {
        //list task
        Page<TaskDTO> listTaskDTO = this.taskRepository.findAllTask(pageable, Status.ACTIVE.getStatus());

        for (int i = 0; i < listTaskDTO.getTotalElements(); i++) {
            List<EmployeeDTO> listEmployeeAssignment = this.taskAssignmentRepository
                    .findAllEmployeeAssignmentByTaskId(listTaskDTO.getContent().get(i).getId(),
                            Status.ACTIVE.getStatus());
            listTaskDTO.getContent().get(i).setListAssignment(listEmployeeAssignment);

        }

        return listTaskDTO;
    }

    @Override
    public void deleteTask(Long taskId, boolean status) {
        this.taskRepository.deleteTask(taskId, status);
        this.taskAssignmentRepository.deleteTaskAssignByTaskId(taskId, status);
    }
}
