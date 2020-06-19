package com.hvcg.api.crm.dto.createDTO;

public class AccountTypeCreateDTO {

    private String name;

    public AccountTypeCreateDTO() {
    }

    public AccountTypeCreateDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
