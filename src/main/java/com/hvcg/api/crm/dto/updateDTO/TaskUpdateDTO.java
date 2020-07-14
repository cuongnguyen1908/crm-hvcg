package com.hvcg.api.crm.dto.updateDTO;


import java.util.Date;
import java.util.List;

public class TaskUpdateDTO {

    private Long taskId;

    private String name;

    private String description;

    private Long customerId;

    private Long taskStatusId;

    private Long taskPrioriryId;

    private Date startDate;

    private List<Long> employeeId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTaskStatusId() {
        return taskStatusId;
    }

    public void setTaskStatusId(Long taskStatusId) {
        this.taskStatusId = taskStatusId;
    }

    public Long getTaskPrioriryId() {
        return taskPrioriryId;
    }

    public void setTaskPrioriryId(Long taskPrioriryId) {
        this.taskPrioriryId = taskPrioriryId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Long> getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(List<Long> employeeId) {
        this.employeeId = employeeId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
