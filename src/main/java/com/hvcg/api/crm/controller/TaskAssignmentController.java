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
import com.hvcg.api.crm.service.TaskAssignmentService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private TaskAssignmentService taskAssignmentService;

    @Autowired
    private EmployeeRepository employeeRepository;


    @PostMapping("/assign")
    public ResponseEntity<ResponseDTO> createTaskAssignment(@RequestBody TaskAssignmentCreateDTO dto, HttpServletRequest request) {

        //valid taskId
        if (!this.taskAssignmentRepository.existsTaskAssignmentByIdAndDeleteFlag(dto.getTaskId(), Status.ACTIVE.getStatus())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Create fail taskId not found with id - " + dto.getTaskId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }


        //valid employee != null or emplty
        if (dto.getEmployeeId() == null || dto.getEmployeeId().size() == 0) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Create fail employeeId is empty");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        boolean flgCheckAllMatchExistByEmployeeId = true;

                flgCheckAllMatchExistByEmployeeId = dto.getEmployeeId().stream()
                        .allMatch(employeeId -> this.employeeRepository
                                .existsEmployeeByIdAndDeleteFlag(employeeId, Status.ACTIVE.getStatus()));

        if (!flgCheckAllMatchExistByEmployeeId) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Create fail some of the employee Id not match");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        this.taskAssignmentService.createTaskAssignment(dto, request);



//        List<Long> listEmployeeId = dto.getEmployeeId();
//
//        if (listEmployeeId != null) {
//            Task task = this.taskRepository.findById(dto.getTaskId())
//                    .orElseThrow(() -> new RuntimeException("Task not found id - " + dto.getTaskId()));
//
//            listEmployeeId.forEach(id -> {
//
//                Employee employee = this.employeeRepository.findEmployeeByIdAndDeleteFlag(id, Status.ACTIVE.getStatus())
//                        .orElseThrow(() -> new RuntimeException("Employee not found id - " + id));
//                if (!this.taskAssignmentRepository.existsTaskAssignmentByTaskIdAndEmployeeId(task.getId(),
//                        employee.getId())) {
//                    TaskAssignment taskAssignment = new TaskAssignment();
//                    taskAssignment.setTask(task);
//                    taskAssignment.setEmployee(employee);
//                    this.taskAssignmentRepository.save(taskAssignment);
//                }
//
//            });
//        } else {
//            throw new NotFoundException("Employee is empty");
//        }


        responseDTO.setContent(dto);
        responseDTO.setMessage("Assign success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping("/getById")
    public ResponseEntity<ResponseDTO> getAllEmployeeAssignmentByTaskId(@RequestParam(value = "taskId") Long taskId) {
        responseDTO.setContent(this.taskAssignmentRepository.getAllEmployeeAssignmentByTaskId(taskId, Status.ACTIVE.getStatus()));
        responseDTO.setMessage("Assign success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
