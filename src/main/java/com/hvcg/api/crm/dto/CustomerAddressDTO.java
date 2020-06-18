package com.hvcg.api.crm.dto;

public class CustomerAddressDTO {
    private Long id;

    private String address;

    private String contactPerson;

    private String contactPhone;

    private String description;

    private Long customerId;

    public CustomerAddressDTO(Long id, String address, String contactPerson, String contactPhone, String description,
                              Long customerId) {
        this.id = id;
        this.address = address;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.description = description;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
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
}
