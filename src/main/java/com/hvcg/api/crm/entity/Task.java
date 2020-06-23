package com.hvcg.api.crm.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "task")
@Table(name = "task")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskAssignment> taskAssignments;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private TaskStatus taskStatus;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "priority_id")
    private TaskPrioriry taskPrioriry;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskPrioriry getTaskPrioriry() {
        return taskPrioriry;
    }

    public void setTaskPrioriry(TaskPrioriry taskPrioriry) {
        this.taskPrioriry = taskPrioriry;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<TaskAssignment> getTaskAssignments() {
        return taskAssignments;
    }

    public void setTaskAssignments(List<TaskAssignment> taskAssignments) {
        this.taskAssignments = taskAssignments;
    }



    public void addTaskAssignment(TaskAssignment taskAssignment) {
        if (taskAssignments == null) {
            taskAssignments = new ArrayList<>();

        }
        taskAssignments.add(taskAssignment);
    }

}
