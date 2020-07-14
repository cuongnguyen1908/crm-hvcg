package com.hvcg.api.crm.controller;


import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.EmployeeDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.TaskDTO;
import com.hvcg.api.crm.dto.createDTO.TaskCreateDTO;

import com.hvcg.api.crm.dto.updateDTO.TaskUpdateDTO;
import com.hvcg.api.crm.entity.Customer;
import com.hvcg.api.crm.entity.TaskPrioriry;
import com.hvcg.api.crm.entity.Task;
import com.hvcg.api.crm.entity.Employee;
import com.hvcg.api.crm.entity.TaskAssignment;
import com.hvcg.api.crm.entity.TaskStatus;
import com.hvcg.api.crm.exception.NotFoundException;

import com.hvcg.api.crm.repository.TaskRepository;
import com.hvcg.api.crm.repository.EmployeeRepository;
import com.hvcg.api.crm.repository.TaskPrioriryRepository;
import com.hvcg.api.crm.repository.TaskStatusRepository;
import com.hvcg.api.crm.repository.CustomerRepository;
import com.hvcg.api.crm.repository.TaskAssignmentRepository;
import com.hvcg.api.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private ResponseDTO responseDTO;

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

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllTask() {
        responseDTO.setContent(this.taskService.getAllTask(Status.ACTIVE.getStatus()));
        responseDTO.setMessage(null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<ResponseDTO> getTaskById(@RequestParam(value = "taskId") Long taskId) {
        //get task
        Optional<TaskDTO> optionalTaskDTO = this.taskRepository.getTaskById(taskId, Status.ACTIVE.getStatus());

        optionalTaskDTO.ifPresentOrElse(taskDTO -> {
            List<EmployeeDTO> employeeDTO = this.taskAssignmentRepository
                    .getAllEmployeeAssignmentByTaskId(taskId, Status.ACTIVE.getStatus());
            taskDTO.setListAssignment(employeeDTO);
            responseDTO.setContent(taskDTO);
            responseDTO.setMessage(null);
        }, () -> {
            responseDTO.setContent(false);
            responseDTO.setMessage("Task not found with id - " + taskId);
        });
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @PostMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteTask(@RequestParam(value = "taskId") Long taskId) {
        this.taskService.deleteTask(taskId, Status.IN_ACTIVE.getStatus());

        responseDTO.setContent(true);
        responseDTO.setMessage("Delete success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    //create task
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createTask(@RequestBody TaskCreateDTO dto, HttpServletRequest request) {

        if (!this.taskStatusRepository.existsTaskStatusById(dto.getTaskStatusId())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Create fail taskStatus not found id - " + dto.getTaskStatusId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        if (!this.taskPrioriryRepository.existsTaskPrioriryById(dto.getTaskPrioriryId())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Create fail taskPriority not found id - " + dto.getTaskPrioriryId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        if (!this.customerRepository.existsCustomerByIdAndDeleteFlag(dto.getCustomerId(), Status.ACTIVE.getStatus())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Create fail customer not found id - " + dto.getCustomerId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        boolean isNull = false;
        boolean flgCheckAllMatchExistByEmployeeId = true;

        if  (dto.getEmployeeId() == null) { //if null
            isNull = true;
        }else{
            if (dto.getEmployeeId().size() > 0) {
                isNull = false;
                flgCheckAllMatchExistByEmployeeId = dto.getEmployeeId().stream()
                        .allMatch(employeeId -> this.employeeRepository
                                .existsEmployeeByIdAndDeleteFlag(employeeId, Status.ACTIVE.getStatus()));
            }
        }

        if (isNull == false && flgCheckAllMatchExistByEmployeeId == false) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Create fail some of the employeeId not match ");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }

        this.taskService.createTask(dto, isNull, request);

        responseDTO.setContent(dto);
        responseDTO.setMessage("Create success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDTO> updateTask(@RequestBody TaskUpdateDTO dto, HttpServletRequest request) {

        //check valid task status/ priority
        if (!this.taskStatusRepository.existsTaskStatusById(dto.getTaskStatusId())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Update fail taskStatus not found id - " + dto.getTaskStatusId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        if (!this.taskPrioriryRepository.existsTaskPrioriryById(dto.getTaskPrioriryId())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Update fail taskPriority not found id - " + dto.getTaskPrioriryId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        if (!this.customerRepository.existsCustomerByIdAndDeleteFlag(dto.getCustomerId(), Status.ACTIVE.getStatus())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Update fail customer not found id - " + dto.getCustomerId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        if (this.taskRepository.existsTaskByIdAndDeleteFlag(dto.getTaskId(), Status.ACTIVE.getStatus())) {
            if (!this.customerRepository.existsCustomerByIdAndDeleteFlag(dto.getCustomerId(), Status.ACTIVE.getStatus())) {
                responseDTO.setContent(false);
                responseDTO.setMessage("UPdate fail customer not found id - " + dto.getCustomerId());
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            boolean isNull = false;
            boolean flgCheckAllMatchExistByEmployeeId = true;

            if  (dto.getEmployeeId() == null) {
                isNull = true;
            }else{
                if (dto.getEmployeeId().size() > 0) {
                    isNull = false;
                    flgCheckAllMatchExistByEmployeeId = dto.getEmployeeId().stream()
                            .allMatch(employeeId -> this.employeeRepository
                                    .existsEmployeeByIdAndDeleteFlag(employeeId, Status.ACTIVE.getStatus()));
                }
            }

            if (isNull == false && flgCheckAllMatchExistByEmployeeId == false) {
                responseDTO.setContent(false);
                responseDTO.setMessage("Update fail some of the employeeId not match ");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
            this.taskService.updateTask(dto, isNull, request);
            responseDTO.setContent(dto);
            responseDTO.setMessage("Update success");

        } else {
            responseDTO.setContent(false);
            responseDTO.setMessage("Update fail not found task with id - " + dto.getCustomerId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
