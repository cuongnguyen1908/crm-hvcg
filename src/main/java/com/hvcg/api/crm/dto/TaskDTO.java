package com.hvcg.api.crm.dto;

import java.util.Date;

public class TaskDTO {
    private Long id;

    private String name;

    private String description;

    private String ownCustomer;

    private String taskStatus;

    private String taskPrioriry;

    private Date startDate;



    public TaskDTO(Long id, String name, String description, String ownCustomer, String taskStatus,
                   String taskPrioriry, Date startDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownCustomer = ownCustomer;
        this.taskStatus = taskStatus;
        this.taskPrioriry = taskPrioriry;
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getOwnCustomer() {
        return ownCustomer;
    }

    public void setOwnCustomer(String ownCustomer) {
        this.ownCustomer = ownCustomer;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskPrioriry() {
        return taskPrioriry;
    }

    public void setTaskPrioriry(String taskPrioriry) {
        this.taskPrioriry = taskPrioriry;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
