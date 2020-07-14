package com.hvcg.api.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.TemporalType;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



import java.util.Date;
import java.util.List;

@Entity(name = "customer")
@Table(name="customer")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer extends BaseEntity{


    @Column(name = "first_name", length = 150)
    private String firstName;

    @Column(name = "last_name", length = 150)
    private String lastName;

    @Column(name = "full_name", length = 300)
    private String fullName;

    @Column(name="email", length = 150)
    private String email;

    @Column(name = "day_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dayOfBirth;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "gender")
    private int gender;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="avatar_id")
    private Avatar avatar;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<CustomerAddress> address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Task> tasks;

    public Customer() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public List<CustomerAddress> getAddress() {
        return address;
    }

    public void setAddress(List<CustomerAddress> address) {
        this.address = address;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
