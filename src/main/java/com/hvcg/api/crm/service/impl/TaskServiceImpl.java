package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.Utilities.CommonUltils;
import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.EmployeeDTO;
import com.hvcg.api.crm.dto.TaskDTO;
import com.hvcg.api.crm.dto.createDTO.TaskCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.TaskUpdateDTO;
import com.hvcg.api.crm.repository.TaskAssignmentRepository;
import com.hvcg.api.crm.repository.TaskRepository;
import com.hvcg.api.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;

    @Override
    public List<TaskDTO> getAllTask(boolean status) {
        List<TaskDTO> listTaskDTO = this.taskRepository.getAllTask(status);

        for (int i = 0; i < listTaskDTO.size(); i++) {
            List<EmployeeDTO> listEmployeeAssignment = this.taskAssignmentRepository
                    .getAllEmployeeAssignmentByTaskId(listTaskDTO.get(i).getId(),
                            Status.ACTIVE.getStatus());
            listTaskDTO.get(i).setListAssignment(listEmployeeAssignment);

        }
        return listTaskDTO;
    }

    @Override
    public void deleteTask(Long taskId, boolean status) {
        this.taskAssignmentRepository.deleteTaskAssignByTaskId(taskId, status);
        this.taskRepository.deleteTask(taskId, status);
    }

    @Override
    public void createTask(TaskCreateDTO dto, boolean isNull, HttpServletRequest request) {
        String username = CommonUltils.getUsernameByRequestHeader(request);

        this.taskRepository.createTask(username, dto.getDescription(), dto.getName(), dto.getStartDate(), dto.getCustomerId(), dto.getTaskPrioriryId(), dto.getTaskStatusId());


        if (!isNull) {
            int taskId = this.taskRepository.getIdLastInsert();

            dto.getEmployeeId().forEach(employeeId -> {
                this.taskAssignmentRepository.assignTaskAssignment(Long.valueOf(taskId), username, employeeId);
            });
        }
    }

    @Override
    public void updateTask(TaskUpdateDTO dto, boolean isNull, HttpServletRequest request) {
        String username = CommonUltils.getUsernameByRequestHeader(request);

        this.taskRepository.updateTask(username, new Date(), dto.getDescription(), dto.getName(),
                dto.getStartDate(), dto.getCustomerId(),
                dto.getTaskPrioriryId(), dto.getTaskStatusId(), dto.getTaskId());

        if (!isNull) {
            this.taskAssignmentRepository.deleteTaskAssignByTaskId(dto.getTaskId(), Status.IN_ACTIVE.getStatus());
            dto.getEmployeeId().forEach(employeeId -> {
                this.taskAssignmentRepository.assignTaskAssignment(dto.getTaskId(),
                        username, employeeId);
            });
        }



    }
}
