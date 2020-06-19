package com.hvcg.api.crm.entity;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "customer")
@Table(name="customer")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer extends BaseEntity{


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name = "day_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dayOfBirth;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private boolean gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="avatar_id")
    private Avatar avatar;

    @OneToMany(mappedBy = "customer")
    private List<CustomerAddress> address;

    public Customer() {
    }

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

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
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

}