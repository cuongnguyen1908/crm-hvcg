package com.hvcg.api.crm.dto.createDTO;

import java.util.List;

public class TaskAssignmentCreateDTO {
    private List<Long> employeeId;

    private Long taskId;

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
