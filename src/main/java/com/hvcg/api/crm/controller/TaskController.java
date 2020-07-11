package com.hvcg.api.crm.controller;


import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.EmployeeDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.TaskDTO;
import com.hvcg.api.crm.dto.createDTO.TaskCreateDTO;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;


@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private ResponseDTO responseDTO;

    @Autowired
    private ResponseDTO responseTestDTO;

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
    public Page<TaskDTO> getAllTask(Pageable pageable) {
        return this.taskService.finAll(pageable);
    }

    @GetMapping("/getById/{taskId}")
    public ResponseEntity<ResponseDTO> getTaskById(@PathVariable Long taskId) {
        TaskDTO taskDTO = this.taskRepository.findTaskAndAssignById(taskId, Status.ACTIVE.getStatus());
        List<EmployeeDTO> employeeDTO = this.taskAssignmentRepository.findAllEmployeeAssignmentByTaskId(taskId,
                Status.ACTIVE.getStatus());

        taskDTO.setListAssignment(employeeDTO);

        responseDTO.setContent(taskDTO);
        responseDTO.setMessage(null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<ResponseDTO> deleteTask(@PathVariable Long taskId) {
        this.taskService.deleteTask(taskId, Status.IN_ACTIVE.getStatus());

        responseDTO.setContent(true);
        responseDTO.setMessage("Delete success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    //create task
    @PostMapping("/create")
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

        responseDTO.setContent(dto);
        responseDTO.setMessage("Create success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<ResponseDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskCreateDTO dto) {

        //check valid task status/ priority
        TaskStatus taskStatus = this.taskStatusRepository.findById(dto.getTaskStatusId())
                .orElseThrow(() -> new RuntimeException("Task status not found id - " + dto.getTaskStatusId()));

        TaskPrioriry taskPrioriry = this.taskPrioriryRepository.findById(dto.getTaskPrioriryId())
                .orElseThrow(() -> new RuntimeException("Task priority not found id - " + dto.getTaskStatusId()));

        Customer customer = this.customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found id - " + dto.getCustomerId()));

        if (this.taskRepository.existsTaskByIdAndDeleteFlag(taskId, Status.ACTIVE.getStatus())) {

            Task task = new Task();
            task.setId(taskId);
            task.setDescription(dto.getDescription());
            task.setName(dto.getName());
            task.setStartDate(dto.getStartDate());
            task.setTaskStatus(taskStatus);
            task.setTaskPrioriry(taskPrioriry);
            task.setCustomer(customer);

            List<Long> listEmployeeId = dto.getEmployeeId();

            //delete all old employee has assign
            this.taskAssignmentRepository.deleteTaskAssignByTaskId(taskId, Status.IN_ACTIVE.getStatus());

            if (listEmployeeId != null) {

                listEmployeeId.forEach(id -> {

                    Employee employee = this.employeeRepository.findEmployeeByIdAndDeleteFlag(id,
                            Status.ACTIVE.getStatus())
                            .orElseThrow(() -> new RuntimeException("Employee not found id - " + id));
                    TaskAssignment taskAssignment = new TaskAssignment();
                    taskAssignment.setEmployee(employee);
                    taskAssignment.setTask(task);
                    task.addTaskAssignment(taskAssignment);
                });

            }
            this.taskRepository.save(task);

        } else {
            throw new NotFoundException("Not found task id - " + taskId);
        }

        responseDTO.setContent(dto);
        responseDTO.setMessage("Update success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
