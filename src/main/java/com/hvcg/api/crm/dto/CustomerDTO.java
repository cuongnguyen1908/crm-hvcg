package com.hvcg.api.crm.dto;

import com.hvcg.api.crm.entity.Avatar;

import java.util.Date;

public class CustomerDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String fullName;

    private String email;

    private Date dayOfBirth;

    private String phone;

    private boolean gender;

    private Long avatarId;

    private String avatarUrl;


    public CustomerDTO(Long id, String firstName, String lastName, String fullName, String email, Date dayOfBirth,
                       String phone, boolean gender, Long avatarId, String avatarUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.dayOfBirth = dayOfBirth;
        this.phone = phone;
        this.gender = gender;
        this.avatarId = avatarId;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
