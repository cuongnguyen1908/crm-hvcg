package com.hvcg.api.crm.controller;


import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.TaskDTO;
import com.hvcg.api.crm.dto.createDTO.TaskCreateDTO;
import com.hvcg.api.crm.entity.*;
import com.hvcg.api.crm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskPrioriryRepository taskPrioriryRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/taskList")
    public Page<TaskDTO> getAllCustomerAddressByCustomerId(Pageable pageable) {
        return this.taskRepository.findAllTask(pageable, Status.ACTIVE.getStatus());
    }

    @PostMapping("/taskList")
    public ResponseEntity<ResponseDTO> createTask(@RequestBody TaskCreateDTO dto) {

        //check valid task status/ priority
        TaskStatus taskStatus = this.taskStatusRepository.findById(dto.getTaskStatusId())
                .orElseThrow(() -> new RuntimeException("Task status not found id - " + dto.getTaskStatusId()));

        TaskPrioriry taskPrioriry = this.taskPrioriryRepository.findById(dto.getTaskPrioriryId())
                .orElseThrow(() -> new RuntimeException("Task priority not found id - " + dto.getTaskStatusId()));

        Customer customer = this.customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found id - " + dto.getCustomerId()));

        Task task = new Task();

        task.setDescription(dto.getDescription());
        task.setName(dto.getName());
        task.setStartDate(dto.getStartDate());
        task.setTaskStatus(taskStatus);
        task.setTaskPrioriry(taskPrioriry);
        task.setCustomer(customer);

        List<Long> listEmployeeId = dto.getEmployeeId();


        if (listEmployeeId != null) {

            listEmployeeId.forEach(id -> {

                Employee employee = this.employeeRepository.findEmployeeByIdAndDeleteFlag(id, Status.ACTIVE.getStatus())
                        .orElseThrow(() -> new RuntimeException("Employee not found id - " + id));
                TaskAssignment taskAssignment = new TaskAssignment();
                taskAssignment.setEmployee(employee);
                taskAssignment.setTask(task);
                task.addTaskAssignment(taskAssignment);
            });
        }

        this.taskRepository.save(task);

        ResponseDTO responseDTO = new ResponseDTO("Create success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
}
