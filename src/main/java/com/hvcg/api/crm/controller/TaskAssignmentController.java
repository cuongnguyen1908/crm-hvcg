package com.hvcg.api.crm.controller;


import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.TaskAssignmentCreateDTO;
import com.hvcg.api.crm.entity.Customer;
import com.hvcg.api.crm.entity.Employee;
import com.hvcg.api.crm.entity.Task;
import com.hvcg.api.crm.entity.TaskAssignment;
import com.hvcg.api.crm.repository.EmployeeRepository;
import com.hvcg.api.crm.repository.TaskAssignmentRepository;
import com.hvcg.api.crm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskAssignmentController {

    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//
//    @PostMapping("/taskAssign")
//    public ResponseEntity<ResponseDTO> createTaskAssign(@RequestBody TaskAssignmentCreateDTO dto) {
//        Employee employee = this.employeeRepository.findById(dto.getEmployeeId())
//                .orElseThrow(() -> new RuntimeException("Employee not found id - " + dto.getEmployeeId()));
//
//
//        Task task = this.taskRepository.findById(dto.getTaskId())
//                .orElseThrow(() -> new RuntimeException("Task not found id - " + dto.getTaskId()));
//
//
//        this.taskAssignmentRepository.createAssignment(employee, task);
//        ResponseDTO responseDTO = new ResponseDTO("Create success");
//        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
//    }

    @GetMapping("/taskAssignment")
    public List<TaskAssignment> getAll() {
        return this.taskAssignmentRepository.findAll();
    }


}
