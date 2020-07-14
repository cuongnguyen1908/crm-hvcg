package com.hvcg.api.crm.dto;

import java.util.Date;
import java.util.List;

public class TaskDTO {
    private Long id;

    private String name;

    private String description;

    private Long customerId;

    private String ownCustomer;

    private Long statusId;

    private String statusName;

    private Long priorityId;

    private String priorityName;

    private Date startDate;

    private List<EmployeeDTO> listAssignment;



    public TaskDTO(Long id, String name, String description, Long customerId, String ownCustomer, Long statusId,
                   String statusName, Long priorityId, String priorityName, Date startDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.customerId = customerId;
        this.ownCustomer = ownCustomer;
        this.statusId = statusId;
        this.statusName = statusName;
        this.priorityId = priorityId;
        this.priorityName = priorityName;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getOwnCustomer() {
        return ownCustomer;
    }

    public void setOwnCustomer(String ownCustomer) {
        this.ownCustomer = ownCustomer;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }



    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<EmployeeDTO> getListAssignment() {
        return listAssignment;
    }

    public void setListAssignment(List<EmployeeDTO> listAssignment) {
        this.listAssignment = listAssignment;
    }
}
