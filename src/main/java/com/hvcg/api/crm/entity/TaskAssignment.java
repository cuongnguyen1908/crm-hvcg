package com.hvcg.api.crm.entity;


import javax.persistence.*;
import java.util.List;

@Entity(name = "task_assignment")
@Table(name = "task_assignment")
public class TaskAssignment extends BaseEntity{

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "task_id")
    private Task task;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
