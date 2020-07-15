package com.hvcg.api.crm.dto;

import java.util.Date;

public class EmployeeDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String fullName;

    private int gender;

    private String email;

    private String address;

    private String phone;

    private String identityNumber;

    private String position;

    private String bankName;

    private String bankAccount;

    private Date dob;

    private String username;

    private String typeAccount;

    private String regionName;

    private String regionAlias;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id, String firstName, String lastName, String fullName, int gender, String email,
                       String address, String phone, String identityNumber, String position, String bankName,
                       String bankAccount, Date dob, String username, String typeAccount, String regionName,
                       String regionAlias) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.identityNumber = identityNumber;
        this.position = position;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.dob = dob;
        this.username = username;
        this.typeAccount = typeAccount;
        this.regionName = regionName;
        this.regionAlias = regionAlias;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionAlias() {
        return regionAlias;
    }

    public void setRegionAlias(String regionAlias) {
        this.regionAlias = regionAlias;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }


}
