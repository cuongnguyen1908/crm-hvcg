package com.hvcg.api.crm.controller;


import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.EmployeeDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.TaskAssignmentCreateDTO;
import com.hvcg.api.crm.entity.Employee;
import com.hvcg.api.crm.entity.Task;
import com.hvcg.api.crm.entity.TaskAssignment;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.EmployeeRepository;
import com.hvcg.api.crm.repository.TaskAssignmentRepository;
import com.hvcg.api.crm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/taskassignment")
public class TaskAssignmentController {

    @Autowired
    private ResponseDTO responseDTO;

    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createTaskAssignment(@RequestBody TaskAssignmentCreateDTO dto) {


        List<Long> listEmployeeId = dto.getEmployeeId();

        if (listEmployeeId != null) {
            Task task = this.taskRepository.findById(dto.getTaskId())
                    .orElseThrow(() -> new RuntimeException("Task not found id - " + dto.getTaskId()));

            listEmployeeId.forEach(id -> {

                Employee employee = this.employeeRepository.findEmployeeByIdAndDeleteFlag(id, Status.ACTIVE.getStatus())
                        .orElseThrow(() -> new RuntimeException("Employee not found id - " + id));
                if (!this.taskAssignmentRepository.existsTaskAssignmentByTaskIdAndEmployeeId(task.getId(),
                        employee.getId())) {
                    TaskAssignment taskAssignment = new TaskAssignment();
                    taskAssignment.setTask(task);
                    taskAssignment.setEmployee(employee);
                    this.taskAssignmentRepository.save(taskAssignment);
                }

            });
        } else {
            throw new NotFoundException("Employee is empty");
        }


        responseDTO.setContent(dto);
        responseDTO.setMessage("Assign success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping("/getById")
    public List<EmployeeDTO> findAllEmployeeAssignByTaskId(@RequestParam(value = "taskId") Long taskId) {
        return this.taskAssignmentRepository.getAllEmployeeAssignmentByTaskId(taskId, Status.ACTIVE.getStatus());
    }
}
