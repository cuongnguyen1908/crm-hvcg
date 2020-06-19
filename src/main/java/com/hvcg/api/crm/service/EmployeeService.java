package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.createDTO.EmployeeCreateDTO;
import com.hvcg.api.crm.entity.AccountType;
import com.hvcg.api.crm.entity.Region;

public interface EmployeeService {
    void saveCustomer(EmployeeCreateDTO dto, Region region, AccountType accountType);
}
