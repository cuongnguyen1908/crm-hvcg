package com.hvcg.api.crm.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.util.Date;
import java.util.List;

@Entity(name = "employee")
@Table(name = "employee")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employee extends BaseEntity {
    @Column(name = "first_name", length = 150)
    private String firstName;

    @Column(name = "last_name", length = 150)
    private String lastName;

    @Column(name = "full_name", length = 300)
    private String fullName;

    @Column(name = "gender")
    private int gender;

    @Column(name = "email", length = 250)
    private String email;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "identity_number", length = 20)
    private String identityNumber;

    @Column(name = "position", length = 250)
    private String position;

    @Column(name= "bank_name", length = 250)
    private String bankName;

    @Column(name= "bank_account", length = 20)
    private String bankAccount;

    @Column(name= "dob")
    private Date dob;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="employee_account_id")
    private EmployeeAccount employeeAccount;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumn(name="region_id")
    private Region region;

    @OneToMany(mappedBy = "employee")
    private List<TaskAssignment> taskAssignments;

    // getter/setter


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public EmployeeAccount getEmployeeAccount() {
        return employeeAccount;
    }

    public void setEmployeeAccount(EmployeeAccount employeeAccount) {
        this.employeeAccount = employeeAccount;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<TaskAssignment> getTaskAssignments() {
        return taskAssignments;
    }

    public void setTaskAssignments(List<TaskAssignment> taskAssignments) {
        this.taskAssignments = taskAssignments;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
